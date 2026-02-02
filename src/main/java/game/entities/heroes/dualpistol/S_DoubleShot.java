package game.entities.heroes.dualpistol;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_DoubleShot extends Skill {

    public S_DoubleShot(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/dualpistol/icons/doubleshot.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.targetType = TargetType.SINGLE;
        this.countAsHits = 2;
        this.dmgMultipliers = List.of(new Multiplier(Stat.ATTACK, 0.2));  
    }



    @Override
    public String getDescriptionFor(Hero hero) {
        return "2 Hits.";
    }
    @Override
    public String getName() {
        return "Double Shot";
    }
}
