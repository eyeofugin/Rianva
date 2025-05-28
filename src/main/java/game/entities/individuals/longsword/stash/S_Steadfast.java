package game.entities.individuals.longsword.stash;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.Stat;

import java.util.List;


public class S_Steadfast extends Skill {


    private int stacks = 0;

    public S_Steadfast(Hero hero) {
        super(hero);
        this.iconPath = "entities/longsword/icons/steadfast.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL, SkillTag.PASSIVE);
    }




    @Override
    public String getDescriptionFor(Hero hero) {
        return "+2"+Stat.STAMINA.getIconString()+" when receiving damage (+10 max.).";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
    }

    public void dmgTrigger(DmgTriggerPayload pl) {
        if (this.hero.equals(pl.target) && this.stacks < 10) {
            this.hero.addToStat(Stat.STAMINA, 2);
            stacks++;
        }
    }

    @Override
    public String getName() {
        return "Steadfast";
    }
}
