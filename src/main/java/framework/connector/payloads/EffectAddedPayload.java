package framework.connector.payloads;

import framework.connector.ConnectionPayload;
import game.entities.Hero;
import game.skills.logic.Effect;

public class EffectAddedPayload  extends ConnectionPayload {
    private Effect effect;
    private Hero caster;

    public Hero getCaster() {
        return caster;
    }

    public EffectAddedPayload setCaster(Hero caster) {
        this.caster = caster;
        return this;
    }

    public Effect getEffect() {
        return effect;
    }

    public EffectAddedPayload setEffect(Effect effect) {
        this.effect = effect;
        return this;
    }
}
