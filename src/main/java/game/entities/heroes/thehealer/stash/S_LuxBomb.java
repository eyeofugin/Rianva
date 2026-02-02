package game.entities.heroes.thehealer.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_LuxBomb extends Skill {

    public S_LuxBomb(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/thehealer/icons/luxbomb.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.targetType = TargetType.SINGLE;
        this.dmg = 1;
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.1),
                new Multiplier(Stat.MANA, 0.3));  
    }

    @Override
    public int getAIRating(Hero target) {
        return target.getMissingLifePercentage() / 25;
    }


    @Override
    public String getName() {
        return "Lux Bomb";
    }
}
