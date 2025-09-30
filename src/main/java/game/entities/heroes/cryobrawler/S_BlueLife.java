package game.entities.heroes.cryobrawler;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.OnPerformPayload;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.Stat;

import java.util.List;


public class S_BlueLife extends Skill {

    public S_BlueLife(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/cryobrawler/icons/bluelife.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL, SkillTag.PASSIVE);
        this.level = 2;
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gain 50% of "+Stat.MANA.getIconString()+" you spend as "+Stat.LIFE.getIconString()+".";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.ON_PERFORM, new Connection(this, OnPerformPayload.class, "onPerform"));
    }

    public void onPerform(OnPerformPayload pl) {
        if (this.hero.equals(pl.skill.hero) && pl.skill.getManaCost() > 0) {
            this.hero.addResource(Stat.CURRENT_LIFE, Stat.LIFE, pl.skill.getManaCost() / 2, this.hero);
        }
    }

    @Override
    public String getName() {
        return "Blue Life";
    }
}
