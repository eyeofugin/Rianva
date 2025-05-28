package game.entities.individuals.dualpistol.stash;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CriticalTriggerPayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Combo;

import java.util.List;

public class S_QuickShot extends Skill {

    public S_QuickShot(Hero hero) {
        super(hero);
        this.iconPath = "entities/dualpistol/icons/quickshot.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{3,4};
        this.dmg = 2;
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.4));
    }



    @Override
    public String getDescriptionFor(Hero hero) {
        return "Whenever you crit, gain combo";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.CRITICAL_TRIGGER, new Connection(this, CriticalTriggerPayload.class, "critTrigger"));
    }

    public void critTrigger(CriticalTriggerPayload pl) {
        if (this.hero.equals(pl.cast.hero)) {
            this.hero.addEffect(new Combo(), this.hero);
        }
    }

    @Override
    public String getName() {
        return "Quick Shot";
    }
}
