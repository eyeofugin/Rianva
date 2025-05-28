package game.entities.individuals.duelist;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CastChangePayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.statusinflictions.Dazed;

import java.util.List;

public class S_SwirlingBlades extends Skill {

    public S_SwirlingBlades(Hero hero) {
        super(hero);
        this.iconPath = "entities/duelist/icons/swirlingblades.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.aiTags = List.of(AiSkillTag.COMBO_ENABLED);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.4));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{3,4};
        this.dmg = 2;
        this.cdMax = 2;
        this.damageMode = DamageMode.PHYSICAL;
        this.countAsHits = 3;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
            this.hero.removePermanentEffectOfClass(Combo.class);
            target.addEffect(new Dazed(2), this.hero);
        }
    }



    @Override
    public String getComboDescription(Hero hero) {
        return "Gain " + Dazed.getStaticIconString() + "(2).";
    }

    @Override
    public String getName() {
        return "Swirling Blades";
    }
}
