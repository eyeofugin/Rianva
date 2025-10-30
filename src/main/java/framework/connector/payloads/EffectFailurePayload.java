package framework.connector.payloads;

import framework.connector.ConnectionPayload;
import game.entities.Hero;
import game.skills.logic.Effect;

public class EffectFailurePayload  extends ConnectionPayload {

    private boolean failure;
    private Hero caster;
    private Effect effect;

    public boolean isFailure() {
        return failure;
    }

    public EffectFailurePayload setFailure(boolean failure) {
        this.failure = failure;
        return this;
    }

    public Hero getCaster() {
        return caster;
    }

    public EffectFailurePayload setCaster(Hero caster) {
        this.caster = caster;
        return this;
    }

    public Effect getEffect() {
        return effect;
    }

    public EffectFailurePayload setEffect(Effect effect) {
        this.effect = effect;
        return this;
    }
}
