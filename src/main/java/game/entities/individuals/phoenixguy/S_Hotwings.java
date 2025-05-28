package game.entities.individuals.phoenixguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.effects.Combo;

import java.util.List;

public class S_Hotwings extends Skill {

    public S_Hotwings(Hero hero) {
        super(hero);
        this.iconPath = "entities/phoenixguy/icons/hotwings.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.5));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{3,4};
        this.effects = List.of(new Burning(2));
        this.dmg = 4;
        this.faithGain = 10;
        this.damageMode = DamageMode.MAGICAL;
    }
    @Override
    public String getName() {
        return "Hot Wings";
    }
}
