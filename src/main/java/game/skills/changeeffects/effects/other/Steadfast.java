package game.skills.changeeffects.effects.other;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgChangesPayload;
import framework.connector.payloads.StartOfTurnPayload;
import game.skills.Effect;

public class Steadfast extends Effect {


    public static String ICON_STRING = "STE";

    public Steadfast(int stacks){
        this.stackable = true;
        this.iconString = ICON_STRING;
        this.stacks = stacks;
        this.type = ChangeEffectType.BUFF;
        this.description = "10% dmg reduction per stack";
        this.name = "Steadfast";

    }
    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }
    @Override
    public Effect getNew() {
        return new Steadfast(this.stacks);
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(this.hero.getId() + Connector.START_OF_TURN, new Connection(this, StartOfTurnPayload.class, "startOfTurn"));
        Connector.addSubscription(Connector.DMG_CHANGES, new Connection(this, DmgChangesPayload.class, "dmgChanges"));
    }

    public void startOfTurn(StartOfTurnPayload pl) {
        this.hero.removeStack(Steadfast.class);
    }
    public void dmgChanges(DmgChangesPayload pl) {
        if (this.hero.equals(pl.target)) {
            int reduction = pl.dmg*10*stacks/100;
            reduction = Math.max(reduction,0);
            pl.dmg-=reduction;
        }
    }
}
