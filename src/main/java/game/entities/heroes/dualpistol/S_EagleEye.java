package game.entities.heroes.dualpistol;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CriticalTriggerPayload;
import framework.connector.payloads.StartOfMatchPayload;
import game.entities.Hero;
import game.skills.*;
import game.skills.changeeffects.effects.StatEffect;

import java.util.List;

public class S_EagleEye extends Skill {

    int addition = 0;
    public S_EagleEye(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/dualpistol/icons/eagleeye.png";
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
        Connector.addSubscription(Connector.START_OF_MATCH, new Connection(this, StartOfMatchPayload.class, "startOfMatch"));
//        Connector.addSubscription(Connector.CRITICAL_TRIGGER, new Connection(this, CriticalTriggerPayload.class, "critTrigger"));
    }

    public void startOfMatch(StartOfMatchPayload pl ) {
        this.addition = 0;
    }
//    public void critTrigger(CriticalTriggerPayload pl) {
//        if (pl.cast.hero.equals(this.hero)) {
//            if (addition < 10) {
//                this.hero.addToStat(Stat.POWER, 1);
//                addition++;
//            }
//        }
//    }

//    @Override
//    public String getDescriptionFor(Hero hero) {
//        return "Passive: Whenever you crit, gain +1" + Stat.POWER.getIconString() + " (10 max).";
//    }
    @Override
    public String getName() {
        return "Eagle Eye";
    }
}
