package game.entities.individuals.rifle;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.statusinflictions.Injured;

import java.util.List;

public class S_Barrage extends Skill {

    public S_Barrage(Hero hero) {
        super(hero);
        this.iconPath = "entities/rifle/icons/barrage.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.ULT);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.15));
        this.effects = List.of(new Injured(1));
        this.possibleCastPositions = new int[]{0,1};
        this.possibleTargetPositions = new int[]{3,4};
        this.targetType = TargetType.ALL_TARGETS;
        this.dmg = 10;
        this.damageMode = DamageMode.PHYSICAL;
        this.cdMax = 4;
        this.level = 5;
    }

    @Override
    public String getName() {
        return "Barrage";
    }
}
