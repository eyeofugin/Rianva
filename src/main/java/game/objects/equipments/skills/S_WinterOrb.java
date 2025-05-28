package game.objects.equipments.skills;

import game.entities.Hero;
import game.objects.Equipment;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

public class S_WinterOrb extends Skill {

    public S_WinterOrb(Equipment equipment) {
        super(null);
        this.equipment = equipment;
        this.iconPath = "icons/skills/winterorb.png";
        setToInitial();
    }
    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.ALL_TARGETS;
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{0,1,2};
        this.cdMax = 5;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        if (target.getSecondaryResource().equals(Stat.MANA)) {
            target.addToStat(Stat.CURRENT_MANA, -5);
        }

    }

    @Override
    public boolean performCheck(Hero hero) {
        return super.performCheck(hero) && this.equipment.isActive();
    }

    public int getAIRating(Hero target) {
        return target.getSecondaryResource().equals(Stat.MANA)? 1: 0;
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "All enemies lose 5 Mana.";
    }

    @Override
    public String getName() {
        return "Winter Orb";
    }
}
