package game.skills.trees.classes.berserker;

import framework.connector.ConnectionPayload;
import game.effects.EffectLibrary;
import game.effects.hero.Enraged;
import game.skills.Skill;
import utils.MyMaths;
import utils.Utils;

public class S_Berserker extends Skill {
    private int rage = 0;
    private boolean dealtDmg = false;
    public void dmgTrigger(ConnectionPayload pl) {
        if (this.hero.equals(pl.caster) || this.hero.equals(pl.target)) {
            rage = Math.min(5, rage + 1);
        }
        if (this.hero.equals(pl.caster)) {
            dealtDmg = true;
        }
        if (rage == 5) {
            this.hero.addEffect(EffectLibrary.getEffect(Enraged.class.getName(), 0, 0, null), this.hero, this);
        }
    }
    public void startOfRound(ConnectionPayload pl) {
        dealtDmg = false;
    }
    public void endOfTurn(ConnectionPayload pl) {
        if (!dealtDmg) {
            rage = Math.max(0, rage - 2);
            this.hero.removeEffectByName(Enraged.class.getName());
        }
    }
    public void dmgChangesMult(ConnectionPayload pl) {
        ConnectionPayload.CondEffectImpact impact = Utils.condTriggerChanges(this.hero, this, null, null, pl.depth + 1);
        if (impact.equals(ConnectionPayload.CondEffectImpact.DISALLOW)) {
            return;
        }
        if (ConnectionPayload.CondEffectImpact.ALLOW.equals(impact) || rage == 5) {
            int change = MyMaths.percentageOf((double)keyValues.get("DmgMitigation"), pl.dmg);
            pl.dmg += change;
        }
    }
}
