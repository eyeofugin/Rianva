package game.entities.heroes.dragonbreather;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Burning;

import java.util.List;

public class S_BlazingCleaver extends Skill {

    public S_BlazingCleaver(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/dragonbreather/icons/blazingcleaver.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.5));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{3,4};
        this.effects = List.of(new Burning(1));
        this.damageMode = DamageMode.PHYSICAL;
    }

    @Override
    public String getName() {
        return "Blazing Cleaver";
    }
}
