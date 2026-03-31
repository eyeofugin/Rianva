package game.skills.trees.roles.hunger;

import framework.connector.ConnectionPayload;
import game.libraries.EffectLibrary;
import game.effects.hero.Advantage;
import game.skills.Skill;

public class S_PreyOnTheWeak extends Skill {
    public void dmgTrigger(ConnectionPayload pl) {

        this.hero.addEffect(EffectLibrary.getEffect(Advantage.class.getName(), 0, 0, null), this.hero);
    }
}
