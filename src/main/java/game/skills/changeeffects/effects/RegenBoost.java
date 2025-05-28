package game.skills.changeeffects.effects;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.HealChangesPayload;
import game.skills.Effect;

public class RegenBoost extends Effect {

    public static String ICON_STRING = "RGB";
    public RegenBoost(int turns) {
        this.turns = turns;
        this.iconString = ICON_STRING;
        this.name = "Regen Boost";
        this.stackable = false;
        this.description = "Doubles all received heal.";
        this.type = ChangeEffectType.BUFF;
    }

    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }
    @Override
    public Effect getNew() {
        return new RegenBoost(this.turns);
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.HEAL_CHANGES, new Connection(this, HealChangesPayload.class, "healChanges"));
    }
    public void healChanges(HealChangesPayload pl) {
        if (this.hero.equals(pl.target)) {
            pl.heal *= 2;
        }
    }
}
