package game.skills.changeeffects.statusinflictions;

import framework.Logger;
import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CanPerformPayload;
import game.skills.Effect;
import game.skills.Skill;

public class Rooted extends Effect {

    public static String ICON_STRING = "ROO";
    public Rooted(int turns) {
        this.turns = turns;
        this.iconString = ICON_STRING;
        this.name = "Rooted";
        this.stackable = false;
        this.description = "Can't use movement skills.";
        this.type = ChangeEffectType.DEBUFF;
    }
    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }
    @Override
    public Rooted getNew() {
        return new Rooted(this.turns);
    }

    @Override
    public void addSubscriptions() {

    }
}
