package game.entities.individuals.dualpistol.stash;

import game.entities.Hero;
import game.skills.*;
import game.skills.changeeffects.effects.Combo;

import java.util.List;

public class S_Roll extends Skill {

    public S_Roll(Hero hero) {
        super(hero);
        this.iconPath = "entities/dualpistol/icons/roll.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.aiTags = List.of(AiSkillTag.COMBO_ENABLED);
        this.targetType = TargetType.SINGLE_OTHER;
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{0,1,2};
        this.cdMax = 3;
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "Move to position.";
    }

    @Override
    public String getComboDescription(Hero hero) {
        return "Get +2" + Stat.SPEED.getIconString() + ".";
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
        if (this.hero.hasPermanentEffect(Combo.class)> 0) {
            this.hero.removePermanentEffectOfClass(Combo.class);
            this.hero.addToStat(Stat.SPEED, 2);
        }
    }
    @Override
    public String getName() {
        return "Roll";
    }
}
