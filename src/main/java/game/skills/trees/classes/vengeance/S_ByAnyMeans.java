package game.skills.trees.classes.vengeance;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.logic.DamageMode;
import game.skills.logic.DamageType;
import utils.MyMaths;
import utils.Utils;

public class S_ByAnyMeans extends Skill {
    int marks = 0;
    public void onMark(ConnectionPayload pl) {
        Logger.logLn("S_ByAnyMeans.onMark()");
        this.marks++;
    }

    @Override
    public void customTargetEffect(Hero target) {
        Logger.logLn("S_ByAnyMeans.customTargetEffect()");
        int damageDone = target.percentageDamage((int)keyValues.get("DmgPercentage"), DamageType.DARK, DamageMode.MAGICAL, this.hero, this, null, null, 0);
        this.hero.heal(MyMaths.percentageOf((int)keyValues.get("HealPercentage"), damageDone),this.hero, this, null, null, false);
    }

    @Override
    public void oncePerActivationEffect() {
        Logger.logLn("S_ByAnyMeans.oncePerActivationEffect()");
        ConnectionPayload.CondEffectImpact impact = Utils.condTriggerChanges(this.hero, this, null, null);
        if (impact.equals(ConnectionPayload.CondEffectImpact.ALLOW) || marks > 9) {
            this.hero.arena.extraTurn(this.hero);
        }
    }
}
