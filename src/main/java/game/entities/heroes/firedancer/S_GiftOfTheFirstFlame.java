package game.entities.heroes.firedancer;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.UpdatePayload;
import game.entities.Hero;
import game.skills.*;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.effects.DoubleShot;

import java.util.Iterator;
import java.util.List;

public class S_GiftOfTheFirstFlame extends Skill {

    public S_GiftOfTheFirstFlame(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/firedancer/icons/giftofthefirstflame.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.ULT);
        this.targetType = TargetType.SELF;
        this.possibleCastPositions = new int[]{0,1,2};
        this.casterEffects = List.of(new Burning(1));
        this.faithRequirement = 75;
        this.faithCost = 20;
        this.level = 5;
    }



    @Override
    public int getAIRating(Hero target) {
        if (this.hero.getCurrentLifePercentage() > 75) {
            return 5;
        }
        if (this.hero.getCurrentLifePercentage() < 25) {
            return -1;
        }
        return 2;
    }
    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.UPDATE, new Connection(this, UpdatePayload.class, "update"));

    }

    public void update(UpdatePayload pl) {
        if (this.hero.getPermanentEffectStacks(Burning.class) < 5 && this.hero.getPermanentEffectStacks(DoubleShot.class) > 0) {
            this.hero.removePermanentEffectOfClass(DoubleShot.class);
        }
        if (this.hero.getPermanentEffectStacks(Burning.class) > 4 && this.hero.getPermanentEffectStacks(DoubleShot.class) < 1) {
            this.hero.addEffect(new DoubleShot(-1), this.hero);
        }
    }

    @Override
    public String getUpperDescriptionFor(Hero hero) {
        return "Active: Remove all debuffs other than " + Burning.getStaticIconString()+".";
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Passive: As long as you have at least 1 "+Burning.getStaticIconString()+" stack, primary skills hit twice.";
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        Iterator<Effect> iter = this.hero.getEffects().stream()
                .filter(effect->effect.type.equals(Effect.ChangeEffectType.DEBUFF) && !(effect instanceof Burning))
                .toList().iterator();
        while(iter.hasNext()) {
            this.hero.removePermanentEffectOfClass(iter.next().getClass());
            iter.remove();
        }
    }

    @Override
    public String getName() {
        return "Gift of the first Flame";
    }
}
