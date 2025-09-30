package game.skills.changeeffects.effects;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CanPerformPayload;
import game.skills.Effect;
import game.skills.genericskills.S_Skip;

public class Taunted extends Effect {

    public static String ICON_STRING = "TAU";
    public Taunted(int turns) {
        this.turns = turns;
        this.iconString = ICON_STRING;
        this.name = "Taunted";
        this.stackable = false;
        this.description = "Can only perform skills that do dmg.";
        this.type = ChangeEffectType.DEBUFF;

    }
    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }

    public void addSubscriptions() {
        Connector.addSubscription(Connector.CAN_PERFORM, new Connection(this, CanPerformPayload.class, "performCheck"));
    }

    @Override
    public Taunted getNew() {
        return new Taunted(this.turns);
    }

    public void performCheck(CanPerformPayload payload) {
        if (payload.skill.hero.equals(this.hero) && payload.success) {
            boolean isDmg = payload.skill.getDmg(null)>0 || !payload.skill.getDmgMultipliers().isEmpty();
            if (!isDmg && !(payload.skill instanceof S_Skip)) {
                payload.success = false;
            }
        }
    }
}
