package game.skills.trees.classes.berserker;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.libraries.EffectLibrary;
import game.effects.hero.Enraged;
import game.skills.Skill;
import utils.MyMaths;
import utils.Utils;

public class S_Berserker extends Skill {
    private int rage = 0;
    private boolean dealtDmg = false;
    public void dmgTrigger(ConnectionPayload pl) {
        Logger.logLn("S_Berserker.dmgTrigger()");
        if (this.hero.equals(pl.caster) || this.hero.equals(pl.target)) {
            Logger.logLn("rage++");
            rage = Math.min(5, rage + 1);
        }
        if (this.hero.equals(pl.caster)) {
            Logger.logLn("damageDone");
            dealtDmg = true;
        }
        if (rage == 5) {
            this.hero.addEffect(EffectLibrary.getEffect(Enraged.class.getName(), 0, 0, null), this.hero, this);
        }
    }
    public void startOfRound(ConnectionPayload pl) {
        Logger.logLn("S_Berserker.startOfRound()");
        dealtDmg = false;
    }
    public void endOfTurn(ConnectionPayload pl) {
        Logger.logLn("S_Berserker.endOfTurn()");
        if (!dealtDmg) {
            Logger.logLn("rage-=2");
            rage = Math.max(0, rage - 2);
            this.hero.removeEffectByName(Enraged.class.getName());
        }
    }
    public void dmgChangesMult(ConnectionPayload pl) {
        Logger.logLn("S_Berserker.dmgChangesMult()");
        ConnectionPayload.CondEffectImpact impact = Utils.condTriggerChanges(this.hero, this, null, null);
        if (impact.equals(ConnectionPayload.CondEffectImpact.DISALLOW)) {
            return;
        }
        if (ConnectionPayload.CondEffectImpact.ALLOW.equals(impact) || rage == 5) {
            int change = MyMaths.percentageOf((double)keyValues.get("DmgMitigation"), pl.dmg);
            pl.dmg += change;
        }
    }
}
