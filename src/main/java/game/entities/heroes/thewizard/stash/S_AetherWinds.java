package game.entities.heroes.thewizard.stash;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.GlobalEffectChangePayload;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.globals.AetherWinds;

import java.util.List;

public class S_AetherWinds extends Skill {

    public S_AetherWinds(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/thewizard/icons/aetherwinds.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.ARENA;
        this.possibleCastPositions = new int[]{0,1,2};
        this.manaCost = 4;
    }


    @Override
    public int getAIRating(Hero target) {
        return 3;
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.GLOBAL_EFFECT_CHANGE, new Connection(this, GlobalEffectChangePayload.class, "globalEffectChange"));
    }

    public void globalEffectChange(GlobalEffectChangePayload pl) {
        if (pl.oldEffect instanceof AetherWinds) {
            this.hero.addToStat(Stat.EVASION, -10);
        } else if (pl.effect instanceof AetherWinds) {
            this.hero.addToStat(Stat.EVASION, 10);
        }
    }
    @Override
    public String getUpperDescriptionFor(Hero hero) {
        return "Active: Summon the Aether Winds global effect.";
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Passive: +10"+Stat.EVASION.getIconString()+" during Aether Winds.";
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.arena.setGlobalEffect(new AetherWinds());
    }

    @Override
    public String getName() {
        return "Aether Winds";
    }
}
