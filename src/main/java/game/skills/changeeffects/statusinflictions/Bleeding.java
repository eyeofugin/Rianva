package game.skills.changeeffects.statusinflictions;

import game.skills.Effect;
import game.skills.Stat;

public class Bleeding extends Effect {

    public static String ICON_STRING = "BLE";
    public Bleeding(int turns) {
        this.turns = turns;
        this.iconString = ICON_STRING;
        this.name = "Bleeding";
        this.stackable = false;
        this.description = "Loses 15% health each turn.";
        this.type = ChangeEffectType.DEBUFF;
    }
    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }

    @Override
    public void turnLogic() {
        int dmg = this.hero.getStat(Stat.LIFE) * 15 / 100;
        this.hero.effectDamage(dmg, this);
    }

    @Override
    public Effect getNew() {
        return new Bleeding(this.turns);
    }

    @Override
    public void addSubscriptions() {}
}
