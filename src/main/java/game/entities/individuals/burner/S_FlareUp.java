package game.entities.individuals.burner;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgChangesPayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Burning;

import java.util.List;

public class S_FlareUp extends Skill {

    public S_FlareUp(Hero hero) {
        super(hero);
        this.iconPath = "entities/burner/icons/flareup.png";
        addSubscriptions();
        setToInitial();
    }
    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.dmgMultipliers = List.of(new Multiplier(Stat.CURRENT_FAITH, 0.1));
        this.targetType = TargetType.SINGLE;
        this.damageMode = DamageMode.MAGICAL;
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{3,4};
        this.dmg = 3;
        this.faithRequirement = 40;
        this.faithGain = 5;
    }

    @Override
    public int getAIRating(Hero target) {
        return target.getPermanentEffectStacks(Burning.class) / 3;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.removePermanentEffectOfClass(Burning.class);
    }

    @Override
    public String getName() {
        return "Flare Up";
    }



    @Override
    public String getDescriptionFor(Hero hero) {
        return "Deals an additional 3 damage for each of the target's "+Burning.getStaticIconString()+" stack.";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.BASE_DMG_CHANGES, new Connection(this, DmgChangesPayload.class, "dmgChanges"));
    }

    public void dmgChanges(DmgChangesPayload pl) {
        if (pl.caster != null && pl.caster.equals(this.hero) && pl.skill != null && pl.skill.equals(this) && pl.target != null) {
            int burnStack = pl.target.hasPermanentEffect(Burning.class);
            pl.dmg *= burnStack;
        }
    }
}
