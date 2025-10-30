package framework.connector.payloads;

import framework.connector.ConnectionPayload;
import game.entities.Hero;
import game.skills.logic.Stat;

public class StatChangePayload extends ConnectionPayload {

    public int oldValue;
    public int newValue;
    public Stat stat;
    public Hero hero;

    public StatChangePayload setOldValue(int oldValue) {
        this.oldValue = oldValue;
        return this;
    }

    public StatChangePayload setNewValue(int newValue) {
        this.newValue = newValue;
        return this;
    }

    public StatChangePayload setStat(Stat stat) {
        this.stat = stat;
        return this;
    }

    public StatChangePayload setHero(Hero hero) {
        this.hero = hero;
        return this;
    }
}
