package game.entities.heroes.rifle;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;

import java.util.List;

public class S_PiercingBolt extends Skill {

    public S_PiercingBolt(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/rifle/icons/piercingbolt.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{3,4};
        this.lethality = 50;
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.35));
        this.damageMode = DamageMode.PHYSICAL;
    }

    @Override
    public String getName() {
        return "Piercing Arrow";
    }
}
