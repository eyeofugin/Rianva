package game.entities.individuals.burner;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Burning;
import utils.MyMaths;

import java.util.List;

public class S_Burn extends Skill {

    public S_Burn(Hero hero) {
        super(hero);
        this.iconPath ="entities/burner/icons/burn.png";
        addSubscriptions();
        setToInitial();
    }
    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.aiTags = List.of(AiSkillTag.FAITH_GAIN);
        this.targetType = TargetType.SINGLE;
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.2));
        this.effects = List.of(new Burning(1));
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{3,4};
        this.faithGain = 10;
    }

    @Override
    public String getName() {
        return "Burn";
    }
}
