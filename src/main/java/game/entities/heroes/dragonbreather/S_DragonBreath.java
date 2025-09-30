package game.entities.heroes.dragonbreather;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Burning;

import java.util.List;

public class S_DragonBreath extends Skill {

    public S_DragonBreath(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/dragonbreather/icons/dragonbreath.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.5));
        this.effects = List.of(new Burning(2));
        this.targetType = TargetType.ALL_TARGETS;
        this.possibleCastPositions = new int[]{2};
        this.possibleTargetPositions = new int[]{3,4};
        this.damageMode = DamageMode.MAGICAL;
        this.manaCost = 6;
        this.level = 2;
    }

    @Override
    public String getName() {
        return "Dragon Breath";
    }
}
