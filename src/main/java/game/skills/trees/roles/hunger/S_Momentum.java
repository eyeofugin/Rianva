package game.skills.trees.roles.hunger;

import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class S_Momentum extends Skill {
    public void deathTrigger(ConnectionPayload pl) {
        this.hero.arena.extraTurn(this.hero);
    }
}
