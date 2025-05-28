package game.objects.equipments;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import framework.graphics.text.Color;
import game.objects.Equipment;
import game.skills.DamageMode;
import game.skills.changeeffects.effects.Burning;
import jdk.jfr.Percentage;

public class FlamingChestplate extends Equipment {

    public FlamingChestplate() {
        super("flamingchestplate", "Flaming Chestplate");
        this.statBonus = this.loadBaseStatBonus();
    }

    @Override
    public String getDescription() {
        return "When receiving " + DamageMode.PHYSICAL.getColor().getCodeString() + "damage" + Color.WHITE.getCodeString()+  ", the attacker gets " + Burning.getStaticIconString() + "(3).";
    }
    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
    }

    public void dmgTrigger(DmgTriggerPayload pl) {
        if (this.active && pl.target != null && pl.target.equals(this.hero)) {
            if (pl.damageMode != null && pl.damageMode.equals(DamageMode.PHYSICAL) && pl.cast != null) {
                pl.cast.hero.addEffect(new Burning(3), this.hero);
            }
        }
    }
}
