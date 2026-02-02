package game.skills.changeeffects.effects.status;

import game.skills.logic.Effect;
import game.skills.logic.Stat;

public class Poisoned extends Effect{
    public static String ICON_STRING = "POI";
    public Poisoned() {
        this.turns = 3;
        this.iconString = ICON_STRING;
        this.name = "Poisoned";
        this.stackable = false;
        this.description = "Loses 15% health each turn. -25% Magic.";
        this.type = Effect.ChangeEffectType.DEBUFF;
        this.stat = Stat.MAGIC;
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
        return new Poisoned();
    }

    @Override
    public void addSubscriptions() {}
}
