package game.skills.changeeffects.globals;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.EffectDmgChangesPayload;
import framework.connector.payloads.EndOfRoundPayload;
import game.skills.GlobalEffect;
import game.skills.Stat;

public class AetherWinds extends GlobalEffect {

    public AetherWinds() {
        super("Aether Winds", "+1 Mana regen");
        initSubscriptions();
    }

    @Override
    public void initSubscriptions() {
        Connector.addSubscription(Connector.END_OF_ROUND, new Connection(this, EffectDmgChangesPayload.class, "endOfRound"));
    }

    public void endOfRound(EndOfRoundPayload pl) {
        pl.arena.getAllLivingEntities().stream()
                .filter(e->e.getSecondaryResource() != null && e.getSecondaryResource().equals(Stat.MANA))
                .forEach(e->e.addResource(Stat.CURRENT_MANA, Stat.MANA, 1, null));
    }
    @Override
    public void turn() {

    }
}
