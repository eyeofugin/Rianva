package game.entities.individuals.divinemage;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;

import java.util.List;

public class S_HealingGleam extends Skill {

    public S_HealingGleam(Hero hero) {
        super(hero);
        this.iconPath = "entities/divinemage/icons/healinggleam.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.ULT);
        this.targetType = TargetType.ALL_TARGETS;
        this.heal = 10;
        this.healMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.3));
        this.possibleCastPositions = new int[]{0,1};
        this.possibleTargetPositions = new int[]{2,3};
        this.faithRequirement = 100;
        this.level = 5;
    }

    @Override
    public int getAIRating(Hero target) {
        int rating = 0;
        for (Effect effect : target.getEffects()) {
            if (effect.type != null && effect.type.equals(Effect.ChangeEffectType.DEBUFF) ||
                effect.type.equals(Effect.ChangeEffectType.DEBUFF)) {
                rating += 2;
            }
        }
        return rating;
    }


    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.removeNegativeEffects();
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Removes all debuffs.";
    }
    @Override
    public String getName() {
        return "Healing Gleam";
    }
}
