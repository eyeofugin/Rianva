package framework.states;

import framework.Engine;
import framework.Logger;
import framework.Property;
import framework.connector.Connector;
import framework.connector.payloads.EndOfRoundPayload;
import framework.connector.payloads.GlobalEffectChangePayload;
import framework.connector.payloads.PrepareUpdatePayload;
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
import game.skills.GlobalEffect;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import game.skills.changeeffects.effects.other.Exhausted;
import game.skills.changeeffects.effects.Scoped;
import game.skills.changeeffects.globals.Heat;
import game.skills.changeeffects.statusinflictions.Rooted;
import utils.Action;
import utils.ActionQueue;
import utils.MyMaths;
import java.lang.reflect.Method;
import java.util.*;

public class Arena extends GUIElement {

    public Engine engine;

    public LogCard logCard;
    private final HUD hud;

    public ArenaAIController aiController;

    public Queue queue = new Queue();
    public final ActionQueue actionQueue = new ActionQueue();

    public enum Status {
        HERO_CHOICE,
        TARGET_CHOICE,
        WAIT_ON_ANIMATION,
        WAIT_ON_TURN,
        WAIT_ON_AI,
        WAIT_ON_HUD
    }
    public int round = 0;
    public boolean pvp = true;
    public Status status = Status.HERO_CHOICE;
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

    private final int[] friendXPos = new int[]{98, 166, 234};
    private final int[] enemyXPos = new int[]{342, 410, 478, 546};
    public static int numberPositions = 3;
    public static int firstFriendPos = 0;
    public static int lastFriendPos = 2;
    public static int firstEnemyPos = 3;
    public static int lastEnemyPos = 5;
    public static int[] allPos = new int[]{0,1,2,3,4,5};
    private final int heroYPos = 80;

    public Arena(Engine e, boolean pvp) {
        super(Engine.X, Engine.Y);
        this.engine = e;
        this.round = this.engine.memory.pvpRound;
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
        this.teams.forEach(team-> {
            for (int i = 0; i < team.heroes.length; i++) {
                Hero hero = team.heroes[i];
                hero.setPosition(team.teamNumber==2?MyMaths.getMirrorPos(i):i);
                hero.arena = this;
                hero.team = team;
                hero.getEquipments().forEach(item -> item.equipToHero(hero));
                hero.changeStatTo(Stat.CURRENT_LIFE, hero.getStat(Stat.LIFE));
                hero.changeStatTo(Stat.CURRENT_MANA, hero.getStat(Stat.MANA));
                hero.changeStatTo(Stat.CURRENT_FAITH, 0);
            }
        });
        this.aiController.setTeam(this.teams.get(1));
        this.startOfMatch();
    }

    @Override
    public void update(int frame) {
        switch (status) {
            case HERO_CHOICE -> updateChooseHero();
            case TARGET_CHOICE -> updateChooseTarget();
            case WAIT_ON_ANIMATION -> updateWaitOnAnimation();
            case WAIT_ON_TURN -> resumeTurn();
            default -> this.hud.update(frame);
        }
        updateAnimations(frame);
        this.logCard.update(frame);
    }

    private void updateChooseHero() {
        if (engine.keyB._leftPressed) {
            this.switchChoice(-1);
        }
        if (engine.keyB._rightPressed) {
            this.switchChoice(1);
        }

        if (engine.keyB._enterPressed) {
            switchToArenaHUD();
        }
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
            addAction();
            aiTurn();
            //pvp logic
//                if (this.activeTeam.teamNumber == 1) {
//                    switchTeam(2);
//                    this.status = Status.HERO_CHOICE;
//                } else {
//                    this.status = Status.WAIT_ON_TURN;
//                }
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
    private void switchChoice(int dir) {
        this.activePointer = this.getNextTeamPointer(dir);
        this.changeActiveHero(this.activePointer);
    }
    private void switchToArenaHUD() {
        this.hud.activateTeamArenaOv();
        this.targetPointers = new int[0];
        this.targetMatrix = new int[0];
        this.status = Status.WAIT_ON_HUD;
    }
    public void switchToHeroChoice() {
        this.hud.disableTeamArenaOV();
        this.status = Status.HERO_CHOICE;
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
        } else {
            this.targetPointers = new int[]{};
            switch (this.activeSkill.getTargetType()) {
                case SELF:
                    this.targetPointers = new int[]{this.activeHero.getPosition()};
                    break;
                case ALL:
                    this.targetPointers = allPos;
                    break;
            }
            this.addAction();
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
        for (Hero hero: this.getAllLivingEntities()) {
            hero.startOfTurn();
            hero.prepareCast();
        }
        this.status = Status.HERO_CHOICE;
        switchTeam(1);
    }


    private void switchTeam(int id) {
        this.targetPointers = new int[0];
        this.activePointer = id == 1 ? lastFriendPos : firstEnemyPos;
        this.activeHero = getAtPosition(this.activePointer);
        this.hud.setActiveHero(this.activeHero);
        this.activeTeam = this.teams.get(id-1);
    }
    public void addAction() {
        Action action = new Action();
        action.skill = this.activeSkill;
        action.targetPositions = this.targetPointers.length==0? new int[]{this.targetPointer} : this.targetPointers;
        action.caster = this.activeHero;
        this.actionQueue.addAction(action);
        this.heroesChosen.add(this.activeHero);
        this.activeHero.getAllies().forEach(h->h.setMovedLast(false));
        this.activeHero.setMovedLast(true);

        this.targetPointer = -1;
        this.targetMatrix = new int[0];
        this.status = Status.HERO_CHOICE;
    }


    private void resumeTurn() {
        Logger.logLn("resumeTurn()");
        this.getAllLivingEntities().forEach(hero-> hero.getSkills().forEach(Skill::setToInitial));
        this.removeTheDead();
        if (this.checkEndOfMatch()) {
            return;
        }
        this.updateEntities();
        if (this.actionQueue.hasAction()) {
            Action action = this.actionQueue.getNextAction();
            if (action != null) {
                this.activeHero = action.caster;
                this.activeSkill = action.skill;
                if (this.activeHero.hasPermanentEffect(Exhausted.class) > 0) {
                    this.actionQueue.remove(action);
                    this.activeHero.removePermanentEffectOfClass(Exhausted.class);
                    this.activeHero.changeStatTo(Stat.CURRENT_ACTION, this.activeHero.getStat(Stat.MAX_ACTION));
                    this.activeHero.endOfTurn();
                    this.activeSkill = null;
                    this.nextAction = null;
                }
                if (this.activeHero.canPerform(this.activeSkill, action.targetPositions)) {
                    this.activeSkill.setTargets(getEntitiesAt(action.targetPositions));
                    this.performSkill();
                } else {
                    this.logCard.addLog("Performing " + this.activeSkill.getName() + " failed.");
                }
                this.actionQueue.remove(action);
            }
        } else {
            endOfRound();
        }
    }
    private void endOfRound() {
        this.removeTheDead();
        this.checkEndOfMatch();
//        this.logCard.openLog("End of round: ");
        for (Hero hero: getAllLivingEntities()) {
            hero.endOfRound();
        }

        this.heroesChosen = new ArrayList<>();
        EndOfRoundPayload endOfTurnPayload = new EndOfRoundPayload()
                .setArena(this);
        Connector.fireTopic(Connector.END_OF_ROUND, endOfTurnPayload);
        this.logCard.finishLog();
        this.startTurn();
    }
    private void aiTurn() {
        Logger.logLn("aiTurn()");
        this.aiController.chooseActions();
        this.status = Status.WAIT_ON_TURN;
    }
    private void performSkill() {
        this.logCard.openLog("");
        this.activeSkill.perform();
        this.targetPointers = new int[0];
        this.nextAction = "resolveSkill";
        this.status = Status.WAIT_ON_ANIMATION;
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

    public void stun(Hero target) {
        this.queue.didTurn(target);
    }
    public void moveTo(Hero e, int targetPos) {
        int indexOffset = e.isTeam2() ? numberPositions:0;

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
        Hero switchWith = group.heroes[targetPos-indexOffset];

        if (switchWith.hasPermanentEffect(Rooted.class) > 0 ||
                switchWith.hasPermanentEffect(Scoped.class) > 0) {
            return;
        }

        switchWith.setPosition(oldPosition);
        e.setPosition(targetPos);
        group.heroes[targetPos-indexOffset] = e;
        group.heroes[oldPosition-indexOffset] = switchWith;

    }
    private void removeTheDead() {
        this.queue.removeAll(this.teams.stream().flatMap(ht->ht.removeTheDead().stream()).toList());
    }
    private void updateEntities() {
        PrepareUpdatePayload prepareUpdatePayload = new PrepareUpdatePayload();
        Connector.fireTopic(Connector.PREPARE_UPDATE, prepareUpdatePayload);

        UpdatePayload updatePayload = new UpdatePayload();
        Connector.fireTopic(Connector.UPDATE, updatePayload);
    }


    //Queries
    public List<Hero> getAllLivingEntities() {
        return this.teams.stream().flatMap(ht->ht.getHeroesAsList().stream()).toList();
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
