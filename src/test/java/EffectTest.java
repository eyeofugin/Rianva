import framework.states.Arena;
import framework.states.GameMode;
import framework.states.Memory;
import game.Libraries;
import game.effects.Effect;
import game.effects.status.Bleeding;
import game.effects.status.Blight;
import game.entities.DraftBuilder;
import game.entities.Hero;
import game.entities.HeroTeam;
import game.libraries.EffectLibrary;
import game.libraries.SkillLibrary;
import game.skills.logic.DamageMode;
import game.skills.logic.DamageType;
import game.skills.logic.Stat;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EffectTest {

    Arena arena;
    Hero hero;

    @Test
    void testBleeding() {
        setup();
        hero.getStats().put(Stat.CURRENT_LIFE, 10);

        Bleeding bleeding = EffectLibrary.getEffect(Bleeding.class.getName(), 0, 1, null);
        hero.addEffect(bleeding, hero);

        hero.regain();

        assert hero.getStat(Stat.CURRENT_LIFE) == 10;
    }

    @Test
    void testBlight() {
        setup();

        Blight blight = EffectLibrary.getEffect(Blight.class.getName(), 0, 0, null);
        hero.addEffect(blight, hero);

        hero.damage(10, DamageType.NORMAL, DamageMode.PHYSICAL, null, null, null, null, 100);

        assert hero.getStat(Stat.LIFE) - hero.getStat(Stat.CURRENT_LIFE) > 10;

        hero.addSkill(SkillLibrary.getSkillByName("S_Unlife"));
        hero.resetResources();
        blight.used = false;

        hero.damage(10, DamageType.NORMAL, DamageMode.PHYSICAL, null, null, null, null, 100);

        assert hero.getStat(Stat.LIFE) == hero.getStat(Stat.CURRENT_LIFE);

    }


    void setup() {
        Libraries.init();
        Memory memory = mockMemory();
        memory.teams = new HeroTeam[]{mockTeam1(),mockTeam2()};
        arena = mockArena(memory);
        arena.start();
        hero = arena.getAtPosition(0);
    }

    Arena mockArena(Memory memory) {
        return new Arena(memory, true);
    }

    Memory mockMemory() {
        return new Memory(GameMode.PVP);
    }

    HeroTeam mockTeam1() {
        return mockTeam(List.of("0","0","0","0"), 1,1);
    }
    HeroTeam mockTeam2() {
        return mockTeam(List.of("0","0","0","0"), -1,2);
    }
    HeroTeam mockTeam(List<String> names, int fill, int nr) {
        return new HeroTeam(fill, nr, DraftBuilder.getHeroes(names));
    }
}
