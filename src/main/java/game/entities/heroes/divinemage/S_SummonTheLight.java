package game.entities.heroes.divinemage;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.ExcessResourcePayload;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;
import game.skills.changeeffects.globals.HolyLight;

import java.util.List;

public class S_SummonTheLight extends Skill {

    public S_SummonTheLight(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/divinemage/icons/summonthelight.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.ARENA;
    }
    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.EXCESS_RESOURCE, new Connection(this, ExcessResourcePayload.class, "excess"));
    }

    public void excess(ExcessResourcePayload pl) {
        if (pl.source != null && pl.source.equals(this.hero) && pl.resource != null && pl.resource.equals(Stat.LIFE) && this.hero.arena.globalEffect instanceof HolyLight) {
            pl.target.shield(pl.excess, this.hero);
        }
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.arena.setGlobalEffect(new HolyLight());
    }
    @Override
    public int getAIRating(Hero target) {
        return 2;
    }

    @Override
    public String getUpperDescriptionFor(Hero hero) {
        return "Active: Summon the Holy Light global effect.";
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Passive: During Holy Light, excess healing you do is converted to "+ Stat.SHIELD.getIconString()+".";
    }


    @Override
    public String getName() {
        return "Summon the Light";
    }
}
