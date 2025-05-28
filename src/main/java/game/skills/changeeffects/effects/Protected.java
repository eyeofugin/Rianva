package game.skills.changeeffects.effects;

import game.skills.Effect;

public class Protected extends Effect {

    public static String ICON_STRING = "PRO";
    public Protected(int turns) {
        this.name = "Protected";
        this.iconString = ICON_STRING;
        this.stackable = false;
        this.turns = turns;
        this.description = "Prevent the next enemy action.";
        this.type = ChangeEffectType.BUFF;
    }
    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }
    @Override
    public Effect getNew() {
        return new Protected(this.turns);
    }

    @Override
    public void addSubscriptions() {}
}
