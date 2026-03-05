package game.deprecated.equipments;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.ConnectionPayload;
import game.objects.Equipment;

public class FlamingSword extends Equipment {
    public FlamingSword() {
        super("flamingsword", "Flaming Sword");
        this.statBonus = this.loadBaseStatBonus();
    }

    @Override
    public String getDescription() {
        return "";//return "When dealing " + DamageMode.PHYSICAL.getColor().getCodeString() + "damage" + Color.WHITE.getCodeString()+ ", give " + Burning.getStaticIconString() + "(2).";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_TRIGGER, new Connection(this, ConnectionPayload.class, "dmgTrigger"));
    }

    public void dmgTrigger(ConnectionPayload pl) {
//        if (this.active && pl.cast != null && pl.cast.hero.equals(this.hero) && pl.damageMode.equals(DamageMode.PHYSICAL)) {
//            pl.target.addEffect(new Burning(2), this.hero);
//        }
    }
}
