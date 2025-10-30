package framework.states;

import framework.Engine;
import framework.Logger;
import framework.Property;
import framework.connector.Connector;
import framework.connector.payloads.EndOfRoundPayload;
import framework.connector.payloads.GlobalEffectChangePayload;
import framework.connector.payloads.StartOfMatchPayload;
import framework.connector.payloads.UpdatePayload;
import framework.graphics.GUIElement;
import framework.graphics.containers.LogCard;
import framework.graphics.containers.Queue;
import framework.graphics.text.Color;
import framework.resources.SpriteLibrary;
import framework.graphics.containers.HUD;

import game.controllers.ArenaAIController;
import game.entities.Hero;
import game.entities.HeroTeam;
import game.skills.logic.Effect;
import game.skills.logic.GlobalEffect;
import game.skills.Skill;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;
import game.skills.changeeffects.globals.Heat;
import utils.MyMaths;
import java.lang.reflect.Method;
import java.util.*;

public class Arena extends GUIElement {

    public Engine engine;

    public LogCard logCard;
    private final HUD hud;

    public ArenaAIController aiController;

    public Queue queue = new Queue();

    public enum Status {
        TARGET_CHOICE,
        WAIT_ON_ANIMATION,
        WAIT_ON_HUD
    }
    public boolean pvp = true;
    public Status status = Status.WAIT_ON_HUD;
    public String nextAction = null;
    public int activePointer = -1;
    public int targetPointer = -1;
    public int[] targetPointers = new int[0];
    public int matrixPointer = 0;
    public int[] targetMatrix = new int[0];
    public boolean finished = false;

    public Skill activeSkill;
    public Hero activeHero = null;
    public Hero[] activeTargets = null;
    public final List<HeroTeam> teams = new ArrayList<>();
    public HeroTeam activeTeam;
    private List<Hero> heroesChosen = new ArrayList<>();
    public GlobalEffect globalEffect;

    private final int[] friendXPos = new int[]{30, 98, 166, 234};
    private final int[] enemyXPos = new int[]{342, 410, 478, 546, 614};
    public static int numberPositions = 4;
    public static int firstFriendPos = 0;
    public static int lastFriendPos = 3;
    public static int firstEnemyPos = 4;
    public static int lastEnemyPos = 7;
    public static int[] allPos = new int[]{0,1,2,3,4,5,6,7};
    private final int heroYPos = 80;

    public Arena(Engine e, boolean pvp) {
        super(Engine.X, Engine.Y);
        this.id = StateManager.ARENA;
        this.engine = e;
        this.hud = new HUD(e);
        this.hud.setArena(this);
        this.logCard = new LogCard(e);
        this.pvp = pvp;
        if (!this.pvp) {
            this.aiController = new ArenaAIController(this);
        }
    }

    public void setTeams(HeroTeam friend, HeroTeam enemies) {
        this.teams.add(0,friend);
        this.teams.add(1,enemies);
        for (int i = 0; i < friend.heroes.length; i++) {
            Hero hero = friend.heroes[i];
            hero.team = friend;
            hero.enemyTeam = enemies;
            hero.enterArena(friend.teamNumber==2?MyMaths.getMirrorPos(i):i, this);
        }
        for (int i = 0; i < enemies.heroes.length; i++) {
            Hero hero = enemies.heroes[i];
            hero.team = enemies;
            hero.enemyTeam = friend;
            hero.enterArena(enemies.teamNumber==2?MyMaths.getMirrorPos(i):i, this);
        }
        initialOrder();
        this.activeHero = this.queue.peek();
        this.hud.setActiveHero(this.activeHero);
        this.updateEntities();
        this.startOfMatch();
    }

    private void initialOrder() {
        List<Hero> backwards = this.getAllLivingEntities();
        Collections.shuffle(backwards);
        backwards = backwards.stream()
                .sorted((Hero e1, Hero e2)->Integer.compare(e2.getStat(Stat.SPEED), e1.getStat(Stat.SPEED)))
                .toList();
        this.queue.addAll(backwards);
    }

    @Override
    public void update(int frame) {
        switch (status) {
            case TARGET_CHOICE -> updateChooseTarget();
            case WAIT_ON_ANIMATION -> updateWaitOnAnimation();
            default -> this.hud.update(frame);
        }
        updateAnimations(frame);
        this.logCard.update(frame);
    }

    private void updateChooseTarget() {
        if (engine.keyB._leftPressed) {
            this.getNextMatrixPointer(-1);
            this.targetPointer = this.targetMatrix[matrixPointer];
        }
        if (engine.keyB._rightPressed) {
            this.getNextMatrixPointer(1);
            this.targetPointer = this.targetMatrix[matrixPointer];
        }
        if (engine.keyB._backPressed) {
            this.switchToArenaHUD();
        }
        if (engine.keyB._enterPressed) {
            performSkill();
        }
    }

    private void updateWaitOnAnimation() {
        int amntRunning = 0;
        for (Hero e : this.teams.stream().flatMap(ht->ht.getHeroesAsList().stream()).toList()) {
            if (e!= null && e.anim.waitFor) {
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
                resumeTurn();
            }
        }
    }
    private void updateAnimations(int frame) {
        this.teams.forEach(t->t.updateAnimations(frame));
    }
    //GUI Logic
    private void switchToArenaHUD() {
        this.hud.activateTeamArenaOv();
        this.targetPointers = new int[0];
        this.targetMatrix = new int[0];
        this.status = Status.WAIT_ON_HUD;
    }
    public void switchToTargetChoice(Skill s) {
        this.hud.disableTeamArenaOV();
        this.status = Status.TARGET_CHOICE;
        this.activeSkill = s;

        if (this.activeSkill.getTargetType().equals(TargetType.SINGLE)
                || this.activeSkill.getTargetType().equals(TargetType.SINGLE_OTHER)) {
            this.targetMatrix = this.activeSkill.setupTargetMatrix();
            this.targetPointer = this.targetMatrix[this.targetMatrix.length-1];
            this.matrixPointer = this.targetMatrix.length-1;

        }else {
            this.targetPointers = new int[]{};
            switch (this.activeSkill.getTargetType()) {
                case SELF:
                    this.targetPointers = new int[]{this.activeHero.getPosition()};
                    break;
                case ALL:
                    this.targetPointers = allPos;
                    break;
                case ALL_ALLY:
                    this.targetPointers = this.activeHero.getAllies().stream().mapToInt(Hero::getPosition).toArray();
                    break;
                case ALL_ENEMY:
                    this.targetPointers = this.activeHero.getEnemies().stream().mapToInt(Hero::getPosition).toArray();
                    break;
                case ALL_OTHER_ALLY:
                    this.targetPointers = this.activeHero.getAllies().stream()
                            .mapToInt(Hero::getPosition).filter(i->this.activeHero.getPosition()!=i).toArray();
                    break;

            }
            this.performSkill();
        }
    }
    private void changeActiveHero(int pos) {
        this.activeHero = this.getAtPosition(pos);
        this.hud.setActiveHero(this.activeHero);
    }
    private int getNextTeamPointer(int dir) {
        int pointer = this.activePointer + dir;
        Hero hero = this.getAtPosition(pointer);
        if (!this.activeTeam.getHeroesAsList().contains(hero)) {
            return this.activePointer;
        }
        if (hero == null || hero.team.teamNumber != this.activeTeam.teamNumber) {
            return this.activePointer;
        }
        return pointer;
    }
    private void getNextMatrixPointer(int dir) {
        int nextMatrixPointer = this.matrixPointer+dir;
        if (nextMatrixPointer >= 0 && nextMatrixPointer < this.targetMatrix.length) {
            this.matrixPointer = nextMatrixPointer;
        }
    }


    //Turn Logic
    private void startOfMatch() {
        StartOfMatchPayload startOfMatchPayload = new StartOfMatchPayload();
        Connector.fireTopic(Connector.START_OF_MATCH, startOfMatchPayload);
        this.startTurn();
    }
    private void startTurn() {
        Logger.logLn("startTurn()");
        this.activeHero = this.queue.peek();
        if (this.activeHero == null) {
            System.out.println("Could not retrieve first in queue");
        } else {
            Logger.logLn("-----------------------------");
            Logger.logLn("Start turn for " + this.activeHero.getName() + " " + this.activeHero.getPosition());
            this.activeHero.startOfTurn();
            this.activeHero.prepareCast();
            resumeTurn();
        }
    }

    private void resumeTurn() {
        Logger.logLn("resumeTurn()");
        this.getAllLivingEntities().forEach(hero-> hero.getSkills().forEach(Skill::setToInitial));
        if (this.checkEndOfMatch()) {
            return;
        }
        this.updateEntities();
        if (!this.activeHero.isMoved()) {
            if (!this.pvp && this.activeHero.isTeam2()) {
                aiTurn();
            } else {
                this.status = Status.WAIT_ON_HUD;
                this.hud.activateTeamArenaOv();
                this.hud.setActiveHero(this.activeHero);
            }
        } else {
            endOfTurn();
        }
    }
    private void endOfTurn() {
        Logger.logLn("endOfTurn()");
        this.activeHero.endOfTurn();
        this.removeTheDead();
        this.queue.didTurn(this.activeHero);

        checkEndOfRound();
    }
    private void checkEndOfRound() {
        if (this.queue.hasHeroUp()) {
            startTurn();
        } else {
            endOfRound();
        }
    }
    private void endOfRound() {
        this.checkEndOfMatch();
//        this.logCard.openLog("End of round: ");
        for (HeroTeam team : this.teams) {
            if (team.getHeroesAsList().stream().allMatch(Hero::isMoved)) {
                team.getHeroesAsList().forEach(hero -> hero.setMoved(false));
            }
        }
        this.heroesChosen = new ArrayList<>();
        EndOfRoundPayload endOfTurnPayload = new EndOfRoundPayload()
                .setArena(this);
        Connector.fireTopic(Connector.END_OF_ROUND, endOfTurnPayload);
        this.logCard.finishLog();
        this.queue.restartTurnQueue();
        this.removeTheDead();
        this.startTurn();
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
        this.activeHero.endOfTurn();
        this.activeSkill = null;
        this.nextAction = null;
    }

    //Misc
    public void setGlobalEffect(GlobalEffect globalEffect) {
        GlobalEffect oldEffect = this.globalEffect;
        this.globalEffect = globalEffect;
        GlobalEffectChangePayload globalEffectChangePayload = new GlobalEffectChangePayload()
                .setEffect(this.globalEffect)
                .setOldEffect(oldEffect);
        Connector.fireTopic(Connector.GLOBAL_EFFECT_CHANGE, globalEffectChangePayload);
    }

    public void moveTo(Hero e, int targetPos) {

        if ((e.isTeam2() && (targetPos < firstEnemyPos || targetPos > lastEnemyPos)) ||
                (!e.isTeam2() && (targetPos <firstFriendPos || targetPos > lastFriendPos))) {
            return;
        }
        if ((e.isTeam2() && (this.teams.get(1).heroes.length <= targetPos-numberPositions || this.teams.get(1).heroes[targetPos-numberPositions] == null)) ||
                !e.isTeam2() && (this.teams.get(0).heroes.length <= targetPos || this.teams.get(0).heroes[targetPos] == null)) {
            return;
        }
        HeroTeam group = e.isTeam2() ? this.teams.get(1) : this.teams.get(0);
        int oldPosition = e.getPosition();
        int oldCastPos = e.getCasterPosition();
        int targetCastPos = e.isTeam2() ? Arena.lastEnemyPos - targetPos : targetPos;
        Hero switchWith = group.heroes[targetCastPos];

        switchWith.setPosition(oldPosition);
        e.setPosition(targetPos);

        group.heroes[targetCastPos] = e;
        group.heroes[oldCastPos] = switchWith;

    }
    private void removeTheDead() {
        this.queue.removeAll(this.teams.stream().flatMap(ht->ht.removeTheDead().stream()).toList());
    }
    private void updateEntities() {
        UpdatePayload updatePayload = new UpdatePayload();
        Connector.fireTopic(Connector.UPDATE, updatePayload);
    }


    //Queries

    public int amountEffects(String effect) {
        int amnt = 0;
        for (Hero hero : getAllLivingEntities()) {
            amnt += hero.hasPermanentEffect(effect);
        }
        return amnt;
    }

    public List<Hero> getAllLivingEntities() {
        return new ArrayList<>(this.teams.stream().flatMap(ht->ht.getHeroesAsList().stream()).toList());
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
        for (Hero e: getAllLivingEntities()) {
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
        if (this.teams.get(0).deadHeroes.size() == numberPositions || this.teams.get(1).deadHeroes.size() == numberPositions) {
            this.finished = true;
            return true;
        }
        return false;
    }


    //RENDER
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
        fillWithGraphicsSize(0,0,640,360,canvas, null);
    }

    private int[] getSingleTargets() {
        return new int[]{this.activePointer};
    }
    private void renderPointer() {
        int[] pointer = SpriteLibrary.getSprite("arrow_down");
//        if (this.activePointer > -1) {
//            int activeX = this.activePointer > lastFriendPos ? enemyXPos[this.activePointer-firstEnemyPos] : friendXPos[this.activePointer];
//            fillWithGraphicsSize(activeX + (64/2 - 16), heroYPos - (32), 32, 32, pointer, null);
//        }
        if (this.targetPointer > -1) {
            int targetX = this.targetPointer > lastFriendPos ? enemyXPos[this.targetPointer-firstEnemyPos] : friendXPos[this.targetPointer];
            fillWithGraphicsSize(targetX + (64/2 - 16), heroYPos - (32), 32, 32, pointer, null);
        }
    }
    private void renderTeams() {
        for (Hero hero: this.teams.get(0).heroes) {
            if (hero != null) {
                int x = friendXPos[hero.getPosition()];
                Color border =  this.activeHero == hero
                        ? Color.WHITE
                        : Color.VOID;
                fillWithGraphicsSize(x, heroYPos, hero.getWidth(), hero.getHeight(), hero.render(Hero.ARENA), true, border);
            }
        }
        for (Hero hero: this.teams.get(1).heroes) {
            if (hero != null) {
                int x = enemyXPos[Math.abs(firstEnemyPos - hero.getPosition())];

                Color border = this.activeHero == hero
                        ? Color.WHITE
                        : Color.VOID;
                fillWithGraphicsSize(x, heroYPos, hero.getWidth(), hero.getHeight(), hero.render(Hero.ARENA), true, border);
            }
        }
    }
    private void renderHUD() {
        fillWithGraphicsSize(0,0,640,360,hud.render(),null);
    }
    private void renderArenaEffect() {
        if (this.globalEffect != null) {
            int[] effectPixels = SpriteLibrary.getSprite(Heat.class.getName());
            fillWithGraphicsSize(this.width-40, 8, Property.GLOBAL_EFFECT_WIDTH, Property.GLOBAL_EFFECT_HEIGHT, effectPixels, Color.WHITE);
        }
    }
    private void renderQueue() {
        if (this.queue != null) {
            fillWithGraphicsSize(Property.QUEUE_X, Property.QUEUE_Y, Property.QUEUE_WIDTH, Property.QUEUE_HEIGHT, this.queue.render(),false);
        }
    }

    private void renderLog() {
        fillWithGraphicsSize(this.logCard.getX(), this.logCard.getY(), this.logCard.getWidth(), this.logCard.getHeight(), this.logCard.render(), false);
    }
}
