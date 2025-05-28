package game.skills.changeeffects.effects;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CastChangePayload;
import game.skills.Effect;
import game.skills.Skill;

import java.util.Arrays;

public class Scoped extends Effect {

    public static String ICON_STRING = "SCO";
    public Scoped(int turns) {
        this.turns = turns;
        this.iconString = ICON_STRING;
        this.name = "Scoped";
        this.stackable = false;
        this.description = "All skills have +1 Range.";
        this.type = ChangeEffectType.BUFF;
    }
    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }

    @Override
    public Effect getNew() {
        return new Scoped(this.turns);
    }
    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.CAST_CHANGE, new Connection(this, CastChangePayload.class,"castChange"));
    }

    public void castChange(CastChangePayload pl) {
        Skill skill = pl.skill;
        if (skill != null && skill.hero != null && skill.hero.equals(this.hero)) {

            if (Arrays.stream(skill.possibleCastPositions).anyMatch(i -> i == 1)) {
                return;
            }
            int[] newCastPositions = Arrays.copyOf(skill.possibleCastPositions, skill.possibleCastPositions.length+1);
            newCastPositions[newCastPositions.length-1] = 1;
            Arrays.sort(newCastPositions);
            skill.possibleCastPositions = newCastPositions;
        }
    }
}
