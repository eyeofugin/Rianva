package game.entities.heroes.duelist;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Injured;

import java.util.List;

public class S_Slash extends Skill {

    public S_Slash(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/duelist/icons/slash.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.2), new Multiplier(Stat.SPEED, 0.15));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{3};
        this.damageMode = DamageMode.PHYSICAL;
        this.effects = List.of(new Injured(2));
    }

    @Override
    public String getName() {
        return "Slash";
    }
}
