package game.entities.heroes.duelist;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Dazed;
import java.util.List;

public class S_SwirlingBlades extends Skill {

    public S_SwirlingBlades(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/duelist/icons/swirlingblades.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.aiTags = List.of(AiSkillTag.COMBO_ENABLED);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.25), new Multiplier(Stat.SPEED, 0.2));
        this.targetType = TargetType.SINGLE;
        this.effects = List.of(new Dazed(2));
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{3,4};
        this.cdMax = 2;
        this.damageMode = DamageMode.PHYSICAL;
        this.countAsHits = 3;
    }

    @Override
    public String getName() {
        return "Swirling Blades";
    }
}
