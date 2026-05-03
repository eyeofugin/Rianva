package game.skills.trees.classes.strider;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.libraries.EffectLibrary;
import game.effects.hero.Advantage;
import game.skills.Skill;

public class S_TrueShot extends Skill {

    public void castChange(ConnectionPayload pl) {
        Logger.logLn("S_TrueShot.castChange()");
        if (this.equals(pl.skill) && this.hero.hasFieldEffect()) {
            pl.skill.getEffects().add(EffectLibrary.getEffect(Advantage.class.getName(), 0 ,1, null));
        }
    }
}
