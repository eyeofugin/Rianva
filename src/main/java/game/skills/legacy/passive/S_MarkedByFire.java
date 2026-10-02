package game.skills.legacy.passive;

import framework.connector.ConnectionPayload;
import game.effects.hero.BlueFlame;
import game.libraries.EffectLibrary;
import game.skills.Skill;

public class S_MarkedByFire extends Skill {

    public void onTarget(ConnectionPayload pl) {
        if (!this.hero.hasPermanentEffect(BlueFlame.class) && this.hero.getCurrentLifePercentage() < 50) {
            this.hero.addEffect(EffectLibrary.getEffect(BlueFlame.class.getName(), -1, -1, null), this.hero);
        }
    }
}
