package game.skills.trees.roles.hunger;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.libraries.EffectLibrary;
import game.skills.Skill;

public class S_AbuseWeakness extends Skill {
    public void critTrigger(ConnectionPayload pl) {
        Logger.logLn("S_AbuseWeakness.critTrigger()");
        pl.target.addEffect(EffectLibrary.getRandomStatDebuff(), this.hero);
    }
}
