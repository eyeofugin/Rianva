package game.skills.trees.classes.berserker;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.skills.Skill;
import utils.Utils;

import java.util.List;

public class S_NeverSurrender extends Skill {
    public void startOfTurn(ConnectionPayload pl) {
        ConnectionPayload.CondEffectImpact impact = Utils.condTriggerChanges(this.hero, this, null, null);
        if (ConnectionPayload.CondEffectImpact.DISALLOW.equals(impact)) {
            return;
        }
        if (ConnectionPayload.CondEffectImpact.ALLOW.equals(impact) || this.hero.getCurrentLifePercentage() < 50) {
            this.hero.removeRdmEffectOfTypes(List.of(Effect.SubType.DEBUFF));
        }
    }
}
