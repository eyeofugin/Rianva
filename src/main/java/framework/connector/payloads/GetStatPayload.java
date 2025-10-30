package framework.connector.payloads;

import framework.connector.ConnectionPayload;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.logic.Stat;

public class GetStatPayload extends ConnectionPayload {
    public Stat stat;
    public int value;
    public Hero hero;
    public Skill skill;

    public Stat getStat() {
        return stat;
    }

    public GetStatPayload setStat(Stat stat) {
        this.stat = stat;
        return this;
    }

    public int getValue() {
        return value;
    }

    public GetStatPayload setValue(int value) {
        this.value = value;
        return this;
    }

    public Hero getHero() {
        return hero;
    }

    public GetStatPayload setHero(Hero hero) {
        this.hero = hero;
        return this;
    }

    public Skill getSkill() {
        return skill;
    }

    public GetStatPayload setSkill(Skill skill) {
        this.skill = skill;
        return this;
    }
}
