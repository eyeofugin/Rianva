package game.entities.heroes.cryobrawler.stash;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.EndOfRoundPayload;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.logic.SkillTag;
import game.skills.logic.TargetType;
import game.skills.changeeffects.effects.other.Frost;
import game.skills.changeeffects.globals.AetherWinds;

import java.util.List;
import java.util.Random;

public class S_ColdWinds extends Skill {

    public S_ColdWinds(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/cryobrawler/icons/coldwinds.png";
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
    public int getAIRating(Hero target) {
        return 3;
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.END_OF_ROUND, new Connection(this, EndOfRoundPayload.class, "endOfRound"));
    }

    public void endOfRound(EndOfRoundPayload pl) {
        if (pl.arena.globalEffect instanceof AetherWinds) {
            Random rand = new Random();
            List<Hero> enemies = this.hero.getEnemies();
            enemies.get(rand.nextInt(enemies.size()))
                    .addEffect(new Frost(1), this.hero);
        }
    }
    @Override
    public String getUpperDescriptionFor(Hero hero) {
        return "Active: Summon the Aether Winds global effect.";
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Passive: During Aether Winds each turn a random enemy gets "+Frost.getStaticIconString()+"(1).";
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.arena.setGlobalEffect(new AetherWinds());
    }

    @Override
    public String getName() {
        return "Cold Winds";
    }
}
