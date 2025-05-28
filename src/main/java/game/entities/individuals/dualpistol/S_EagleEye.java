package game.entities.individuals.dualpistol;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CriticalTriggerPayload;
import game.entities.Hero;
import game.skills.*;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.effects.StatEffect;

import java.util.List;

public class S_EagleEye extends Skill {

    public S_EagleEye(Hero hero) {
        super(hero);
        this.iconPath = "entities/dualpistol/icons/eagleeye.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.ULT);
        this.targetType = TargetType.SELF;
        this.possibleCastPositions = new int[]{0,1,2};
        this.effects = List.of(new StatEffect(3, Stat.CRIT_CHANCE, 100));
        this.cdMax = 5;
        this.level = 5;
    }

    @Override
    public int getAIRating(Hero target) {
        return this.hero.getCurrentLifePercentage()/20;
    }
    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.CRITICAL_TRIGGER, new Connection(this, CriticalTriggerPayload.class, "critTrigger"));
    }

    public void critTrigger(CriticalTriggerPayload pl) {
        if (pl.cast.hero.equals(this.hero)) {
            this.hero.addEffect(new Combo(), this.hero);
        }
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Passive: Whenever you crit, gain " + Combo.getStaticIconString() + ".";
    }
    @Override
    public String getName() {
        return "Eagle Eye";
    }
}
