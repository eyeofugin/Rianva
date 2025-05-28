package game.entities.individuals.divinemage;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.ExcessResourcePayload;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.globals.HolyLight;

import java.util.List;

public class S_SummonTheLight extends Skill {

    public S_SummonTheLight(Hero hero) {
        super(hero);
        this.iconPath = "entities/divinemage/icons/summonthelight.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.ARENA;
        this.possibleCastPositions = new int[]{0,1,2};
        this.faithRequirement = 25;
        this.faithGain = 10;
    }
    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.EXCESS_RESOURCE, new Connection(this, ExcessResourcePayload.class, "excess"));
    }

    public void excess(ExcessResourcePayload pl) {
        if (pl.source != null && pl.source.equals(this.hero) && this.hero.arena.globalEffect instanceof HolyLight) {
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
