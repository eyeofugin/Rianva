package game.entities.goons.sworddude;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.status.Bleeding;
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_Slash extends Skill {

    public S_Slash(Hero hero) {
        super(hero);
        this.iconPath = "entities/goons/sworddude/icons/slash.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.targetType = TargetType.SINGLE;
        this.effects = List.of(new Bleeding(1));
        this.dmgMultipliers = List.of(new Multiplier(Stat.ATTACK, 1.0));
    }
    @Override
    public String getName() {
        return "Slash";
    }
}
