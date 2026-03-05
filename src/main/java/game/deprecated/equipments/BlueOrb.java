package game.deprecated.equipments;

import framework.connector.Connection;
import framework.connector.ConnectionPayload;
import framework.connector.Connector;
import game.objects.Equipment;
import game.skills.changeeffects.globals.AetherWinds;

public class BlueOrb extends Equipment {
    public BlueOrb() {
        super("blueorb", "Blue Orb");
        this.statBonus = this.loadBaseStatBonus();
//        this.skill = new S_BlueOrb(this);
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.START_OF_MATCH, new Connection(this, "start"));
    }

    public void start(ConnectionPayload pl) {
        if (this.active && this.hero != null) {
            this.hero.arena.setGlobalEffect(new AetherWinds());
        }
    }

    @Override
    public String getDescription() {
        return "Summon the Aether Winds Global Effect at the start of the match.";
    }
}
