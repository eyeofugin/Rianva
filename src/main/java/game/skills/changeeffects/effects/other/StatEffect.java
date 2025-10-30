package game.skills.changeeffects.effects.other;

import game.skills.logic.Effect;
import game.skills.logic.Stat;

public class StatEffect extends Effect {
    public boolean isBuff;
    public String negates;

    public static StatEffect arcane = new StatEffect(Stat.MAGIC, true, "Arcane", "Dull");
    public static StatEffect dull = new StatEffect(Stat.MAGIC, true, "Dull", "Arcane");

    public static StatEffect strong = new StatEffect(Stat.ATTACK, true, "Strong", "Weak");
    public static StatEffect weak = new StatEffect(Stat.ATTACK, true, "Weak", "Strong");

    public static StatEffect robust = new StatEffect(Stat.DEFENSE, true, "Robust", "Frail");
    public static StatEffect frail = new StatEffect(Stat.DEFENSE, true, "Frail", "Robust");

    public static StatEffect fast = new StatEffect(Stat.SPEED, true, "Fast", "Slow");
    public static StatEffect slow = new StatEffect(Stat.SPEED, true, "Slow", "Fast");

    public static StatEffect focused = new StatEffect(Stat.ACCURACY, true, "Focused", "Blinded");
    public static StatEffect blinded = new StatEffect(Stat.ACCURACY, true, "Blinded", "Focused");

    public static StatEffect covered = new StatEffect(Stat.EVASION, true, "Covered", "Exposed");
    public static StatEffect exposed = new StatEffect(Stat.EVASION, true, "Exposed", "Covered");

    public static StatEffect lucky = new StatEffect(Stat.CRIT_CHANCE, true, "Lucky", "Jinxed");
    public static StatEffect jinxed = new StatEffect(Stat.CRIT_CHANCE, true, "Jinxed", "Lucky");

    public static StatEffect deadly = new StatEffect(Stat.LETHALITY, true, "Deadly", "Harmless");
    public static StatEffect harmless = new StatEffect(Stat.LETHALITY, true, "Harmless", "Deadly");

    public StatEffect(Stat stat, boolean isBuff, String name, String negates) {
        this.turns = 3;
        this.iconString = stat.getIconKey();
        this.stat = stat;
        this.isBuff = isBuff;
        if (this.isBuff) {
            this.statChangePercentage = 2;
            this.description = "Doubles " + this.stat.name() + " for " + this.turns + " turns.";
            this.type = ChangeEffectType.BUFF;
        } else {
            this.statChangePercentage = 0.5;
            this.description = "Halves " + this.stat.name() + " for " + this.turns + " turns.";
            this.type = ChangeEffectType.DEBUFF;
        }
        this.name = name;
        this.stackable = false;
        this.negates = negates;
    }

    @Override
    public Effect getNew() {
        return new StatEffect(this.stat, this.isBuff, this.name, this.negates);
    }
}
