package game.entities.goons.axedude;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;

import java.util.List;

public class S_Chop extends Skill {

    public S_Chop(Hero hero) {
        super(hero);
        this.iconPath = "entities/goons/axedude/icons/chop.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.targetType = TargetType.SINGLE;
        this.possibleTargetPositions = new int[]{3};
        this.possibleCastPositions = new int[]{1,2};
        this.damageMode = DamageMode.PHYSICAL;
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 1.0));
    }

    @Override
    public String getName() {
        return "Slash";
    }
}
