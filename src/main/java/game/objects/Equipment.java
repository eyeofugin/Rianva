package game.objects;

import framework.Property;
import framework.connector.Connector;
import framework.connector.payloads.EquipmentChangePayload;
import framework.graphics.text.Color;
import framework.resources.SpriteLibrary;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.logic.Stat;
import utils.FileWalker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Equipment {

    protected final String packageName;
    protected Map<Stat, Integer> statBonus = new HashMap<>();
    protected Map<Stat, Integer> tempStatBonus = new HashMap<>();
    protected boolean loseTempStat = false;
    protected boolean consumed = false;
    protected boolean equipped = false;
    protected Hero hero;
    protected Hero oldHero;
    protected boolean active;
    protected Skill skill;
    protected String name;
    protected List<Stat> adaptiveStats;


    public Equipment() {
        this.packageName = "";
    }
    public Equipment(String packageName, String name) {
        this.packageName = packageName;
        this.name = name;
    }

    public void addSubscriptions() {}
    public void removeSubscriptions() {
        Connector.removeSubscriptions(this);
    }

    public void turn() {}
    public void equipToHero(Hero hero) {
        if (this.hero != null && this.equipped) {
            return;
        }
        this.hero = hero;
        this.hero.equip(this);
        this.activateEquipment();
        this.equipped = true;
    }

    public void unEquipFromHero() {
        this.hero.unequip(this);
        statChange(this.statBonus, -1);
        removeSubscriptions();
        this.oldHero = this.hero;
        if (this.loseTempStat) return;
        statChange(this.tempStatBonus, -1);
        EquipmentChangePayload pl = new EquipmentChangePayload()
                .setEquipment(this)
                .setTarget(this.oldHero)
                .setMode(EquipmentChangePayload.EquipmentChangeMode.UNEQUIP);
        Connector.fireTopic(Connector.EQUIPMENT_CHANGE_TRIGGER, pl);
        this.hero = null;
        this.equipped = false;
    }

    private void statChange(Map<Stat, Integer> map, int sign) {
        for (Map.Entry<Stat, Integer> statBonus : map.entrySet()) {
            if (this.adaptiveStats != null && !this.adaptiveStats.isEmpty()) {
                if (this.adaptiveStats.contains(statBonus.getKey())) {
                    this.hero.addToStat(statBonus.getKey(), statBonus.getValue() * sign);
                }
            } else {
                this.hero.addToStat(statBonus.getKey(), statBonus.getValue() * sign);
            }
            if (statBonus.getKey().equals(Stat.LIFE) && this.hero.getStat(Stat.CURRENT_LIFE) > this.hero.getStat(Stat.LIFE)) {
                this.hero.changeStatTo(Stat.CURRENT_LIFE, this.hero.getStat(Stat.LIFE));
            }
            if (statBonus.getKey().equals(Stat.MANA) && this.hero.getStat(Stat.CURRENT_MANA) > this.hero.getStat(Stat.LIFE)) {
                this.hero.changeStatTo(Stat.CURRENT_MANA, this.hero.getStat(Stat.MANA));
            }
        }
    }

    public void deactivateEquipment() {
        this.active = false;
        statChange(this.statBonus, -1);
        if (this.loseTempStat) return;
        statChange(this.tempStatBonus, -1);
        EquipmentChangePayload pl = new EquipmentChangePayload()
                .setEquipment(this)
                .setTarget(this.hero)
                .setMode(EquipmentChangePayload.EquipmentChangeMode.DEACTIVATE);
        Connector.fireTopic(Connector.EQUIPMENT_CHANGE_TRIGGER, pl);

    }

    public void activateEquipment() {
        if (active) {
            return;
        }
        statChange(this.statBonus, 1);
        if (this.loseTempStat) return;
        statChange(this.tempStatBonus, 1);
        this.active = true;
        EquipmentChangePayload pl = new EquipmentChangePayload()
                .setEquipment(this)
                .setTarget(this.hero)
                .setMode(EquipmentChangePayload.EquipmentChangeMode.ACTIVATE);
        Connector.fireTopic(Connector.EQUIPMENT_CHANGE_TRIGGER, pl);
    }
    public void loseTempStat() {
        this.loseTempStat = true;
        statChange(this.tempStatBonus, -1);
    }
    private Map<Stat, Integer> loadStatBonus(String file) {
        Map<Stat, Integer> result = FileWalker.getEquipmentStatJson("equipments/" + this.packageName + file);
        if (result != null) {
            return result;
        }
        return new HashMap<>();
    }
    protected Map<Stat, Integer> loadBaseStatBonus() {
        return loadStatBonus("/stats.json");
    }
    protected Map<Stat, Integer> loadTempStatBonus() {
        return loadStatBonus("/tempstats.json");
    }

    public String getName() {
        return this.name;
    }
    public String getDescription() { return " "; }
    public Skill getSkill() { return skill; }
    public boolean isActive() { return active; }
    public String getInfoStatBonus() {
        return getBaseStatBonusString();
    }
    public String getBaseStatBonusString() {
        return getStatBonusString(this.statBonus);
    }
    public String getTempStatBonusString() {
        return getStatBonusString(this.tempStatBonus);
    }
    public String getStatBonusString(Map<Stat, Integer> statMap) {
        if (statMap == null || statMap.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Stat, Integer> entry : statMap.entrySet()) {
            builder.append(entry.getKey().getColorKey());
            if (entry.getValue()>0) {
                builder.append(Color.MEDIUMGREEN.getCodeString());
                builder.append("+");
            } else {
                builder.append(Color.DARKRED.getCodeString());
            }
            builder.append(entry.getValue());
            builder.append(entry.getKey().getReference());
            builder.append(" ");
        }
        builder.append(Color.WHITE.getCodeString());
        return builder.toString();
    }

    public int[] getIcon() {
        return SpriteLibrary.sprite(Property.SKILL_ICON_SIZE,Property.SKILL_ICON_SIZE,Property.SKILL_ICON_SIZE,Property.SKILL_ICON_SIZE,
                "equipments/" + this.packageName + "/sprite.png", 0);
    }

    public void consume() {
        this.consumed = true;
    }
    public boolean isConsumed() {
        return consumed;
    }

    public void reset() {
        this.activateEquipment();
    }
}
