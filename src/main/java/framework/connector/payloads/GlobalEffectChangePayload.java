package framework.connector.payloads;

import framework.connector.ConnectionPayload;
import game.skills.logic.GlobalEffect;

public class GlobalEffectChangePayload extends ConnectionPayload {
    public GlobalEffect effect;
    public GlobalEffect oldEffect;
    public GlobalEffectChangePayload setEffect(GlobalEffect effect) {
        this.effect = effect;
        return this;
    }

    public GlobalEffectChangePayload setOldEffect(GlobalEffect oldEffect) {
        this.oldEffect = oldEffect;
        return this;
    }
}
