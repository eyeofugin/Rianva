package game.skills.trees.classes.flora;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.skills.Skill;
import utils.MyMaths;
import utils.Utils;

import java.util.List;

public class S_SpreadingVines extends Skill {
    public void endOfRound(ConnectionPayload pl) {
        int chance = (int) keyValues.get("Chance");
        if (this.hero.arena.globalEffect != null) {
            chance = (int) keyValues.get("WeatherChance");
        }
        int finalChance = Utils.chanceChanges(null, this.hero, chance,this, null, null);
        List<Effect> myFieldEffects = this.hero.arena.fieldEffects.stream().filter(e->e.origin.equals(this.hero)).toList();
        myFieldEffects.forEach(e -> {
            if (MyMaths.success(finalChance)) {
                e.randomSpread();
            }
        });
    }
}
