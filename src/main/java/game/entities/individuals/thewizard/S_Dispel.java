package game.entities.individuals.thewizard;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Blight;

import java.util.List;

public class S_Dispel extends Skill {

    public S_Dispel(Hero hero) {
        super(hero);
        this.iconPath = "entities/thewizard/icons/dispel.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{3,4};
        this.manaCost = 4;
    }

    @Override
    public int getAIRating(Hero target) {
        if (target.getSecondaryResource() == null) {
            return -2;
        } else if (target.getSecondaryResource() == Stat.MANA) {
            return 5;
        } else {
            return 3;
        }
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "Target loses 25% of either "+Stat.FAITH.getIconString()+","+Stat.MANA.getIconString()+" or "+Stat.HALO.getIconString()+".";
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        if (target.getSecondaryResource().equals(Stat.MANA)) {
            int resourceLoss = target.getStat(Stat.MANA)/4;
            target.addResource(Stat.CURRENT_MANA, Stat.MANA, -1*resourceLoss, this.hero);
        } else if (target.getSecondaryResource().equals(Stat.FAITH)) {
            int resourceLoss = target.getStat(Stat.FAITH)/4;
            target.addResource(Stat.CURRENT_FAITH, Stat.FAITH, -1*resourceLoss, this.hero);
        } else if (target.getSecondaryResource().equals(Stat.HALO)) {
            target.addResource(Stat.CURRENT_HALO, Stat.HALO, -1, this.hero);
        }
    }

    @Override
    public String getName() {
        return "Dispel";
    }
}
