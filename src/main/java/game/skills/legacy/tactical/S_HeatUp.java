package game.skills.legacy.tactical;

import framework.connector.ConnectionPayload;
import game.effects.status.Burning;
import game.libraries.EffectLibrary;
import game.skills.Skill;

public class S_HeatUp extends Skill {

    public void onSingleTarget(ConnectionPayload pl) {
        if (!pl.target.getRace().name.equals("Flameborn")) {
            pl.target.addEffect(EffectLibrary.getEffect(Burning.class.getName(), 1, -1, null), this.hero);
        }
    }
}