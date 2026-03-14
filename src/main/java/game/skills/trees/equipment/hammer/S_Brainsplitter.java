package game.skills.trees.equipment.hammer;

import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class S_Brainsplitter extends Skill {
    public void onDmg(ConnectionPayload pl) {
        int totalDmg = pl.dmg + pl.shieldDmg;
        this.hero.heal(totalDmg * (int)keyValues.get("HealPercentage"), this.hero, this, null, this.equipment, false);
    }
}
