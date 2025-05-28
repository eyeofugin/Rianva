package game.entities.individuals.phoenixguy;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.effects.Exalted;

import java.util.List;

public class S_PhoenixFlames extends Skill {

    public S_PhoenixFlames(Hero hero) {
        super(hero);
        this.iconPath = "entities/phoenixguy/icons/phoenixflames.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.ULT);
        this.targetType = TargetType.SELF;
        this.possibleCastPositions = new int[]{0,1,2};
        this.effects = List.of(new Exalted(3));
        this.faithRequirement = 100;
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
