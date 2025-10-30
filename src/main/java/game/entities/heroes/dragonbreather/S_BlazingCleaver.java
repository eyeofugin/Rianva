package game.entities.heroes.dragonbreather;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.other.Burning; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_BlazingCleaver extends Skill {

    public S_BlazingCleaver(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/dragonbreather/icons/blazingcleaver.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.ATTACK, 0.5));
        this.targetType = TargetType.SINGLE;
        this.effects = List.of(new Burning(1));  
    }

    @Override
    public String getName() {
        return "Blazing Cleaver";
    }
}
