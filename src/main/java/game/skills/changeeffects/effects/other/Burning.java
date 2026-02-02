package game.skills.changeeffects.effects.other;

import game.skills.logic.Effect;
import game.skills.logic.Stat;

public class Burning extends Effect {

    public static String ICON_STRING = "BRN";
    public Burning(int stacks) {
        this.name = "Burning";
        this.iconString = ICON_STRING;
        this.stackable = true;
        this.stacks = stacks;
        this.description = "Loses 5% health per stack each turn. (1 at least)";
        this.type = ChangeEffectType.DEBUFF;
    }
    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }

    @Override
    public void turnLogic() {
        int dmg = this.hero.getStat(Stat.LIFE) * this.stacks / 20;
        dmg = Math.max(1, dmg);
        this.hero.effectDamage(dmg, this);
    }

    @Override
    public Effect getNew() {
        return new Burning(this.stacks);
    }

    @Override
    public void addSubscriptions() {}
}
