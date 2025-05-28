package game.entities.individuals.firedancer;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.statusinflictions.Bleeding;

import java.util.List;

public class S_FlamingSwing extends Skill {

    public S_FlamingSwing(Hero hero) {
        super(hero);
        this.iconPath = "entities/firedancer/icons/flamingswing.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.3));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{3,4};
        this.effects = List.of(new Burning(2));
        this.dmg = 3;
        this.damageMode = DamageMode.PHYSICAL;
        this.faithGain = 10;
    }
    @Override
    public String getName() {
        return "Flaming Swing";
    }
}
