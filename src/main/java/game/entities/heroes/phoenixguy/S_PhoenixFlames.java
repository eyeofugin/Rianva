package game.entities.heroes.phoenixguy;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.logic.SkillTag;
import game.skills.logic.TargetType;
import game.skills.changeeffects.effects.other.Burning;

import java.util.List;

public class S_PhoenixFlames extends Skill {

    public S_PhoenixFlames(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/phoenixguy/icons/phoenixflames.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.ULT);
        this.targetType = TargetType.SELF;
        this.level = 5;
    }

    @Override
    public int getAIRating(Hero target) {
        return 10;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        for (Hero hero : this.hero.getEnemies()) {
            hero.addEffect(new Burning(5), this.hero);
        }
    }

    @Override
    public String getName() {
        return "Phoenix Flames";
    }



    @Override
    public String getDescriptionFor(Hero hero) {
        return "All enemies get " + Burning.getStaticIconString() + "(5).";
    }

}
