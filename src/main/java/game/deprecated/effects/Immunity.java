package game.skills.changeeffects.effects.other;

import game.skills.logic.Effect;

public class Immunity extends Effect {

    public static String ICON_STRING = "IMU";
    public Immunity(int turns) {
        this.turns = turns;
        this.iconString = ICON_STRING;
        this.name = "Immunity";
        this.stackable = false;
        this.description = "Cannot get new Effects.";
        this.type = ChangeEffectType.BUFF;
    }
    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }
    @Override
    public Effect getNew() {
        return new Immunity(this.turns);
    }

    @Override
    public void addSubscriptions() {

    }
}
