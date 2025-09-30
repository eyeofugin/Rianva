package game.entities.heroes.firedancer.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Bleeding;

import java.util.List;

public class S_Slash extends Skill {

    public S_Slash(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/firedancer/icons/slash.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.45));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{3};
        this.effects = List.of(new Bleeding(1));
        this.dmg = 9;
        this.faithGain = 15;
        this.critChance = 25;
        this.damageMode = DamageMode.PHYSICAL;
    }

    @Override
    public String getName() {
        return "Slash";
    }

}
