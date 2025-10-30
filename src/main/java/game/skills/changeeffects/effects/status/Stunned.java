package game.skills.changeeffects.effects.status;

import game.skills.logic.Effect;

public class Stunned extends Effect {

    public static String ICON_STRING = "STU";
    public Stunned() {
        this.turns = -1;
        this.iconString = ICON_STRING;
        this.name = "Stunned";
        this.stackable = false;
        this.description = "Can't perform the next turn.";
        this.type = ChangeEffectType.DEBUFF;
    }
    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }

    @Override
    public Effect getNew() {
        return new Stunned();
    }

}
