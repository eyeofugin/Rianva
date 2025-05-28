package game.objects.equipments;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.objects.Equipment;
import game.skills.Stat;

public class ArcaneCloak extends Equipment {

    public ArcaneCloak() {
        super("arcanecloak", "Arcane Cloak");
        this.statBonus = this.loadBaseStatBonus();
        this.tempStatBonus = this.loadTempStatBonus();
    }
    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
    }
    public void dmgTrigger(DmgTriggerPayload pl) {
        if (!this.loseTempStat && this.active && pl.target != null && pl.target.equals(this.hero)) {
            loseTempStat();
        }
    }

    @Override
    public void turn() {
        if (this.active && this.hero.getSecondaryResource().equals(Stat.FAITH)) {
            this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 5, this.hero);
        }
    }

    @Override
    public String getDescription() {
        return "Until receiving damage, gain " + getTempStatBonusString()+ ". Gain +5" + Stat.FAITH.getIconString() + " per turn.";
    }
}
