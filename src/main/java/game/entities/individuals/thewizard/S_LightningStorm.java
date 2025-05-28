package game.entities.individuals.thewizard;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CastChangePayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.statusinflictions.Dazed;

import java.util.List;

public class S_LightningStorm extends Skill {

    public S_LightningStorm(Hero hero) {
        super(hero);
        this.iconPath = "entities/thewizard/icons/lightningstorm.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.ULT);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.5));
        this.targetType = TargetType.ALL_TARGETS;
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{3,4,5};
        this.manaCost = 8;
        this.damageMode = DamageMode.MAGICAL;
        this.level = 5;
    }

    @Override
    public String getName() {
        return "Lightning Storm";
    }
}
