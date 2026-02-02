package game.entities.heroes.dragonbreather;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.other.Burning; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_DragonBreath extends Skill {

    public S_DragonBreath(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/dragonbreather/icons/dragonbreath.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.5));
        this.effects = List.of(new Burning(2));
        this.targetType = TargetType.ALL_ENEMY;  
        this.level = 2;
    }

    @Override
    public String getName() {
        return "Dragon Breath";
    }
}
