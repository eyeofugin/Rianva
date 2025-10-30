package game.entities.heroes.eldritchguy;

import framework.graphics.text.Color;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_TentacleGrab extends Skill {

    public S_TentacleGrab(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/eldritchguy/icons/tentaclegrab.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;  
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.3));
        this.level = 2;
        this.move = -1;
    }


    @Override
    public int getDmg(Hero target) {
        return target.getStat(Stat.LIFE) * 10 / 100;
    }
    @Override
    public int getAIRating(Hero target) {
        int rating = 0;
        if (target.getPosition() == target.team.getFirstPosition()) {
            return --rating;
        }
        if (target.getCurrentLifePercentage() < 50) {
            rating += 2;
        }
        if (this.hero.arena.getAtPosition(target.team.getFirstPosition()).getCurrentLifePercentage() < 50) {
            rating -= 2;
        }
        return rating;
    }

    @Override
    public String getDmgStringGUI() {
        return "";//return DamageMode.PHYSICAL.getColor().getCodeString()+"DMG"+ Color.WHITE.getCodeString()+": 10% of target's "+ Stat.LIFE.getReference() + ".";
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Pull 1.";
    }

    @Override
    public String getName() {
        return "Tentacle Grab";
    }
}
