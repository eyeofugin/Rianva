package game.skills.legacy.tactical;

import game.effects.hero.BlueFlame;
import game.skills.Skill;

public class S_BurnUp extends Skill {

    @Override
    protected void oncePerActivationEffect() {
        if (this.hero.hasPermanentEffect(BlueFlame.class)) {
            this.hero.arena.extraTurn(this.hero);
        }
    }
}