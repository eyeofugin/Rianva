package game.skills.changeeffects.effects;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CastChangePayload;
import game.entities.individuals.phoenixguy.S_Fireblast;
import game.entities.individuals.phoenixguy.stash.S_Combustion;
import game.entities.individuals.phoenixguy.S_Hotwings;
import game.skills.Effect;
import game.skills.Skill;

public class Exalted extends Effect {

    public static String ICON_STRING = "EXA";
    public Exalted(int turns) {
        this.turns = turns;
        this.iconString = ICON_STRING;
        this.name = "Exalted";
        this.stackable = false;
        this.description = "Extends the range of Hotwings and Fireblast.";
        this.type = ChangeEffectType.BUFF;
    }
    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }

    @Override
    public Effect getNew() {
        return new Exalted(this.turns);
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.CAST_CHANGE, new Connection(this, CastChangePayload.class,"castChange"));
    }

    public void castChange(CastChangePayload pl) {
        Skill skill = pl.skill;
        if (skill != null && skill.hero != null && skill.hero.equals(this.hero) && (skill instanceof S_Hotwings || skill instanceof S_Fireblast)) {

            skill.possibleCastPositions = new int[]{3,4,5};
        }
    }
}
