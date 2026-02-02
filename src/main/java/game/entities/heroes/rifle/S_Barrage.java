package game.entities.heroes.rifle;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.status.Injured;
import game.skills.changeeffects.effects.other.StatEffect; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_Barrage extends Skill {

    public S_Barrage(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/rifle/icons/barrage.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.ULT);
        this.dmgMultipliers = List.of(new Multiplier(Stat.ATTACK, 0.5));
        this.effects = List.of(new Injured(1), StatEffect.slow.getNew());
        this.targetType = TargetType.ALL_ENEMY;  
        this.level = 5;
    }

    @Override
    public String getName() {
        return "Barrage";
    }
}
