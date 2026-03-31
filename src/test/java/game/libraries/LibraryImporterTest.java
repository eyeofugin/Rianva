package game.libraries;

import org.junit.jupiter.api.Test;

public class LibraryImporterTest {

    @Test
    public void test_libraries() {
        test_EffectLibrary();
        test_HeroLibrary();
        test_SkillLibrary();
    }

    @Test
    public void test_SkillLibrary() {
        SkillLibrary.init();
    }

    @Test
    public void test_HeroLibrary() {
        HeroLibrary.init();
    }

    @Test
    public void test_EffectLibrary() {
        EffectLibrary.init();
    }
}
