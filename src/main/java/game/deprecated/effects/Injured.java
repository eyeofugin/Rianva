package game.skills.changeeffects.effects.status;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.HealChangesPayload;
import game.skills.logic.Effect;
import game.skills.logic.Stat;

public class Injured extends Effect {

    public static String ICON_STRING = "INJ";
    public Injured(int turns) {
        this.turns = turns;
        this.iconString = ICON_STRING;
        this.name = "Injured";
        this.stackable = false;
        this.description = "You can't heal. -25% Speed";
        this.type = ChangeEffectType.DEBUFF;
        this.stat = Stat.SPEED;
        this.statChangePercentage = 0.75;
    }
    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }

    @Override
    public Injured getNew() {
        return new Injured(this.turns);
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.EOT_HEAL_CHANGES, new Connection(this, HealChangesPayload.class, "healChanges"));
        Connector.addSubscription(Connector.HEAL_CHANGES, new Connection(this, HealChangesPayload.class, "healChanges"));
    }

    public void healChanges(HealChangesPayload pl) {
        if (this.hero.equals(pl.target)) {
            pl.heal *= 0;
        }
    }
}
