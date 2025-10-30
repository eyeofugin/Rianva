package game.entities.heroes.firedancer;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.other.Burning; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_FlamingSwing extends Skill {

    public S_FlamingSwing(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/firedancer/icons/flamingswing.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.2), new Multiplier(Stat.ATTACK, 0.2));
        this.targetType = TargetType.SINGLE;
        this.effects = List.of(new Burning(1));  
    }
    @Override
    public String getName() {
        return "Flaming Swing";
    }
}
