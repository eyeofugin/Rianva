package game.skills.trees.classes.berserker;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.effects.hero.Enraged;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.logic.TargetType;
import utils.Utils;

import java.util.List;

public class S_IntimidatingRoar extends Skill {

    @Override
    public void customTargetEffect(Hero target) {
        target.removeRdmEffectOfTypes(List.of(Effect.SubType.BUFF, Effect.SubType.STAT));
    }

    public void castChange(ConnectionPayload pl) {
        if (pl.skill.equals(this)) {
            ConnectionPayload.CondEffectImpact impact = Utils.condTriggerChanges(this.hero, this, null, null);
            if (impact.equals(ConnectionPayload.CondEffectImpact.ALLOW)
                    || this.hero.hasPermanentEffect(Enraged.class)) {
                this.targetType = TargetType.ALL_TARGETS;
            }
        }
    }
}
