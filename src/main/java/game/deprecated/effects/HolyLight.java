package game.skills.changeeffects.globals;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.ShieldChangesPayload;
import game.skills.logic.GlobalEffect;

public class HolyLight extends GlobalEffect {
    public HolyLight() {
        super("Holy Light", "Doubles Shields");
        initSubscriptions();
    }

    @Override
    public void initSubscriptions() {
        Connector.addSubscription(Connector.SHIELD_CHANGES, new Connection(this, ShieldChangesPayload.class, "shieldChanges"));
    }

    public void shieldChanges(ShieldChangesPayload pl) {
        pl.shield *= 2;
    }
    @Override
    public void turn() {

    }
}
