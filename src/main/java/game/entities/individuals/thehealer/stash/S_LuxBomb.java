package game.entities.individuals.thehealer.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;

import java.util.List;

public class S_LuxBomb extends Skill {

    public S_LuxBomb(Hero hero) {
        super(hero);
        this.iconPath = "entities/thehealer/icons/luxbomb.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.targetType = TargetType.SINGLE;
        this.dmg = 1;
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.1),
                new Multiplier(Stat.MANA, 0.3));
        this.damageMode = DamageMode.MAGICAL;
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{3};
    }

    @Override
    public int getAIRating(Hero target) {
        return target.getMissingLifePercentage() / 25;
    }


    @Override
    public String getName() {
        return "Lux Bomb";
    }
}
