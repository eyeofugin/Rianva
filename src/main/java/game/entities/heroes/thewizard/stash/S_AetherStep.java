package game.entities.heroes.thewizard.stash;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.TargetType;

import java.util.List;

public class S_AetherStep extends Skill {

    public S_AetherStep(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/thewizard/icons/aetherstep.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE_OTHER;
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{0,1,2};
        this.manaCost = 3;
        this.actionCost = 1;
    }


    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int targetPosition = target.getPosition();
        this.hero.arena.moveTo(this.hero, targetPosition);
    }

    @Override
    public int getAIRating(Hero target) {
        return getRollRating(target);
    }

    @Override
    public String getName() {
        return "Aether Step";
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "No action cost. Move to position.";
    }

}
