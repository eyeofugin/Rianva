package game.entities.individuals.thehealer;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_ImbueWithLight extends Skill {

    public S_ImbueWithLight(Hero hero) {
        super(hero);
        this.iconPath = "entities/thehealer/icons/imbuewithlight.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.ULT);
        this.targetType = TargetType.ALL_TARGETS;
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{0,1,2};
        this.manaCost = 6;
        this.healMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.2));
        this.shieldMultipliers = List.of(new Multiplier(Stat.MANA, 0.2),
                new Multiplier(Stat.LIFE, 0.2));
        this.level = 5;
    }
    @Override
    public int getAIRating(Hero target) {
        return target.getMissingLifePercentage() / 50;
    }

    @Override
    public String getName() {
        return "Imbue with light";
    }
}
