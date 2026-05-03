package game.skills.trees.classes.trickster;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.libraries.EffectLibrary;
import game.effects.status.Stunned;
import game.entities.Hero;
import game.skills.Skill;

public class S_CheapShot extends Skill {
    public void onTarget(ConnectionPayload pl) {
        Logger.logLn("S_CheapShot.onTarget()");
        if (this.equals(pl.skill)) {
            Hero target = this.targets.getFirst();
            if (target != null) {
                if (target.arena.queue.heroIsUp(target)) {
                    this.push = 1;
                } else {
                    this.effects.add(EffectLibrary.getEffect(Stunned.class.getName(), 0, 0, null));
                }
            }
        }
    }
}
