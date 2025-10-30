package game.entities.heroes.burner.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.other.Burning; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_TwinFlames extends Skill {

    public S_TwinFlames(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/burner/icons/twinflames.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MANA, 0.4));
        this.effects = List.of(new Burning(1));
        this.targetType = TargetType.ALL_ENEMY;
//        this.possibleCastPositions = new int[]{1,2};
//        this.possibleTargetPositions = new int[]{3,4};
        this.dmg = 2;  
//        this.faithRequirement = 15;
//        this.faithGain = 5;
    }

    @Override
    public String getName() {
        return "Twin Flames";
    }
}
