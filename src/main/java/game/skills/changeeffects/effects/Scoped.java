package game.skills.changeeffects.effects;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CastChangePayload;
import framework.states.Arena;
import game.entities.heroes.rifle.S_Barrage;
import game.entities.heroes.rifle.S_PiercingBolt;
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
        this.description = "All skills can hit the last position.";
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
        if (skill != null && skill.hero != null && skill.hero.equals(this.hero) && (skill instanceof S_Barrage || skill instanceof S_PiercingBolt)) {
            skill.possibleCastPositions = new int[]{3,4,5};
        }
    }
}
