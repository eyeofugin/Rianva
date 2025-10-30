package game.objects.equipments;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import framework.connector.payloads.HealChangesPayload;
import game.objects.Equipment;
import game.skills.logic.Stat;
import game.skills.changeeffects.effects.other.Protected;

public class JewelOfLife extends Equipment {

    private boolean healAvailable = true;
    public JewelOfLife() {
        super("jeweloflife", "Jewel of Life");
        this.statBonus = this.loadBaseStatBonus();
    }

    @Override
    public String getDescription() {
        return "Trigger: When less than 50%"+ Stat.LIFE.getReference()+", gain " + Protected.getStaticIconString() + "(~).";
    }

    @Override
    public void addSubscriptions() {
//        Connector.addSubscription(Connector.HEAL_CHANGES, new Connection(this, HealChangesPayload.class, "healChanges"));
        Connector.addSubscription(Connector.DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
        Connector.addSubscription(Connector.EFFECT_DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
    }

    public void dmgTrigger(DmgTriggerPayload pl) {
        if (this.active && pl.target != null && pl.target.equals(this.hero)) {
            if (this.healAvailable && pl.target.getCurrentLifePercentage() < 50) {
                this.healAvailable = false;
                this.hero.addEffect(new Protected(-1), this.hero);
            }
        }
    }

    public void healChanges(HealChangesPayload pl) {
        if (this.active && pl.target != null && pl.target.equals(this.hero)) {
            pl.heal = (int) (1.5*pl.heal);
        }
    }
}
