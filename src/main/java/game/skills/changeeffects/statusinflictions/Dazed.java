package game.skills.changeeffects.statusinflictions;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CastChangePayload;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.genericskills.S_Skip;

public class Dazed extends Effect {

    public static String ICON_STRING = "DAZ";
    public Dazed(int turns) {
        this.turns = turns;
        this.iconString = ICON_STRING;
        this.name = "Dazed";
        this.stackable = false;
        this.description = "CD for skills is +1.";
        this.type = ChangeEffectType.DEBUFF;
    }

    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }
    @Override
    public Effect getNew() {
        return new Dazed(this.turns);
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.CAST_CHANGE, new Connection(this, CastChangePayload.class, "castChange"));
    }

    public void castChange(CastChangePayload castChangePayload) {
        Skill skill = castChangePayload.skill;
        if (skill != null && skill.hero.equals(this.hero) && !skill.isPassive() && !(skill instanceof S_Skip)) {
            skill.setCdMax(skill.getCdMax() + 1);
        }
    }
}
