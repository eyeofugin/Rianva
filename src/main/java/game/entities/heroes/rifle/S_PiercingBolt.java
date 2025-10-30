package game.entities.heroes.rifle;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_PiercingBolt extends Skill {

    public S_PiercingBolt(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/rifle/icons/piercingbolt.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.targetType = TargetType.SINGLE;
        this.lethality = 50;
        this.dmgMultipliers = List.of(new Multiplier(Stat.ATTACK, 0.35));  
    }

    @Override
    public String getName() {
        return "Piercing Arrow";
    }
}
