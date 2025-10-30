package game.skills.changeeffects.globals;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.EffectDmgChangesPayload;
import game.skills.logic.GlobalEffect;
import game.skills.changeeffects.effects.other.Burning;

public class Heat extends GlobalEffect {

    public Heat() {
        super("Heat", "Doubles burning dmg");
        initSubscriptions();
    }

    @Override
    public void initSubscriptions() {
        Connector.addSubscription(Connector.EFFECT_DMG_CHANGES, new Connection(this, EffectDmgChangesPayload.class, "effectDmgChanges"));
    }

    public void effectDmgChanges(EffectDmgChangesPayload pl) {
        if (pl.effect instanceof Burning) {
            pl.dmg *= 2;
        }
    }
    @Override
    public void turn() {

    }
}
