package game.entities.heroes.firedancer.stash;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;

import java.util.List;

public class S_FlameDance extends Skill {

    public S_FlameDance(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/firedancer/icons/flamedance.png";
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
        this.casterEffects = List.of(new Burning(3));
    }

    @Override
    public int getAIRating(Hero target) {
        return getRollRating(target);
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int targetPosition = target.getPosition();
        this.hero.arena.moveTo(this.hero, targetPosition);
    }

    @Override
    public String getName() {
        return "Flame Dance";
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Move to position.";
    }

}
