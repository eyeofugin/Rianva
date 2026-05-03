package game.skills.trees.roles.inspiration;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class S_FreshManaPool extends Skill {

    public void castChange(ConnectionPayload pl) {
        Logger.logLn("S_FreshManaPool.castChange()");
        if (this.hero.getCurrentManaPercentage() > (int) keyValues.get("ManaCutOff")) {
            pl.skill.getEffects().stream().filter(e->e.turns > 0).forEach(e->{
                e.stacks++;
            });
            pl.skill.getCasterEffects().stream().filter(e->e.turns > 0).forEach(e->{
                e.stacks++;
            });
        }
    }
}
