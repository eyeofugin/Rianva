package game.skills.changeeffects.effects.other;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload; 
import game.skills.logic.Effect;
import game.skills.logic.Stat;

public class LifeSteal extends Effect {

    public static String ICON_STRING = "LST";
    public LifeSteal(int turns) {
        this.turns = turns;
        this.iconString = ICON_STRING;
        this.name = "LifeSteal";
        this.stackable = false;
        this.description = "Get physical dmg dealt as life";
        this.type = ChangeEffectType.BUFF;
    }
    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }
    @Override
    public Effect getNew() {
        return new LifeSteal(this.turns);
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
    }

    public void dmgTrigger(DmgTriggerPayload pl) {
//        if (this.hero.equals(pl.cast.hero) && pl.damageMode.equals(DamageMode.PHYSICAL)) {
//            this.hero.addResource(Stat.CURRENT_LIFE, Stat.LIFE, pl.dmgDone, this.hero);
//        }
    }
}
