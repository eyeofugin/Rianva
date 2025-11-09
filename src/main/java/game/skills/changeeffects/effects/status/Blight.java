package game.skills.changeeffects.effects.status;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.skills.logic.Effect;
import game.skills.logic.Stat;

public class Blight extends Effect {
    public static String ICON_STRING = "BLI";

    public Blight(int turns) {
        this.turns = turns;
        this.iconString = ICON_STRING;
        this.name = "Dazed";
        this.stackable = false;
        this.description = "Whenever you receive Damage, get 5% max health damage. -25% Defense.";
        this.type = ChangeEffectType.STATUS;
        this.stat = Stat.STAMINA;
        this.statChangePercentage = 0.75;
    }

    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }
    @Override
    public Effect getNew() {
        return new Blight(this.turns);
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
    }

    public void dmgTrigger(DmgTriggerPayload pl) {
        if (pl != null && pl.target != null && pl.target.equals(this.hero)) {
            this.hero.effectDamage(this.hero.getStat(Stat.LIFE) * 10 / 100, this);
        }
    }
}
