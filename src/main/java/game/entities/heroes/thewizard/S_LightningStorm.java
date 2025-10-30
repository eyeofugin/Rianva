package game.entities.heroes.thewizard;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_LightningStorm extends Skill {

    public S_LightningStorm(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/thewizard/icons/lightningstorm.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.ULT);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.45));
        this.targetType = TargetType.ALL_ENEMY;  
        this.level = 5;
    }

    @Override
    public String getName() {
        return "Lightning Storm";
    }
}
