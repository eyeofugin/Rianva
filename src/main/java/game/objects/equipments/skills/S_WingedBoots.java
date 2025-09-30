package game.objects.equipments.skills;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.TargetType;

public class S_WingedBoots extends Skill {

    public S_WingedBoots(Hero hero) {
        super(hero);
        this.hero = hero;
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
        this.moveTo = true;
    }
    @Override
    public int getSort() {
        return 6;
    }

    public int getAIRating(Hero target) {
        return -1;
//        return getRollRating(target);
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
