package game.entities.individuals.dragonbreather.stash;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.EndOfRoundPayload;
import game.entities.Hero;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.SkillTag;

import java.util.List;
import java.util.Random;

public class S_DragonScales extends Skill {

    public S_DragonScales(Hero hero) {
        super(hero);
        this.iconPath = "entities/dragonbreather/icons/blazingskin.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL, SkillTag.PASSIVE);
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "At the end of each turn, remove a random debuff.";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.END_OF_ROUND, new Connection(this, EndOfRoundPayload.class, "endOfRound"));
    }

    public void endOfRound(EndOfRoundPayload pl) {
        List<Effect> debuffs = this.hero.getEffects().stream().filter(e->e.type.equals(Effect.ChangeEffectType.DEBUFF) ||
                e.type.equals(Effect.ChangeEffectType.DEBUFF)).toList();
        if (!debuffs.isEmpty()) {
            Random rand = new Random();
            Effect effect = debuffs.get(rand.nextInt(debuffs.size()));
            this.hero.removePermanentEffectOfClass(effect.getClass());
        }
    }
    @Override
    public String getName() {
        return "Dragon Scales";
    }
}
