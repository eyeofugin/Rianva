package framework.connector.payloads;

import framework.connector.ConnectionPayload;
import game.entities.Hero;
import game.skills.logic.Stat;

public class ExcessResourcePayload extends ConnectionPayload {
    public Stat resource;
    public int excess;
    public Hero source;
    public Hero target;

    public ExcessResourcePayload setTarget(Hero target) {
        this.target = target;
        return this;
    }

    public ExcessResourcePayload setSource(Hero source) {
        this.source = source;
        return this;
    }

    public ExcessResourcePayload setExcess(int excess) {
        this.excess = excess;
        return this;
    }

    public ExcessResourcePayload setResource(Stat resource) {
        this.resource = resource;
        return this;
    }
}
