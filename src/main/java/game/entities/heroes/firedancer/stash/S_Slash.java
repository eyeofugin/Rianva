package game.entities.heroes.firedancer.stash;

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
        this.iconPath = "entities/heroes/firedancer/icons/slash.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.ATTACK, 0.45));
        this.targetType = TargetType.SINGLE;
        this.effects = List.of(new Bleeding(1));
        this.dmg = 9;
        this.critChance = 25;  
    }

    @Override
    public String getName() {
        return "Slash";
    }

}
