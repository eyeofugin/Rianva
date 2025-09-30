package game.objects.equipments;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.ShieldChangesPayload;
import game.objects.Equipment;
import game.skills.Stat;

public class ShiningArmor extends Equipment {

    public ShiningArmor() {
        super("shiningarmor", "Shining Armor");
        this.statBonus = this.loadBaseStatBonus();
    }

    @Override
    public String getDescription() {
        return "Get 5" + Stat.FAITH.getIconString() + " at the end of each turn. All received " + Stat.SHIELD.getIconString() + " is doubled.";
    }


    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.SHIELD_CHANGES, new Connection(this, ShieldChangesPayload.class, "shieldChanges"));
    }

    public void shieldChanges(ShieldChangesPayload pl) {
        if (this.active && pl.target != null && pl.target.equals(this.hero)) {
            pl.shield *= 2;
        }
    }
    @Override
    public void turn() {
        if (this.active && this.hero.getSecondaryResource().equals(Stat.FAITH)) {
            this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 5, this.hero);
        }
    }
}
