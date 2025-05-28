package game.entities.individuals.angelguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.effects.StatEffect;

import java.util.List;

public class S_AngelicWings extends Skill {

    public S_AngelicWings(Hero hero) {
        super(hero);
        this.iconPath = "entities/angelguy/icons/angelicwings.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE_OTHER;
        this.possibleTargetPositions = new int[]{0,1,2};
        this.possibleCastPositions = new int[]{0,1,2};
        this.effects = List.of(new StatEffect(1, Stat.ENDURANCE, 50), new Combo());
        this.heal = 3;
        this.healMultipliers = List.of(new Multiplier(Stat.ENDURANCE, 0.1), new Multiplier(Stat.MAGIC, 0.1));
        this.faithRequirement = 20;
        this.faithGain = 5;
        this.priority = 5;
    }

    @Override
    public String getName() {
        return "Angelic Wings";
    }
}
