package game.skills.legacy.tactical;

import game.effects.globals.ScorchingSun;
import game.effects.status.Burning;
import game.skills.Skill;


public class S_FanTheTorch extends Skill {

    public void endOfTurn() {
        if (this.hero.arena.hasGlobalEffect(ScorchingSun.class)) {
            int manaRegain = this.hero.getEnemies().stream().mapToInt(h->h.getPermanentEffectStacks(Burning.class)).sum();
            this.hero.percentageEnergy(manaRegain, this.hero, this, null, null, false);
        }
    }
}