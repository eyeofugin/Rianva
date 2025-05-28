package game.entities.individuals.darkmage;

import framework.graphics.containers.ActiveCharCard;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;

import java.util.List;
import java.util.Map;

public class S_DarkSchemes extends Skill {

    public S_DarkSchemes(Hero hero) {
        super(hero);
        this.iconPath = "entities/darkmage/icons/darkschemes.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.ULT);
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{3,4,5};
        this.manaCost = 10;
        this.level = 5;
    }


    @Override
    public int getAIRating(Hero target) {
        Map<Stat, Integer> statChanges = target.getStatChanges();
        if (statChanges.isEmpty()) {
            return -3;
        }
        int positiveValue = 0;
        for (Map.Entry<Stat, Integer> stat : statChanges.entrySet()) {
            if (stat != null) {
                if (stat.getKey().equals(Stat.ACCURACY)) {
                    positiveValue += stat.getValue() / 10;
                } else {
                    positiveValue += stat.getValue();
                }
            }
        }
        int weighted = positiveValue / 2;
        return target.isTeam2() ? -1 * weighted : weighted;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        for (Map.Entry<Stat, Integer> entry : target.getStatChanges().entrySet()) {
            target.addToStat(entry.getKey(), -2*entry.getValue());
        }
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Reverts target's stat changes.";
    }

    @Override
    public String getName() {
        return "Dark Schemes";
    }
}
