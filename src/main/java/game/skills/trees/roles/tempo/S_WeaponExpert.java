package game.skills.trees.roles.tempo;

import framework.connector.ConnectionPayload;
import game.libraries.EffectLibrary;
import game.skills.Skill;

public class S_WeaponExpert extends Skill {
    public void effectAdded(ConnectionPayload pl) {
        this.hero.addEffect(EffectLibrary.getRandomAtkEnhance(), this.hero);
    }
}
