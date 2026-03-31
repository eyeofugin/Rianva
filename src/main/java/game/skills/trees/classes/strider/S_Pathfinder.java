package game.skills.trees.classes.strider;

import framework.connector.ConnectionPayload;
import game.libraries.EffectLibrary;
import game.effects.field.Cover;
import game.skills.Skill;

public class S_Pathfinder extends Skill {

    public void onMove(ConnectionPayload pl)  {
        if (!pl.forced) {
            pl.target.changeRandomActiveCdBy(-1);
            this.hero.arena.addFieldEffect(pl.value, EffectLibrary.getEffect(Cover.class.getName(), 0, 1, null), this.hero);
        }
    }
}
