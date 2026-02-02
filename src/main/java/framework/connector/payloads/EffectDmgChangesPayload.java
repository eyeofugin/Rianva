package framework.connector.payloads;

import framework.connector.ConnectionPayload;
import game.entities.Hero;
import game.skills.logic.Effect;

public class EffectDmgChangesPayload extends ConnectionPayload {
    public Hero target;
    public Effect effect;
    public int dmg;

    public EffectDmgChangesPayload setTarget(Hero target) {
        this.target = target;
        return this;
    }

    public EffectDmgChangesPayload setDmg(int dmg) {
        this.dmg = dmg;
        return this;
    }

    public EffectDmgChangesPayload setEffect(Effect effect) {
        this.effect = effect;
        return this;
    }
}
