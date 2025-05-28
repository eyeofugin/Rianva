package game.objects.equipments;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgChangesPayload;
import game.objects.Equipment;
import game.skills.Stat;

public class SnipersTunika extends Equipment {

    public SnipersTunika() {
        super("sniperstunika", "Snipers Tunika");
        this.statBonus = this.loadBaseStatBonus();
    }

    @Override
    public String getDescription() {
        return "Receive 20% more damage.";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_CHANGES, new Connection(this, DmgChangesPayload.class, "dmgChanges"));
    }
    public void dmgChanges(DmgChangesPayload pl) {
        if (pl.target != null && pl.target.equals(this.hero) && this.active) {
            pl.dmg = (int)(pl.dmg * 1.2);
        }
    }
}
