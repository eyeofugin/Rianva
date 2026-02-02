package game.entities.heroes.thehealer;

import game.entities.Hero;
import game.skills.logic.Effect;
import game.skills.Skill;
import game.skills.logic.SkillTag;
import game.skills.logic.TargetType;

import java.util.List;

public class S_Cleanse extends Skill {

    public S_Cleanse(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/thehealer/icons/cleanse.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;
        this.level = 2;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.removeNegativeEffects();
    }

    @Override
    public int getAIRating(Hero target) {
        return target.getEffects().stream().filter(e->e.type.equals(Effect.ChangeEffectType.DEBUFF)).toList().size();
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Remove all debuffs";
    }


    @Override
    public String getName() {
        return "Cleanse";
    }
}
