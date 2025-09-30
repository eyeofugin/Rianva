package game.entities.heroes.battleaxe;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Bleeding;

import java.util.List;

public class S_BloodCleave extends Skill {

    public S_BloodCleave(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/battleaxe/icons/awesomeaxe.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.6));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{2};
        this.possibleTargetPositions = new int[]{3};
        this.damageMode = DamageMode.PHYSICAL;
        this.effects = List.of(new Bleeding(2));
    }

    @Override
    public int getAIRating(Hero target) {
        return 1;
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return " ";
    }

    @Override
    public String getName() {
        return "Blood Cleave";
    }
}
