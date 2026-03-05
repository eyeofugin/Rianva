package game.skills.changeeffects.effects.status;

import game.skills.logic.Effect;
import game.skills.logic.Stat;

public class Bleeding extends Effect {

    public static String ICON_STRING = "BLE";
    public Bleeding(int turns) {
        this.turns = turns;
        this.iconString = ICON_STRING;
        this.name = "Bleeding";
        this.stackable = false;
        this.description = "Loses 15% health each turn. -25% Attack.";
        this.type = ChangeEffectType.STATUS;
        this.stat = Stat.ATTACK;
        this.statChangePercentage = 0.75;
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
