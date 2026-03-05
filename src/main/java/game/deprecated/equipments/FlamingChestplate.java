package game.deprecated.equipments;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.ConnectionPayload;
import game.objects.Equipment;

public class FlamingChestplate extends Equipment {

    public FlamingChestplate() {
        super("flamingchestplate", "Flaming Chestplate");
        this.statBonus = this.loadBaseStatBonus();
    }

    @Override
    public String getDescription() {
        return "";//return "When receiving " + DamageMode.PHYSICAL.getColor().getCodeString() + "damage" + Color.WHITE.getCodeString()+  ", the attacker gets " + Burning.getStaticIconString() + "(3).";
    }
    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_TRIGGER, new Connection(this, ConnectionPayload.class, "dmgTrigger"));
    }

    public void dmgTrigger(ConnectionPayload pl) {
//        if (this.active && pl.target != null && pl.target.equals(this.hero)) {
//            if (pl.damageMode != null && pl.damageMode.equals(DamageMode.PHYSICAL) && pl.cast != null) {
//                pl.cast.hero.addEffect(new Burning(3), this.hero);
//            }
//        }
    }
}
