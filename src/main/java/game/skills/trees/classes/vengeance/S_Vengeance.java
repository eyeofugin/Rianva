package game.skills.trees.classes.vengeance;

import framework.connector.ConnectionPayload;
import game.effects.EffectLibrary;
import game.effects.hero.Marked;
import game.entities.Hero;
import game.skills.Skill;

public class S_Vengeance extends Skill {
    public void dmgTrigger(ConnectionPayload pl) {
        Hero caster = pl.caster;
        caster.addEffect(EffectLibrary.getEffect(Marked.class.getName(), 1, -1, null), this.hero, this);
    }
    public void onMark(ConnectionPayload pl) {
        this.hero.energy((int) keyValues.get("Energy"), this.hero, this, null, null, false);
        this.hero.heal((int) keyValues.get("Heal"), this.hero, this, null, null, false);
    }
}
