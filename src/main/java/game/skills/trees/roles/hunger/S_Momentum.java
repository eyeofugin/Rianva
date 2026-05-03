package game.skills.trees.roles.hunger;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class S_Momentum extends Skill {
    public void deathTrigger(ConnectionPayload pl) {
        Logger.logLn("S_Momentum.deathTrigger()");
        this.hero.arena.extraTurn(this.hero);
    }
}
