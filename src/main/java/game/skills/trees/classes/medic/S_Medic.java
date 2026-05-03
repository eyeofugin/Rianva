package game.skills.trees.classes.medic;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.libraries.EffectLibrary;
import game.effects.status.Immunity;
import game.effects.status.Protected;
import game.skills.Skill;
import game.skills.logic.Stat;
import utils.Utils;

public class S_Medic extends Skill {
    public void onHeal(ConnectionPayload pl) {
        Logger.logLn("S_Medic.onHeal()");
        ConnectionPayload.CondEffectImpact impact = Utils.condTriggerChanges(this.hero, this, null, null);
        if (impact.equals(ConnectionPayload.CondEffectImpact.DISALLOW)) {
            return;
        }
        if (impact.equals(ConnectionPayload.CondEffectImpact.ALLOW) || pl.target.hasPermanentEffect(Immunity.class)) {
            pl.target.addEffect(EffectLibrary.getEffect(Protected.class.getName(), 0, 1, null), this.hero, this);
        } else if (pl.target.getStat(Stat.SHIELD) > 0){
            pl.target.addEffect(EffectLibrary.getEffect(Immunity.class.getName(), 0, 1, null), this.hero, this);
        } else {
            int shieldStrength = getShieldWithMulti(pl.target);
            pl.target.shield(shieldStrength, this.hero, this, null, null);
        }
    }
}
