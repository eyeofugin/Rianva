package game.entities.individuals.battleaxe;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.changeeffects.statusinflictions.Bleeding;

import java.util.List;

public class S_Bloodlust extends Skill {

    public S_Bloodlust(Hero hero) {
        super(hero);
        this.iconPath = "entities/battleaxe/icons/bloodlust.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PASSIVE, SkillTag.TACTICAL);
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Heal for 50% of life enemies lose to bleed";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.EFFECT_DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
    }

    public void dmgTrigger(DmgTriggerPayload pl) {
        if (pl.target != null && pl.target.isTeam2() != this.hero.isTeam2() && pl.effect instanceof Bleeding) {
            this.hero.heal(this.hero, pl.dmgDone*50/100, this, false);
        }
    }

    @Override
    public String getName() {
        return "Blood lust";
    }
}
