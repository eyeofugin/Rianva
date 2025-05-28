package game.skills.changeeffects.effects.other;

import game.skills.Effect;

public class Exhausted extends Effect {

    public static String ICON_STRING = "STU";
    public Exhausted() {
        this.name = "Exhausted";
        this.iconString = ICON_STRING;
        this.stackable = false;
        this.turns = -1;
        this.description = "This character won't perform their next turn.";
        this.type = Effect.ChangeEffectType.OTHER;
    }
    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }

    @Override
    public Effect getNew() {
        return new Exhausted();
    }
}
