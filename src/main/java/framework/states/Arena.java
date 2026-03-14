package framework.states;

import framework.Engine;
import framework.Logger;
import framework.Property;
import framework.connector.ConnectionPayload;
import framework.connector.Connector;
import framework.graphics.containers.LogCard;
import framework.graphics.containers.Queue;
import framework.graphics.text.Color;
import framework.resources.SpriteLibrary;
import framework.graphics.containers.HUD;

import game.controllers.ArenaAIController;
import game.effects.Effect;
import game.effects.globals.Darkness;
import game.effects.status.Stunned;
import game.entities.Hero;
import game.entities.HeroTeam;
import game.skills.Skill;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;
import utils.MyMaths;

import java.lang.reflect.Method;
import java.util.*;

public class Arena extends State {

  public LogCard logCard;
  private final HUD hud;

  public ArenaAIController aiController;

  public Queue queue = new Queue();

  public enum Status {
    TARGET_CHOICE,
    WAIT_ON_ANIMATION,
    TURN_LOGIC,
    WAIT_ON_HUD
  }

  public boolean roundStarted = false;

  public boolean pvp = true;
  public Status status;
  public String nextAction = null;
  public int activePointer = -1;
  public int targetPointer = -1;
  public int[] targetPointers = new int[0];
  public int matrixPointer = 0;
  public int[] targetMatrix = new int[0];

  public Skill activeSkill;
  public Hero activeHero = null;
  public Hero[] activeTargets = null;
  public final List<HeroTeam> teams = new ArrayList<>();
  public HeroTeam activeTeam;
  public Effect globalEffect;
  public List<Effect> fieldEffects;

  private final int[] friendXPos = new int[] {30, 98, 166, 234};
  private final int[] enemyXPos = new int[] {342, 410, 478, 546, 614};
  public static int numberPositions = 4;
  public static int firstFriendPos = 0;
  public static int lastFriendPos = 3;
  public static int firstEnemyPos = 4;
  public static int lastEnemyPos = 7;
  public static int[] allPos = new int[] {0, 1, 2, 3, 4, 5, 6, 7};
  private final int heroYPos = 80;

  public Arena(Memory memory, boolean pvp) {
    super(memory);
    this.id = StateManager.ARENA;
    this.hud = new HUD();
    this.hud.setArena(this);
    this.logCard = new LogCard();
    this.pvp = pvp;
    this.status = Status.TURN_LOGIC;
    if (!this.pvp) {
      this.aiController = new ArenaAIController(this);
    }
  }

  @Override
  public void update(int frame) {
    switch (status) {
      case TURN_LOGIC -> updateTurnLogic();
      case TARGET_CHOICE -> updateChooseTarget();
      case WAIT_ON_ANIMATION -> updateWaitOnAnimation();
      case WAIT_ON_HUD -> this.hud.update(frame);
    }
    updateAnimations(frame);
    this.logCard.update(frame);
  }

  private void updateTurnLogic() {
    if (!started) {
      this.start();
    } else if (!roundStarted) {
      this.startRound();
    } else if (this.activeHero != null) {
      if (!this.activeHero.isMoved()) {
        this.startTurn();
      } else {
        this.endTurn();
      }
    } else {
      endOfRound();
    }
  }

  private void updateChooseTarget() {
    if (Engine.KeyBoard._leftPressed) {
      this.getNextMatrixPointer(-1);
      this.targetPointer = this.targetMatrix[matrixPointer];
    }
    if (Engine.KeyBoard._rightPressed) {
      this.getNextMatrixPointer(1);
      this.targetPointer = this.targetMatrix[matrixPointer];
    }
    if (Engine.KeyBoard._backPressed) {
      this.switchToArenaHUD();
    }
    if (Engine.KeyBoard._enterPressed) {
      performSkill();
    }
  }

  private void updateWaitOnAnimation() {
    int amntRunning = 0;
    for (Hero e : this.getAllLivingEntities()) {
      if (e != null && e.anim.waitFor) {
        amntRunning++;
      }
    }
    if (amntRunning == 0) {
      if (this.nextAction != null) {
        try {
          Method method = this.getClass().getMethod(this.nextAction);
          method.invoke(this);
        } catch (Exception e) {
          System.out.println(Arrays.toString(e.getStackTrace()));
          this.nextAction = null;
        }
      } else {
        status = Status.TURN_LOGIC;
      }
    }
  }

  private void updateAnimations(int frame) {
    this.teams.forEach(t -> t.updateAnimations(frame));
  }

  @Override
  public void start() {
    this.setTeams(memory.teams[0], memory.teams[1]);
    this.getAllLivingEntities().forEach(Hero::arenaStart);
    this.activeHero = this.queue.peek();
    this.hud.setActiveHero(this.activeHero);
    trigger_startOfMatch();
    initialOrder();
  }

  public void startRound() {
    this.getAllLivingEntities().forEach(hero -> hero.getSkills().forEach(Skill::setToInitial));
    trigger_startOfRound();
    this.activeHero = this.queue.peek();
  }

  public void startTurn() {
    this.activeHero.getSkills().forEach(Skill::getCurrentVersion);
    this.activeHero.startOfTurn();
    if (this.activeHero.hasPermanentEffect(Stunned.class)) {
      this.activeHero.removePermanentEffectOfClass(Stunned.class);
      this.activeHero.setMoved(true);
    } else {
      if (!this.pvp && this.activeHero.isTeam2()) {
        aiTurn();
      } else {
        switchToArenaHUD();
      }
    }
  }

  private void endTurn() {
    if (this.activeHero.isAlive()) {
      this.activeHero.endOfTurn();
    }
    this.removeTheDead();
    this.queue.didTurn(this.activeHero);
    if (queue.hasHeroUp()) {
      this.activeHero = queue.peek();
    } else {
      this.activeHero = null;
    }
    this.checkEndOfMatch();
  }

  private void endOfRound() {
    for (HeroTeam team : this.teams) {
      if (team.getHeroesAsList().stream().allMatch(Hero::isMoved)) {
        team.getHeroesAsList().forEach(hero -> hero.setMoved(false));
      }
    }
    trigger_endOfRound();
    this.logCard.finishLog();
    this.removeTheDead();
    if (this.checkEndOfMatch()) {
      return;
    }
    this.roundStarted = false;
    this.queue.restartRoundQueue();
  }

  public void extraTurn(Hero hero) {
    this.queue.sendToBack(hero);
  }

  public void setTeams(HeroTeam friends, HeroTeam enemies) {
    this.teams.add(0, friends);
    this.teams.add(1, enemies);
    for (int i = 0; i < friends.heroes.length; i++) {
      Hero hero = friends.heroes[i];
      hero.team = friends;
      hero.enemyTeam = enemies;
      hero.setPosition(friends.teamNumber == 2 ? MyMaths.getMirrorPos(i) : i, this);
    }
    for (int i = 0; i < enemies.heroes.length; i++) {
      Hero hero = enemies.heroes[i];
      hero.team = enemies;
      hero.enemyTeam = friends;
      hero.setPosition(enemies.teamNumber == 2 ? MyMaths.getMirrorPos(i) : i, this);
    }
  }

  private void initialOrder() {
    List<Hero> backwards = this.getAllLivingEntities();
    Collections.shuffle(backwards);
    backwards =
        backwards.stream()
            .sorted(
                (Hero e1, Hero e2) ->
                    Integer.compare(e2.getStat(Stat.DEXTERITY), e1.getStat(Stat.DEXTERITY)))
            .toList();
    this.queue.addAll(backwards);
  }

  // GUI Logic
  private void switchToArenaHUD() {
    this.hud.activateTeamArenaOv();
    this.targetPointers = new int[0];
    this.targetMatrix = new int[0];
    this.status = Status.WAIT_ON_HUD;
    this.hud.setActiveHero(this.activeHero);
  }

  public void switchToTargetChoice(Skill s) {
    this.hud.disableTeamArenaOV();
    this.status = Status.TARGET_CHOICE;
    this.activeSkill = s;

    if (this.activeSkill.getTargetType().equals(TargetType.SINGLE)
        || this.activeSkill.getTargetType().equals(TargetType.SINGLE_OTHER)) {
      this.targetMatrix = this.activeSkill.setupTargetMatrix();
      this.targetPointer = this.targetMatrix[this.targetMatrix.length - 1];
      this.matrixPointer = this.targetMatrix.length - 1;

    } else {
      this.targetPointers = new int[] {};
      switch (this.activeSkill.getTargetType()) {
        case SELF:
          this.targetPointers = new int[] {this.activeHero.getPosition()};
          break;
        case ALL:
          this.targetPointers = allPos;
          break;
        case ALL_TARGETS:
          this.targetPointers = activeSkill.getConvertedTargetPos();
        case ALL_OTHER_ALLY:
          this.targetPointers =
              this.activeHero.getAllies().stream()
                  .mapToInt(Hero::getPosition)
                  .filter(i -> this.activeHero.getPosition() != i)
                  .toArray();
          break;
      }
      this.performSkill();
    }
  }

  private void getNextMatrixPointer(int dir) {
    int nextMatrixPointer = this.matrixPointer + dir;
    if (nextMatrixPointer >= 0 && nextMatrixPointer < this.targetMatrix.length) {
      this.matrixPointer = nextMatrixPointer;
    }
  }

  private void aiTurn() {
    Logger.logLn("aiTurn()");
    this.aiController.turn();
  }

  private void performSkill() {
    this.logCard.openLog("");
    this.activeSkill.perform();
    this.targetPointers = new int[0];
    this.nextAction = "resolveSkill";
    this.status = Status.WAIT_ON_ANIMATION;
    this.activeHero.setMoved(true);
  }

  public void resolveSkill() {
    this.activeSkill.resolve();
    this.logCard.finishLog();
    this.activeSkill = null;
    this.nextAction = null;
    this.removeTheDead();
    this.status = Status.TURN_LOGIC;
  }

  // Misc
  public void setGlobalEffect(Effect globalEffect) {
    Effect oldEffect = this.globalEffect;
    this.globalEffect = globalEffect;
    trigger_globalEffectChange(globalEffect, oldEffect);
  }

  public boolean hasGlobalEffect(Class<Darkness> darknessClass) {
    return this.globalEffect != null && this.globalEffect.getClass().equals(darknessClass);
  }

  public void addFieldEffect(int position, Effect effect, Hero caster) {
    if (trigger_effectFailure(effect, caster, getAtPosition(position))) {
      return;
    }
    Effect oldEffect =
        this.fieldEffects.stream().filter(e -> e.position == position).findFirst().orElse(null);

    boolean newlyAdded = false;
    if (oldEffect != null) {
      if (oldEffect.getName().equals(effect.getName())) {
        oldEffect.addStacks(effect.stacks);
        oldEffect.turns += effect.turns;
      } else {
        oldEffect.removeFromHero();
        this.fieldEffects.remove(oldEffect);
      }
    } else {
      newlyAdded = true;
      Effect newEffect = effect.copy();
      newEffect.origin = caster;
      newEffect.arena = this;
      this.fieldEffects.add(effect);
      newEffect.addSubscriptions();
    }

    trigger_effectAdded(effect, caster, newlyAdded);
  }

  public void push(Hero h, int push) {
    int targetPos = h.getPosition() + (h.isTeam2() ? push : -1 * push);
    moveTo(h, targetPos);
  }
  public void pull(Hero h, int push) {
    int targetPos = h.getPosition() - (h.isTeam2() ? push : -1 * push);
    moveTo(h, targetPos);
  }

  public void moveTo(Hero e, int targetPos) {

    if ((e.isTeam2() && (targetPos < firstEnemyPos || targetPos > lastEnemyPos))
        || (!e.isTeam2() && (targetPos < firstFriendPos || targetPos > lastFriendPos))) {
      return;
    }
    if ((e.isTeam2()
            && (this.teams.get(1).heroes.length <= targetPos - numberPositions
                || this.teams.get(1).heroes[targetPos - numberPositions] == null))
        || !e.isTeam2()
            && (this.teams.get(0).heroes.length <= targetPos
                || this.teams.get(0).heroes[targetPos] == null)) {
      return;
    }
    HeroTeam group = e.isTeam2() ? this.teams.get(1) : this.teams.get(0);
    int oldPosition = e.getPosition();
    int oldTeamPosition = e.getTeamPosition();
    int targetTeamPosition = e.isTeam2() ? Arena.lastEnemyPos - targetPos : targetPos;
    Hero switchWith = group.heroes[targetTeamPosition];

    switchWith.setPosition(oldPosition);
    e.setPosition(targetPos);

    group.heroes[targetTeamPosition] = e;
    group.heroes[oldTeamPosition] = switchWith;

    leaves(e, oldPosition);
    enters(e, targetPos);
    leaves(switchWith, targetPos);
    enters(switchWith, oldPosition);
  }

  private void enters(Hero hero, int position) {
    Connector.fireTopic(
        Connector.ON_ENTER, new ConnectionPayload(1).setTarget(hero).setValue(position));
  }

  private void leaves(Hero hero, int position) {
    Connector.fireTopic(
        Connector.ON_LEAVE, new ConnectionPayload(1).setTarget(hero).setValue(position));
  }

  private void removeTheDead() {
    this.queue.removeAll(this.teams.stream().flatMap(ht -> ht.removeTheDead().stream()).toList());
  }

  // Queries

  public int amountEffects(String effect) {
    int amnt = 0;
    for (Hero hero : getAllLivingEntities()) {
      amnt += hero.getPermanentEffectStacks(effect);
    }
    return amnt;
  }

  public List<Hero> getAllLivingEntities() {
    return new ArrayList<>(
        this.teams.stream().flatMap(ht -> ht.getHeroesAsList().stream()).toList());
  }

  public int[] getAllLivingPositions() {
    List<Hero> livingHeroes = getAllLivingEntities();
    livingHeroes.sort(Comparator.comparingInt(Hero::getPosition));
    int[] positions = new int[livingHeroes.size()];
    for (int i = 0; i < livingHeroes.size(); i++) {
      positions[i] = livingHeroes.get(i).getPosition();
    }
    return positions;
  }

  public Hero getAtPosition(int position) {
    for (Hero e : getAllLivingEntities()) {
      if (e != null && e.getPosition() == position) {
        return e;
      }
    }
    return null;
  }

  public Hero[] getEntitiesAt(int[] positions) {
    List<Hero> resultList = new ArrayList<>();
    for (int position : positions) {
      resultList.add(getAtPosition(position));
    }
    return resultList.stream().filter(Objects::nonNull).toList().toArray(new Hero[0]);
  }

  private boolean checkEndOfMatch() {
    if (this.teams.get(0).deadHeroes.size() == numberPositions
        || this.teams.get(1).deadHeroes.size() == numberPositions) {
      this.finished = true;
      return true;
    }
    return false;
  }

  // triggers

  public void trigger_startOfMatch() {
    ConnectionPayload pl = new ConnectionPayload(1).setArena(this);
    Connector.fireTopic(Connector.START_OF_MATCH, pl);
  }

  public void trigger_startOfRound() {
    ConnectionPayload pl = new ConnectionPayload(1).setArena(this);
    Connector.fireTopic(Connector.START_OF_ROUND, pl);
  }

  public void trigger_endOfRound() {
    ConnectionPayload endOfTurnPayload = new ConnectionPayload(1).setArena(this);
    Connector.fireTopic(Connector.END_OF_ROUND, endOfTurnPayload);
  }

  public boolean trigger_effectFailure(Effect effect, Hero caster, Hero target) {
    ConnectionPayload payload =
        new ConnectionPayload(1).setEffect(effect).setCaster(caster).setTarget(target);
    Connector.fireTopic(Connector.EFFECT_FAILURE_CHECK, payload);
    return payload.failure;
  }

  public void trigger_effectAdded(Effect effect, Hero caster, boolean newlyAdded) {
    ConnectionPayload effectAddedPayload =
        new ConnectionPayload(1).setEffect(effect).setCaster(caster).setNewEffect(newlyAdded);
    Connector.fireTopic(Connector.EFFECT_ADDED, effectAddedPayload);
  }

  private void trigger_globalEffectChange(Effect globalEffect, Effect oldEffect) {
    ConnectionPayload globalEffectChangePayload =
        new ConnectionPayload(1).setGlobalEffect(globalEffect).setOldGlobalEffect(oldEffect);
    Connector.fireTopic(Connector.GLOBAL_EFFECT_CHANGE, globalEffectChangePayload);
  }

  // RENDER
  @Override
  public int[] render() {
    background(Color.RED);
    renderBackGround();
    renderPointer();
    renderTeams();
    renderHUD();
    renderArenaEffect();
    renderQueue();
    renderLog();
    return this.pixels;
  }

  private void renderBackGround() {
    int[] canvas = SpriteLibrary.getSprite("scene01");
    fillWithGraphicsSize(0, 0, 640, 360, canvas, null);
  }

  private void renderPointer() {
    int[] pointer = SpriteLibrary.getSprite("arrow_down");
    if (this.targetPointer > -1) {
      int targetX =
          this.targetPointer > lastFriendPos
              ? enemyXPos[this.targetPointer - firstEnemyPos]
              : friendXPos[this.targetPointer];
      fillWithGraphicsSize(targetX + (64 / 2 - 16), heroYPos - (32), 32, 32, pointer, null);
    }
  }

  private void renderTeams() {
    for (Hero hero : this.teams.get(0).heroes) {
      if (hero != null) {
        int x = friendXPos[hero.getPosition()];
        Color border = this.activeHero == hero ? Color.WHITE : Color.VOID;
        fillWithGraphicsSize(
            x, heroYPos, hero.getWidth(), hero.getHeight(), hero.render(Hero.ARENA), true, border);
      }
    }
    for (Hero hero : this.teams.get(1).heroes) {
      if (hero != null) {
        int x = enemyXPos[Math.abs(firstEnemyPos - hero.getPosition())];

        Color border = this.activeHero == hero ? Color.WHITE : Color.VOID;
        fillWithGraphicsSize(
            x, heroYPos, hero.getWidth(), hero.getHeight(), hero.render(Hero.ARENA), true, border);
      }
    }
  }

  private void renderHUD() {
    fillWithGraphicsSize(0, 0, 640, 360, hud.render(), null);
  }

  private void renderArenaEffect() {
    if (this.globalEffect != null) {
      int[] effectPixels = SpriteLibrary.getSprite(this.globalEffect.getClass().getName());
      fillWithGraphicsSize(
          this.width - 40,
          8,
          Property.GLOBAL_EFFECT_WIDTH,
          Property.GLOBAL_EFFECT_HEIGHT,
          effectPixels,
          Color.WHITE);
    }
  }

  private void renderQueue() {
    if (this.queue != null) {
      fillWithGraphicsSize(
          Property.QUEUE_X,
          Property.QUEUE_Y,
          Property.QUEUE_WIDTH,
          Property.QUEUE_HEIGHT,
          this.queue.render(),
          false);
    }
  }

  private void renderLog() {
    fillWithGraphicsSize(
        this.logCard.getX(),
        this.logCard.getY(),
        this.logCard.getWidth(),
        this.logCard.getHeight(),
        this.logCard.render(),
        false);
  }
}
