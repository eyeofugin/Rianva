package framework.states;

import framework.Engine;
import framework.graphics.GUIElement;
import game.entities.DraftBuilder;
import game.entities.Hero;
import game.entities.HeroTeam;
import game.entities.individuals.angelguy.H_AngelGuy;
import game.entities.individuals.battleaxe.H_BattleAxe;
import game.entities.individuals.burner.H_Burner;
import game.entities.individuals.dualpistol.H_DualPistol;
import game.entities.individuals.eldritchguy.H_EldritchGuy;
import game.entities.individuals.firedancer.H_FireDancer;
import game.entities.individuals.rifle.H_Rifle;
import game.entities.individuals.thehealer.H_TheHealer;
import game.skills.Skill;

import java.util.ArrayList;
import java.util.List;

public class StateManager {

    public Engine engine;
    private GUIElement activeScene;
    private List<GUIElement> sceneMemory = new ArrayList<>();

    public StateManager(Engine e) {
        this.engine = e;
//        dungeonDraft();
            dev();
    }

    private void pvpDraft() {
        HeroTeam left = new HeroTeam(1,new Hero[0], 1);
        HeroTeam right = new HeroTeam(-1,new Hero[0], 2);

        this.engine.memory = new Memory(GameMode.PVP);
        this.engine.memory.teams = new HeroTeam[]{left, right};

        PvpDraft draft = new PvpDraft(this.engine);
        draft.setActive(true);
        this.activeScene = draft;
    }
    private void dungeonDraft() {
        HeroTeam dungeonTeam = new HeroTeam(1, new Hero[0], 1);
        this.engine.memory = new Memory(GameMode.DUNGEON);
        this.engine.memory.dungeonTeam = dungeonTeam;

        Draft draft = new Draft(this.engine);
        draft.setActive(true);
        this.activeScene = draft;

    }
    private void draft() {
        HeroTeam left = new HeroTeam(1,new Hero[0], 1);
        HeroTeam right = DraftBuilder.getRandomTeam(-1, 2);

        this.engine.memory = new Memory(GameMode.PVP);
        this.engine.memory.teams = new HeroTeam[]{left, right};

        Draft draft = new Draft(this.engine);
        draft.setActive(true);
        this.activeScene = draft;
    }

    private void dev() {
        DevState devState = new DevState(this.engine);
        devState.setActive(true);
        this.activeScene = devState;
    }

    public void joinDungeon() {
        this.activeScene = new Dungeon(this.engine);
        this.activeScene.setActive(true);
    }

    public void joinArena(Memory memory) {
        Arena arena = new Arena(this.engine, false);
        if (this.engine.memory.mode.equals(GameMode.DUNGEON)) {
            arena.setTeams(this.engine.memory.dungeonTeam, this.engine.memory.dungeonEnemies);
        } else if (this.engine.memory.mode.equals(GameMode.PVP)) {
            arena.round = memory.pvpRound;
            arena.setTeams(memory.teams[0], memory.teams[1]);
        }
        this.activeScene = arena;
    }

    public void joinTeamBuilding() {
        this.activeScene = new TeamBuilder(this.engine);
    }

    private void checkEndOfDraft() {
        if (this.activeScene instanceof Draft) {
            if (((Draft) this.activeScene).finished) {
                if (this.engine.memory.mode.equals(GameMode.DUNGEON)) {
                    joinDungeon();
                } else if (this.engine.memory.mode.equals(GameMode.PVP)) {
                    joinArena(this.engine.memory);
                }
            }
        }
        if (this.activeScene instanceof PvpDraft) {
            if (((PvpDraft) this.activeScene).finished) {
                joinTeamBuilding();
            }
        }
        if (this.activeScene instanceof TeamBuilder) {
            if (((TeamBuilder) this.activeScene).finished) {
                joinArena(this.engine.memory);
            }
        }
        if (this.activeScene instanceof Arena) {
            if (((Arena) this.activeScene).finished) {
//                joinDraft(this.engine.memory);
            }
        }
        if (this.activeScene instanceof Dungeon) {
            if (((Dungeon) this.activeScene).startEncounter) {
                joinArena(this.engine.memory);
            } else if (((Dungeon) this.activeScene).startLoot) {
                joinTeamBuilding();
            }
        }
    }

    public int[] render(){

        return this.activeScene.render();
    }
    public void update(int frame) {
        this.activeScene.update(frame);
        checkEndOfDraft();
    }
}