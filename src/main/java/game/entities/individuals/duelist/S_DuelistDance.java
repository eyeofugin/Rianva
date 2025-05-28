package game.entities.individuals.duelist;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CastChangePayload;
import framework.connector.payloads.GlobalEffectChangePayload;
import framework.connector.payloads.OnPerformPayload;
import framework.connector.payloads.StartOfTurnPayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.globals.Heat;
import game.skills.changeeffects.statusinflictions.Dazed;

import java.util.List;

public class S_DuelistDance extends Skill {

//    private boolean active = false;
//    private boolean used = false;
    public S_DuelistDance(Hero hero) {
        super(hero);
        this.iconPath = "entities/duelist/icons/duelistdance.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.ULT, SkillTag.PASSIVE);
        this.level = 5;
    }
    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.CAST_CHANGE, new Connection(this, CastChangePayload.class, "castChange"));
    }

    public void castChange(CastChangePayload pl) {
        if (pl.skill.hero.equals(this.hero)) {
            pl.skill.priority++;
        }
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "Your skills have +1 Priority.";
    }

    @Override
    public String getName() {
        return "Duelist Dance";
    }
}