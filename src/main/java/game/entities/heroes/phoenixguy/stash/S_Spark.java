package game.entities.heroes.phoenixguy.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_Spark extends Skill {

    public S_Spark(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/phoenixguy/icons/spark.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MANA, 0.1));
        this.targetType = TargetType.SINGLE;
        this.dmg = 5;  
    }

    @Override
    public String getName() {
        return "Spark";
    }
}
