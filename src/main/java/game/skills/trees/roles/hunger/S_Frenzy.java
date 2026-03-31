package game.skills.trees.roles.hunger;

import framework.connector.ConnectionPayload;
import game.libraries.EffectLibrary;
import game.effects.hero.Initiative;
import game.skills.Skill;

public class S_Frenzy extends Skill {
    public void effectAdded(ConnectionPayload pl) {
        this.hero.addEffect(EffectLibrary.getEffect(Initiative.class.getName(), 0, 0, null), this.hero);
    }
}
