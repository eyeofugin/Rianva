package game.skills.trees.roles.tempo;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.libraries.EffectLibrary;
import game.skills.Skill;

public class S_WeaponExpert extends Skill {
    public void effectAdded(ConnectionPayload pl) {
        Logger.logLn("S_WeaponExpert.effectAdded()");
        this.hero.addEffect(EffectLibrary.getRandomAtkEnhance(), this.hero);
    }
}
