package game.entities.heroes.cryobrawler;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.other.Frost;
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_IceClaw extends Skill {

    public S_IceClaw(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/cryobrawler/icons/iceclaw.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.ATTACK, 0.6));
        this.effects = List.of(new Frost(1));
        this.targetType = TargetType.SINGLE;
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
