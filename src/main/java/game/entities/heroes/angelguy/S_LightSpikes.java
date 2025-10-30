package game.entities.heroes.angelguy;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_LightSpikes extends Skill {

    private int tempDmg = 0;
    public S_LightSpikes(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/angelguy/icons/lightspikes.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.ALL_ENEMY;
        this.tags = List.of(SkillTag.ULT);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 1.3));  
        this.accuracy = 80;
    }

    @Override
    public int getAIRating(Hero target) {
        if (this.hero.getCurrentLifePercentage() < 25) {
            return 2;
        }
        if (this.hero.getCurrentLifePercentage() < 50) {
            return 1;
        }
        return 0;
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Heal for 30% of the damage dealt (round up).";
    }

    @Override
    public void turn() {
//        if (cdCurrent > 0) {
//            cdCurrent--;
//        }
        if (this.tempDmg > 0) {
            this.hero.heal(this.hero, (int)(this.tempDmg*0.3), this, false);
            this.tempDmg = 0;
        }
    }
    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
    }
    public void dmgTrigger(DmgTriggerPayload pl) {
        if (this.equals(pl.cast)) {
            this.tempDmg += pl.dmgDone;
        }
    }

    @Override
    public void reset() {
        super.reset();
        this.tempDmg = 0;
    }

    @Override
    public String getName() {
        return "Light Spikes";
    }
}
