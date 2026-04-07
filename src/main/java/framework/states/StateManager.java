package framework.states;

import framework.Engine;
import game.entities.DraftBuilder;
import game.entities.Hero;
import game.entities.HeroTeam;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StateManager {

  public static final int ARENA = 1;
  public static final int DRAFT = 2;
  public static final int DUNGEON = 3;
  public static final int DEV = 4;
  public static final int TEAM_BUILDER = 5;
  public static final int PVP_DRAFT = 6;
  public Engine engine;
  public Memory memory;
  private final List<State> scenes = new ArrayList<>();

  public StateManager(Engine e) {
    this.engine = e;
    this.memory = engine.memory;
    //        dungeonDraft();
//    dev();
    //        pvpDraft();
    //        arena();
    abilityGlossary();
  }

  private void pvpDraft() {
    HeroTeam left = new HeroTeam(1, new Hero[0], 1);
    HeroTeam right = new HeroTeam(-1, new Hero[0], 2);

    this.memory = new Memory(GameMode.PVP);
    this.memory.teams = new HeroTeam[] {left, right};

    this.scenes.add(new PvpDraft(this.memory));
  }

  private void arena() {

    this.memory.teams =
        new HeroTeam[] {DraftBuilder.getRandomTeam(1, 1), DraftBuilder.getRandomTeam(-1, 2)};
    Arena arena = new Arena(memory, false);
    this.scenes.add(arena);
  }

  //    private void dungeonDraft() {
  //        HeroTeam dungeonTeam = new HeroTeam(1, new Hero[0], 1);
  //        this.engine.memory = new Memory(GameMode.DUNGEON);
  //        this.engine.memory.dungeonTeam = dungeonTeam;
  //
  //        this.draft = new Draft(this.engine);
  //        draft.setActive(true);
  //        this.activeSceneId = DRAFT;
  //        this.scenes.add(draft);
  //
  //    }
  //    private void draft() {
  //        HeroTeam left = new HeroTeam(1,new Hero[0], 1);
  //        HeroTeam right = DraftBuilder.getRandomTeam(-1, 2);
  //
  //        this.engine.memory = new Memory(GameMode.PVP);
  //        this.engine.memory.teams = new HeroTeam[]{left, right};
  //
  //        this.draft = new Draft(this.engine);
  //        draft.setActive(true);
  //        this.activeSceneId = DRAFT;
  //        this.scenes.add(draft);
  //    }

  private void dev() {
    DevState devState = new DevState(this.memory);
    devState.setActive(true);
    this.scenes.add(devState);
  }

  private void abilityGlossary() {
    AbilityGlossaryState state = new AbilityGlossaryState(this.memory);
    state.setActive(true);
    this.scenes.add(state);
  }

  //    public void joinDungeon() {
  //        this.dungeon = new Dungeon(this.engine);
  //        this.dungeon.setActive(true);
  //        this.activeSceneId = DUNGEON;
  //        this.scenes.add(dungeon);
  //    }
  //    public void returnToDungeon() {
  //        this.activeSceneId = DUNGEON;
  //        this.arena = null;
  //        this.engine.memory.dungeonTeam.getHeroesAsList().forEach(Hero::leaveArena);
  //        Connector.cleanUpSubscriptions();
  //    }
  //
  //    public void joinArena(Memory memory) {
  //        this.arena = new Arena(this.engine, false);
  //        if (memory.mode.equals(GameMode.DUNGEON)) {
  //            arena.setTeams(memory.dungeonTeam, memory.dungeonEnemies);
  //        } else if (memory.mode.equals(GameMode.PVP)) {
  //            arena.pvp = true;
  //            arena.setTeams(memory.teams[0], memory.teams[1]);
  //        }
  //        this.activeSceneId = ARENA;
  //        this.scenes.add(arena);
  //    }
  //
  //    public void joinTeamBuilding() {
  //        this.teamBuilder = new TeamBuilder(this.engine);
  //        this.activeSceneId = TEAM_BUILDER;
  //        this.scenes.add(teamBuilder);
  //    }

  public int[] render() {
    if (this.scenes.isEmpty()) {
      return new int[0];
    }
    return this.scenes.get(0).render();
  }

  public void update(int frame) {
    State state = this.scenes.getFirst();
    if (state.nextState != 0) {
      State nextState = getNewState(state.nextState);
      if (state.close) {
        state.finish();
        this.scenes.removeFirst();
      }
      this.scenes.add(nextState);
    } else if (state.goBack) {
      state.finish();
      this.scenes.removeFirst();
    } else {
      state.update(frame);
    }
  }

  public State getNewState(int st) {
    switch (st) {
      case ARENA -> {
        return new Arena(memory, false);
      }
      case DRAFT -> {
        return new Draft(memory);
      }
      case DUNGEON -> {
        return new Dungeon(memory);
      }
      case PVP_DRAFT -> {
        return new PvpDraft(memory);
      }
      case TEAM_BUILDER -> {
        return new TeamBuilder(memory);
      }
    }
    return new DevState(memory);
  }
}
