package framework.states;

import framework.Engine;
import framework.connector.Connector;
import framework.graphics.GUIElement;
import game.entities.DraftBuilder;
import game.entities.Hero;
import game.entities.HeroTeam;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class StateManager {

    public static final int ARENA = 1;
    public static final int DRAFT = 2;
    public static final int DUNGEON = 3;
    public static final int DEV = 4;
    public static final int TEAM_BUILDER = 5;
    public static final int PVP_DRAFT = 6;
    public Engine engine;
    private int activeSceneId = 0;
    private Arena arena;
    private Draft draft;
    private Dungeon dungeon;
    private DevState devState;
    private PvpDraft pvpDraft;
    private TeamBuilder teamBuilder;
    private List<GUIElement> scenes = new ArrayList<>();

    public StateManager(Engine e) {
        this.engine = e;
//        dungeonDraft();
            dev();
//        pvpDraft();
    }

    private void pvpDraft() {
        HeroTeam left = new HeroTeam(1,new Hero[0], 1);
        HeroTeam right = new HeroTeam(-1,new Hero[0], 2);

        this.engine.memory = new Memory(GameMode.PVP);
        this.engine.memory.teams = new HeroTeam[]{left, right};

        this.pvpDraft = new PvpDraft(this.engine);
        pvpDraft.setActive(true);
        this.activeSceneId = PVP_DRAFT;
        this.scenes.add(pvpDraft);
    }
    private void dungeonDraft() {
        HeroTeam dungeonTeam = new HeroTeam(1, new Hero[0], 1);
        this.engine.memory = new Memory(GameMode.DUNGEON);
        this.engine.memory.dungeonTeam = dungeonTeam;

        this.draft = new Draft(this.engine);
        draft.setActive(true);
        this.activeSceneId = DRAFT;
        this.scenes.add(draft);

    }
    private void draft() {
        HeroTeam left = new HeroTeam(1,new Hero[0], 1);
        HeroTeam right = DraftBuilder.getRandomTeam(-1, 2);

        this.engine.memory = new Memory(GameMode.PVP);
        this.engine.memory.teams = new HeroTeam[]{left, right};

        this.draft = new Draft(this.engine);
        draft.setActive(true);
        this.activeSceneId = DRAFT;
        this.scenes.add(draft);
    }

    private void dev() {
        this.devState = new DevState(this.engine);
        devState.setActive(true);
        this.activeSceneId = DEV;
        this.scenes.add(devState);
    }

    public void joinDungeon() {
        this.dungeon = new Dungeon(this.engine);
        this.dungeon.setActive(true);
        this.activeSceneId = DUNGEON;
        this.scenes.add(dungeon);
    }
    public void returnToDungeon() {
        this.activeSceneId = DUNGEON;
        this.arena = null;
        this.engine.memory.dungeonTeam.getHeroesAsList().forEach(Hero::leaveArena);
        Connector.cleanUpSubscriptions();
    }

    public void joinArena(Memory memory) {
        this.arena = new Arena(this.engine, false);
        if (memory.mode.equals(GameMode.DUNGEON)) {
            arena.setTeams(memory.dungeonTeam, memory.dungeonEnemies);
        } else if (memory.mode.equals(GameMode.PVP)) {
            arena.pvp = true;
            arena.setTeams(memory.teams[0], memory.teams[1]);
        }
        this.activeSceneId = ARENA;
        this.scenes.add(arena);
    }

    public void joinTeamBuilding() {
        this.teamBuilder = new TeamBuilder(this.engine);
        this.activeSceneId = TEAM_BUILDER;
        this.scenes.add(teamBuilder);
    }

    public int[] render(){
        Optional<GUIElement> activeScene = this.scenes.stream()
                .filter(gui->gui.id ==  this.activeSceneId)
                .findAny();
        if (activeScene.isPresent()) {
            return activeScene.get().render();
        }
        return new int[0];
    }
    public void update(int frame) {
        Optional<GUIElement> activeScene = this.scenes.stream()
                .filter(gui->gui.id ==  this.activeSceneId)
                .findAny();
        activeScene.ifPresent(guiElement -> guiElement.update(frame));
        switch (this.activeSceneId) {
            case ARENA -> {
                if (this.arena.finished) {
                    this.arena = null;
                    this.scenes.removeIf(Objects::isNull);
                    returnToDungeon();
                }
            }
            case DRAFT -> {
                if (this.draft.finished) {
                    if (this.engine.memory.mode.equals(GameMode.DUNGEON)) {
                        joinDungeon();
                    } else if (this.engine.memory.mode.equals(GameMode.PVP)) {
                        joinArena(this.engine.memory);
                    }
                }
            }
            case PVP_DRAFT -> {
                if (this.pvpDraft.finished) {
                    this.pvpDraft = null;
                    this.scenes.removeIf(Objects::isNull);
                    joinTeamBuilding();
                }
            }
            case TEAM_BUILDER -> {
                if (this.teamBuilder.finished) {
                    this.teamBuilder = null;
                    this.scenes.removeIf(Objects::isNull);
                    joinArena(this.engine.memory);
                }
            }
            case DUNGEON -> {
                if (this.dungeon.startEncounter) {
                    this.dungeon.startEncounter = false;
                    joinArena(this.engine.memory);
                } else if (this.dungeon.startLoot) {
                    this.dungeon.startLoot = false;
                    joinTeamBuilding();
                }
            }
        }
    }
}