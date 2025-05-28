package game.entities.individuals.cryobrawler;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;

import java.util.List;

public class S_IceClaw extends Skill {

    public S_IceClaw(Hero hero) {
        super(hero);
        this.iconPath = "entities/cryobrawler/icons/iceclaw.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.15),
                new Multiplier(Stat.MANA, 0.15),
                new Multiplier(Stat.MAGIC, 0.15));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{3,4};
        this.dmg = 8;
        this.damageMode = DamageMode.PHYSICAL;
    }



    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
    }

    public void dmgTrigger(DmgTriggerPayload pl) {
        if (pl.cast.hero.equals(this.hero) && pl.cast instanceof S_IceClaw) {
            this.hero.addResource(Stat.CURRENT_MANA, Stat.MANA, pl.dmgDone / 2, this.hero);
        }
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Get 50% of damage done as "+Stat.MANA.getIconString()+".";
    }


    @Override
    public String getName() {
        return "Ice Claw";
    }
}
