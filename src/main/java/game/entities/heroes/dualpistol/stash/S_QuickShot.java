package game.entities.heroes.dualpistol.stash;

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
        this.iconPath = "entities/heroes/dualpistol/icons/quickshot.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;
        this.dmg = 2;
        this.dmgMultipliers = List.of(new Multiplier(Stat.ATTACK, 0.4));
    }



    @Override
    public String getName() {
        return "Quick Shot";
    }
}
