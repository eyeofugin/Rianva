package game.skills.changeeffects.effects.other;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CanPerformPayload;
import game.skills.logic.Effect;

public class Threatening extends Effect {

    public static String ICON_STRING = "THR";
    public Threatening(int turns) {
        this.turns = turns;
        this.iconString = ICON_STRING;
        this.name = "Threatening";
        this.stackable = false;
        this.description = "Must be the target of single target skills.";
        this.type = ChangeEffectType.BUFF;
    }
    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }
    @Override
    public Effect getNew() {
        return new Threatening(this.turns);
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.CAN_PERFORM, new Connection(this, CanPerformPayload.class, "canPerform"));
    }

//    public void canPerform(CanPerformPayload pl) {
//        if (pl.skill.hero.isTeam2() != this.hero.isTeam2()
//                && pl.skill.getTargetType().equals(TargetType.SINGLE)
//                && pl.targetPositions[0] != this.hero.getPosition()
//                && pl.success) {
//            pl.success = Arrays.stream(pl.skill.possibleTargetPositions).noneMatch(i->i == this.hero.getPosition());
//        }
//    }

}