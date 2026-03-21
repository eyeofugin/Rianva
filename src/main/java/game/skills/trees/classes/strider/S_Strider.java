package game.skills.trees.classes.strider;

import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class S_Strider extends Skill {
    private boolean used = false;
    public void endOfTurn(ConnectionPayload pl) {
        if (this.hero.hasFieldEffect()) {
            this.hero.percentageHeal((int)keyValues.get("HealPercentage"), this.hero, this, null, null, false);
        }
    }
    public void onGlobalEffect(ConnectionPayload pl) {
        if (used) { return; }
        this.hero.arena.extraTurn(this.hero);
    }
    public void startOfTurn(ConnectionPayload pl) {
        this.used = false;
    }
}
