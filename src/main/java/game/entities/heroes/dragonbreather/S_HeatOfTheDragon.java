package game.entities.heroes.dragonbreather;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.GlobalEffectChangePayload;
import framework.states.Arena;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.globals.Heat;

import java.util.List;

public class S_HeatOfTheDragon extends Skill {

    public S_HeatOfTheDragon(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/dragonbreather/icons/heat.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.ARENA;
        this.possibleCastPositions = new int[]{0,1,2};
        this.manaCost = 6;
    }

    @Override
    public int getAIArenaRating(Arena arena) {
        int rating = 0;
        for (Hero hero : arena.getAllLivingEntities().stream().filter(e->!e.isTeam2()).toList()) {
            rating += hero.getPermanentEffectStacks(Burning.class) / 2;
        }
        return rating;
    }
    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.GLOBAL_EFFECT_CHANGE, new Connection(this, GlobalEffectChangePayload.class, "globalEffectChange"));
    }

    public void globalEffectChange(GlobalEffectChangePayload pl) {
        if (pl.oldEffect instanceof Heat) {
            this.hero.addToStat(Stat.ENDURANCE, -10);
        } else if (pl.effect instanceof Heat) {
            this.hero.addToStat(Stat.ENDURANCE, 10);
        }
    }

    @Override
    public String getName() {
        return "Heat";
    }


    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.arena.setGlobalEffect(new Heat());
    }

    @Override
    public String getUpperDescriptionFor(Hero hero) {
        return "Active: Summon the Heat global effect.";
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Passive: +10"+Stat.ENDURANCE.getIconString()+" during Heat.";
    }

}
