package game.entities.heroes.longsword;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Bleeding;

import java.util.List;

public class S_Stab extends Skill {

    public S_Stab(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/longsword/icons/stab.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{3};
        this.damageMode = DamageMode.PHYSICAL;
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.25));
        this.effects = List.of(new Bleeding(1));

    }

    @Override
    public String getName() {
        return "Stab";
    }
}
