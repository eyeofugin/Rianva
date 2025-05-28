package game.objects.equipments.skills;
import game.entities.Hero;
import game.objects.Equipment;
import game.skills.Skill;
import game.skills.TargetType;

public class S_WingedBoots extends Skill {

    public S_WingedBoots(Equipment equipment) {
        super(null);
        this.equipment = equipment;
        this.iconPath = "icons/skills/wingedboots.png";
        setToInitial();
    }
    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE_OTHER;
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{0,1,2};
        this.cdMax = 5;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int targetPosition = target.getPosition();
        this.hero.arena.moveTo(this.hero, targetPosition);
    }

    @Override
    public boolean performCheck(Hero hero) {
        return super.performCheck(hero) && this.equipment.isActive();
    }

    public int getAIRating(Hero target) {
        return getRollRating(target);
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Move 1.";
    }

    @Override
    public String getName() {
        return "Winged Boots";
    }
}
