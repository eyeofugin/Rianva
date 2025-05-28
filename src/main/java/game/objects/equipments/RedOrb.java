package game.objects.equipments;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.*;
import game.objects.Equipment;
import game.skills.changeeffects.effects.Burning;

public class RedOrb extends Equipment {

    public RedOrb() {
        super("redorb", "Red Orb");
        this.statBonus = this.loadBaseStatBonus();
    }

    @Override
    public String getDescription() {
        return "Get " + Burning.getStaticIconString() + "(1) at the start of each turn.";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.START_OF_TURN, new Connection(this, StartOfTurnPayload.class, "start"));
    }

    public void start(StartOfTurnPayload pl) {
        if (this.active && this.hero != null) {
            this.hero.addEffect(new Burning(1), this.hero);
        }
    }
}
