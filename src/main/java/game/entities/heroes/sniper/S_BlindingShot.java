package game.entities.heroes.sniper;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.other.StatEffect; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_BlindingShot extends Skill {

    public S_BlindingShot(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/sniper/icons/blindingshot.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;  
        this.dmgMultipliers = List.of(new Multiplier(Stat.ATTACK, 0.2));
        this.effects = List.of(StatEffect.blinded.getNew());
    }

    @Override
    public int getAIRating(Hero target) {
        return 4;
    }


    @Override
    public String getName() {
        return "Blinding Shot";
    }
}
