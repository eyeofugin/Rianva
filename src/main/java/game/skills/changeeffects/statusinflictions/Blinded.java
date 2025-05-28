package game.skills.changeeffects.statusinflictions;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CastChangePayload;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.TargetType;

import java.util.Arrays;

public class Blinded extends Effect {

    public static String ICON_STRING = "BLI";
    public Blinded(int turns) {
        this.turns = turns;
        this.iconString = ICON_STRING;
        this.name = "Blinded";
        this.stackable = false;
        this.description = "Single target skills have -1 range.";
        this.type = ChangeEffectType.DEBUFF;
    }
    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }

    @Override
    public Effect getNew() {
        return new Blinded(this.turns);
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.CAST_CHANGE, new Connection(this, CastChangePayload.class,"castChange"));
    }

    public void castChange(CastChangePayload castChangePayload) {
        Skill skill = castChangePayload.skill;
        if (skill != null && skill.hero.equals(this.hero)) {
            if (skill.getTargetType().equals(TargetType.SINGLE)) {
                skill.possibleCastPositions = Arrays.copyOfRange(skill.possibleCastPositions, 1, skill.possibleCastPositions.length);
            }
        }
    }
}
