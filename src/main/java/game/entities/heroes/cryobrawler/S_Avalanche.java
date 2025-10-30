package game.entities.heroes.cryobrawler;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.other.Frost; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_Avalanche extends Skill {

    public S_Avalanche(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/cryobrawler/icons/avalanche.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.ULT);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 1));
        this.targetType = TargetType.ALL_ENEMY;
        this.effects = List.of(new Frost(1));  
        this.level = 5;
    }

    @Override
    public String getName() {
        return "Avalanche";
    }
}
