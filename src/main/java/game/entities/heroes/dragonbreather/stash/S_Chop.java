package game.entities.heroes.dragonbreather.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Bleeding;

import java.util.List;

public class S_Chop extends Skill {

    public S_Chop(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/dragonbreather/icons/axeswing.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.3), new Multiplier(Stat.MAGIC, 0.2));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{3};
        this.effects = List.of(new Bleeding(1));
        this.dmg = 10;
        this.damageMode = DamageMode.PHYSICAL;
    }

    @Override
    public String getName() {
        return "Chop";
    }
}
