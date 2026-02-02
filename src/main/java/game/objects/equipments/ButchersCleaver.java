package game.objects.equipments;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgChangesPayload;
import framework.connector.payloads.EquipmentChangePayload;
import framework.connector.payloads.StatChangePayload;
import framework.connector.payloads.UpdatePayload;
import game.entities.Hero;
import game.objects.Equipment;
import game.skills.logic.Stat;

public class ButchersCleaver extends Equipment {
    private int calculatedExtraPower = 0;
    private int lastLife = 0;

    public ButchersCleaver() {
        super("butcherscleaver", "Butchers Cleaver");
        this.statBonus = this.loadBaseStatBonus();
    }


    @Override
    public String getDescription() {
        return "Get 50% of your " + Stat.LIFE.getIconString() + " as " + Stat.ATTACK.getIconString() + ", but receive 50% more damage.";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.UPDATE, new Connection(this, UpdatePayload.class, "update"));
        Connector.addSubscription(Connector.EQUIPMENT_CHANGE_TRIGGER, new Connection(this, EquipmentChangePayload.class, "equipChange", true));
        Connector.addSubscription(Connector.STAT_CHANGE, new Connection(this, StatChangePayload.class, "statChange", true));
        Connector.addSubscription(Connector.DMG_CHANGES, new Connection(this, DmgChangesPayload.class, "dmgChanges"));
    }
    public void dmgChanges(DmgChangesPayload pl) {
        if (pl.target != null && pl.target.equals(this.hero) && this.active) {
            pl.dmg = (int)(pl.dmg * 1.5);
        }
    }

    public void statChange(StatChangePayload pl) {
        if (this.active && this.hero.equals(pl.hero) && pl.stat.equals(Stat.LIFE)) {
            this.recalc();
        }
    }
    public void equipChange(EquipmentChangePayload pl) {
        if (this.active) {
            if (pl.equipment != null && pl.equipment.equals(this)) {
                Hero hero = this.hero != null ? this.hero: this.oldHero;
                if (pl.mode.equals(EquipmentChangePayload.EquipmentChangeMode.EQUIP)
                        || pl.mode.equals(EquipmentChangePayload.EquipmentChangeMode.ACTIVATE)) {
                    this.add(hero);
                } else {
                    this.remove(hero);
                }
            }
        }
    }
    public void update(UpdatePayload pl) {
        if (this.hero != null && this.active) {
            int health = this.hero.getStat(Stat.LIFE);
            if (health != this.lastLife) {
                this.recalc();
            }
        }
    }
    private void recalc() {
        if (this.hero != null) {
            this.remove(this.hero);
            this.add(this.hero);
        }
    }
    private void remove(Hero hero) {
        if (hero != null) {
            hero.addToStat(Stat.ATTACK, -1* this.calculatedExtraPower);
        }
    }
    private void add(Hero hero) {
        if (hero != null) {
            this.lastLife = hero.getStat(Stat.LIFE);
            this.calculatedExtraPower = hero.getStatChange(Stat.LIFE) / 2;
            hero.addToStat(Stat.ATTACK, this.calculatedExtraPower);
        }
    }

    @Override
    public void reset() {
        super.reset();
        update(null);
    }
}
