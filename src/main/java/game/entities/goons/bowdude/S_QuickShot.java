package game.entities.goons.bowdude;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

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
        this.dmgMultipliers = List.of(new Multiplier(Stat.ATTACK, 1.0));
    }

    @Override
    public String getName() {
        return "Quick Shot";
    }
}
