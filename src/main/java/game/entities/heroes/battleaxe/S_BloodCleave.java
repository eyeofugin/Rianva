package game.entities.heroes.battleaxe;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.status.Bleeding; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_BloodCleave extends Skill {

    public S_BloodCleave(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/battleaxe/icons/awesomeaxe.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.ATTACK, 0.6));
        this.targetType = TargetType.SINGLE;  
        this.effects = List.of(new Bleeding(2));
    }

    @Override
    public int getAIRating(Hero target) {
        return 1;
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return " ";
    }

    @Override
    public String getName() {
        return "Blood Cleave";
    }
}
