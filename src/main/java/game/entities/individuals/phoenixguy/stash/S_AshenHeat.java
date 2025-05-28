package game.entities.individuals.phoenixguy.stash;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.GlobalEffectChangePayload;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.globals.Heat;

import java.util.List;

public class S_AshenHeat extends Skill {

    public S_AshenHeat(Hero hero) {
        super(hero);
        this.iconPath = "entities/phoenixguy/icons/ashenheat.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.ARENA;
        this.possibleCastPositions = new int[]{0,1,2};
        this.faithRequirement = 40;

    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Passive: +8"+Stat.SPEED.getIconString()+" in heat.";
    }

    @Override
    public String getUpperDescriptionFor(Hero hero) {
        return "Active: Summon the global Heat effect.";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.GLOBAL_EFFECT_CHANGE, new Connection(this, GlobalEffectChangePayload.class, "globalEffectChange"));
    }

    public void globalEffectChange(GlobalEffectChangePayload pl) {
        if (pl.oldEffect instanceof Heat) {
            this.hero.addToStat(Stat.SPEED, -8);
        } else if (pl.effect instanceof Heat) {
            this.hero.addToStat(Stat.SPEED, 8);
        }
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.arena.setGlobalEffect(new Heat());
    }
    @Override
    public String getName() {
        return "Ashen Heat";
    }

}
