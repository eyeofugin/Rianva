package game.entities.heroes.paladin;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.other.Disenchanted; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_LightPillar extends Skill {

    public S_LightPillar(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/paladin/icons/lightpillar.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.dmgMultipliers = List.of(new Multiplier(Stat.CURRENT_MANA, 0.05));
        this.effects = List.of(new Disenchanted(1));
        this.targetType = TargetType.SINGLE;  
        this.level = 2;
    }


    @Override
    public int getAIRating(Hero target) {
        return 3;
    }
    @Override
    public String getName() {
        return "Light Pillar";
    }
}
