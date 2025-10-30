package game.skills.changeeffects.effects.other;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.skills.logic.Effect;
import game.skills.changeeffects.effects.status.Stunned;

public class Frost extends Effect {

    public static String ICON_STRING = "FRO";
    public Frost(int stacks) {
        this.name = "Frost";
        this.iconString = ICON_STRING;
        this.stackable = true;
        this.stacks = stacks;
        this.description = "Damage adds another stack, then if there are 2 or more stacks, remove all stacks and stun.";
        this.type = ChangeEffectType.DEBUFF;
    }
    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }

    @Override
    public Effect getNew() {
        return new Frost(this.stacks);
    }


    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
    }

    public void dmgTrigger(DmgTriggerPayload pl) {
        if (this.hero.equals(pl.target)) {
            this.addStack(1);
        }
    }

    @Override
    public void addStack(int amount) {
        this.stacks += amount;
        if (this.stacks >= 2) {
            this.stacks = 0;
            this.hero.addEffect(new Stunned(), this.hero);
        }
    }
}
