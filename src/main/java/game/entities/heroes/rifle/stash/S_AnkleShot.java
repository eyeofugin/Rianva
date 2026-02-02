package game.entities.heroes.rifle.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_AnkleShot extends Skill {

    public S_AnkleShot(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/rifle/icons/ankleshot.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.targetType = TargetType.SINGLE;
        this.dmg = 11;
        this.dmgMultipliers = List.of(new Multiplier(Stat.ATTACK, 0.1));  
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.addToStat(Stat.SPEED, -2);
    }




    @Override
    public String getDescriptionFor(Hero hero) {
        return "Target gets -2" + Stat.SPEED.getIconString() + ".";
    }


    @Override
    public String getName() {
        return "Ankle Shot";
    }
}
