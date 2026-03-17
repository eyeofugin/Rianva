package game.skills.trees.classes.flora;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.effects.globals.ScorchingSun;
import game.skills.Skill;

public class S_HarvestSun extends Skill {
    public void castChange(ConnectionPayload pl) {
        if (this.hero.arena.globalEffect instanceof ScorchingSun) {
            pl.skill.getEffects().stream()
                    .filter(e -> e.turns > 0 && e.type.equals(Effect.ChangeEffectType.FIELD))
                    .forEach(e -> e.turns++);
            pl.skill.getCasterEffects().stream()
                    .filter(e -> e.turns > 0 && e.type.equals(Effect.ChangeEffectType.FIELD))
                    .forEach(e -> e.turns++);
        }
    }
}
