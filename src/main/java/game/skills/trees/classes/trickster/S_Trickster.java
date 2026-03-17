package game.skills.trees.classes.trickster;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.skills.Skill;

public class S_Trickster extends Skill {
    public void effectAdded(ConnectionPayload pl) {
        if (pl.effect.subTypes.contains(Effect.SubType.DEBUFF)) {
            pl.target.changeRandomActiveCdBy(1);
        }
    }
}
