package game.entities.heroes.darkmage.stash;

import framework.graphics.text.Color;
import game.entities.Hero;
import game.skills.*; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_Blight extends Skill {

    public S_Blight(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/darkmage/icons/blight.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.targetType = TargetType.SINGLE;  
    }


    @Override
    public int getDmg(Hero target) {
        return target.getStat(Stat.STAMINA) / 2;
    }

//    @Override
//    public String getDmgStringGUI() {
//        return DamageMode.MAGICAL.getColor().getCodeString()+"DMG + "+ Color.WHITE.getCodeString()+": 50% of target's " + Stat.STAMINA.getIconString() + ".";
//    }

    @Override
    public String getName() {
        return "Dark Blast";
    }
}
