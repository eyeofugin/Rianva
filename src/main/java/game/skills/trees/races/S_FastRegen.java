package game.skills.trees.races;

import framework.connector.ConnectionPayload;
import framework.graphics.elements.EffectInfo;
import game.effects.Effect;
import game.skills.Skill;
import utils.MyMaths;
import utils.Utils;

import java.util.List;

public class S_FastRegen extends Skill {
    public void eot(ConnectionPayload pl) {
        if (!this.hero.hasDebuff()) {
            return;
        }
        int chance = Utils.chanceChanges(null, this.hero, (int)keyValues.get("Chance"), this, null, null, ++pl.depth);
        if (MyMaths.success(chance)) {
            this.hero.removeRdmEffectOfTypes(List.of(Effect.SubType.DEBUFF));
        }
    }
}
