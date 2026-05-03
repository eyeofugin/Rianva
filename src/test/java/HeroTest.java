import framework.states.Arena;
import framework.states.GameMode;
import framework.states.Memory;
import game.Libraries;
import game.entities.DraftBuilder;
import game.entities.Hero;
import game.entities.HeroTeam;
import game.libraries.HeroLibrary;
import game.libraries.SkillLibrary;
import game.skills.Skill;
import org.junit.jupiter.api.Test;

import java.util.List;

class HeroTest {

  @Test
  void baseTest() {
    Libraries.init();
    Memory memory = mockMemory();
    Arena arena = mockArena(memory);

    Hero hero = HeroLibrary.getHero("0");
    arena.addHero(hero, 3);
    arena.addHero(HeroLibrary.getHero("0"),4);

    arena.devStart();
  }

  @Test
  void testEvoker() {
    Libraries.init();
    Memory memory = mockMemory();
    memory.teams = new HeroTeam[]{mockTeam1(),mockTeam2()};
    Arena arena = mockArena(memory);
    arena.start();

  }

  Hero mockDummy() {
    return HeroLibrary.getHero("0");
  }
  Skill mockSkill(String name) {
    return SkillLibrary.getSkillByName(name);
  }
  HeroTeam mockTeam1() {
    return mockTeam(List.of("4","1","3","2"), 1,1);
  }
  HeroTeam mockTeam2() {
    return mockTeam(List.of("8","7","6","5"), -1,2);
  }
  HeroTeam mockTeam(List<String> names, int fill, int nr) {
    return new HeroTeam(fill, nr, DraftBuilder.getHeroes(names));
  }

  Arena mockArena(Memory memory) {
    return new Arena(memory, true);
  }

  Memory mockMemory() {
    return new Memory(GameMode.PVP);
  }


}
