package game.entities.goons.bowdude;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;

import java.util.List;

public class S_QuickShot extends Skill {

    public S_QuickShot(Hero hero) {
        super(hero);
        this.iconPath = "entities/goons/bowdude/icons/quickshot.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.targetType = TargetType.SINGLE;
        this.possibleTargetPositions = new int[]{3,4,5};
        this.possibleCastPositions = new int[]{0,1,2};
        this.damageMode = DamageMode.PHYSICAL;
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 1.0));
        this.priority = 1;
    }

    @Override
    public String getName() {
        return "Quick Shot";
    }
}
