package game.entities.individuals.paladin;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.statusinflictions.Disenchanted;

import java.util.List;

public class S_LightPillar extends Skill {

    public S_LightPillar(Hero hero) {
        super(hero);
        this.iconPath = "entities/paladin/icons/lightpillar.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.dmgMultipliers = List.of(new Multiplier(Stat.CURRENT_FAITH, 0.6));
        this.effects = List.of(new Disenchanted(1));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{3,4,5};
        this.dmg = 2;
        this.faithRequirement = 40;
        this.damageMode = DamageMode.MAGICAL;
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
