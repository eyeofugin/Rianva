package game.entities.heroes.duelist;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CastChangePayload;
import game.entities.Hero;
import game.skills.*;
import java.util.List;

public class S_DuelistDance extends Skill {

    public S_DuelistDance(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/duelist/icons/duelistdance.png";
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