package game.skills.changeeffects.effects;

import game.skills.Effect;
import game.skills.Stat;

public class StatEffect extends Effect {

    public static String ICON_STRING = "STE";
    public StatEffect(int turns, Stat stat, int statChange) {
        this.turns = turns;
        this.iconString = stat.getIconKey();
        this.stat = stat;
        this.statChange = statChange;
        this.name = this.stat.name();
        this.stackable = false;
        this.description = "Gain " + statChange + " " + this.stat.name() + ".";
        this.type = ChangeEffectType.STAT_CHANGE;
    }

    @Override
    public Effect getNew() {
        return new StatEffect(this.turns, this.stat, this.statChange);
    }
}
