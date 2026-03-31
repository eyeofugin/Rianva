package game.skills.trees.roles.tempo;

import framework.connector.ConnectionPayload;
import game.libraries.EffectLibrary;
import game.effects.hero.Initiative;
import game.entities.Hero;
import game.skills.Skill;

public class S_Commandeer extends Skill {
    public void onTarget(ConnectionPayload pl) {
        if (this.equals(pl.skill)) {
            Hero target = this.targets.getFirst();
            if (target != null) {
                if (target.arena.queue.heroIsUp(target)) {
                    this.push = 1;
                } else {
                    this.effects.add(EffectLibrary.getEffect(Initiative.class.getName(), 0, 0, null));
                }
            }
        }
    }
}
