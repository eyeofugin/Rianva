package game.entities.heroes.burner;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.other.Burning;
import game.skills.logic.*;

import java.util.List;

public class S_Burn extends Skill {

    public S_Burn(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/burner/icons/burn.png";
        addSubscriptions();
        setToInitial();
    }
    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.aiTags = List.of(AiSkillTag.FAITH_GAIN);
        this.targetType = TargetType.SINGLE;  
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.35));
        this.effects = List.of(new Burning(3));
    }

    @Override
    public String getName() {
        return "Burn";
    }
}
