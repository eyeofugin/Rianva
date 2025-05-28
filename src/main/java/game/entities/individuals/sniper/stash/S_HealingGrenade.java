package game.entities.individuals.sniper.stash;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_HealingGrenade extends Skill {

    public S_HealingGrenade(Hero hero) {
        super(hero);
        this.iconPath = "entities/sniper/icons/healinggrenade.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.ALL_TARGETS;
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{0,1,2};
        this.cdMax = 3;
    }

    @Override
    public int getAIRating(Hero target) {
        return 1 + target.getMissingLifePercentage() / 50;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int missingLife = target.getStat(Stat.LIFE) - target.getStat(Stat.CURRENT_LIFE);
        target.heal(this.hero, missingLife / 3, this, false);
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Heals all allies for 1/3 of their missing " + Stat.LIFE.getIconString() + ".";
    }


    @Override
    public String getName() {
        return "Healing Grenade";
    }
}
