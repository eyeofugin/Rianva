package game.skills.trees.classes.fauna;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.libraries.EffectLibrary;
import game.effects.hero.Advantage;
import game.effects.hero.Initiative;
import game.effects.hero.Prepared;
import game.skills.Skill;
import utils.Utils;

public class S_Fauna extends Skill {
    public void onGetEffect(ConnectionPayload pl) {
        Logger.logLn("S_Fauna.onGetEffect()");
        if (!this.equals(pl.skill) && pl.effect.type.equals(Effect.ChangeEffectType.HERO)) {
            ConnectionPayload.CondEffectImpact impact = Utils.condTriggerChanges(this.hero, this, null, null);
            if (impact.equals(ConnectionPayload.CondEffectImpact.DISALLOW)) {
                return;
            }
            if (impact.equals(ConnectionPayload.CondEffectImpact.ALLOW) || pl.target.hasPermanentEffect(Prepared.class)) {
                pl.target.addEffect(EffectLibrary.getEffect(Initiative.class.getName(), 0, 1, null), this.hero, this);
            } else if (pl.target.hasPermanentEffect(Advantage.class)){
                pl.target.addEffect(EffectLibrary.getEffect(Prepared.class.getName(), 0, 1, null), this.hero, this);
            } else {
                pl.target.addEffect(EffectLibrary.getEffect(Advantage.class.getName(), 0, 1, null), this.hero, this);
            }
        }
    }
}
