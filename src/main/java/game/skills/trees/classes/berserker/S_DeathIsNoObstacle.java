package game.skills.trees.classes.berserker;

import framework.connector.ConnectionPayload;
import game.effects.hero.Enraged;
import game.skills.Skill;
import game.skills.logic.Stat;
import utils.MyMaths;
import utils.Utils;

public class S_DeathIsNoObstacle extends Skill {
    public void onDamage(ConnectionPayload pl) {
        if (this.hero.getStat(Stat.CURRENT_LIFE) < 0) {
            int chance = (int) keyValues.get("Chance");
            if (this.hero.hasPermanentEffect(Enraged.class)) {
                chance = (int) keyValues.get("EnragedChance");
            }
            chance = Utils.chanceChanges(this.hero, this.hero, chance, this, null, null, pl.depth + 1);
            if (MyMaths.success(chance)) {
                this.hero.getStats().put(Stat.CURRENT_LIFE, 1);
            }
        }
    }
}
