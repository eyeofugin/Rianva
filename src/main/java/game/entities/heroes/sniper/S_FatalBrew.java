package game.entities.heroes.sniper;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;
import java.util.Map;

public class S_FatalBrew extends Skill {

    public S_FatalBrew(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/sniper/icons/fatalbrew.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.ULT);
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{3,4};
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
            if (entry.getValue() > 0) {
                target.addToStat(entry.getKey(), -1*entry.getValue());
            }
        }
        target.removePositiveEffects();
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Remove buffs and positive stat changes.";
    }

    @Override
    public String getName() {
        return "Fatal Brew";
    }
}
