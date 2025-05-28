package game.objects.equipments.skills;

import game.entities.Hero;
import game.objects.Equipment;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.TargetType;

public class S_GraftedExoskeleton extends Skill {

    public S_GraftedExoskeleton(Equipment equipment) {
        super(null);
        this.equipment = equipment;
        this.iconPath = "icons/skills/graftedexoskeleton.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SELF;
        this.possibleCastPositions = new int[]{0,1,2};
        this.cdMax = 5;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.removeNegativeEffects();
    }

    @Override
    public boolean performCheck(Hero hero) {
        return super.performCheck(hero) && this.equipment.isActive();
    }

    @Override
    public int getAIRating(Hero target) {
        return target.getEffects().stream().filter(e->e.type.equals(Effect.ChangeEffectType.DEBUFF)).toList().size();
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Removes all debuffs.";
    }

    @Override
    public String getName() {
        return "Grafted Skeleton";
    }
}
