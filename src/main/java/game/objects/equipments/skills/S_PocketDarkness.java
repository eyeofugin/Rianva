package game.objects.equipments.skills;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.StartOfMatchPayload;
import game.entities.Hero;
import game.objects.Equipment;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

public class S_PocketDarkness extends Skill {

    public S_PocketDarkness(Equipment equipment) {
        super(null);
        this.equipment = equipment;
        this.iconPath = "icons/skills/pocketdarkness.png";
        setToInitial();
    }
    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{3,4};
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.addToStat(Stat.ACCURACY, -15);
        this.equipment.unEquipFromHero();
    }

    @Override
    public boolean performCheck(Hero hero) {
        return super.performCheck(hero) && this.equipment.isActive();
    }

    public int getAIRating(Hero target) {
        return 2;
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Target permanently -15" + Stat.ACCURACY.getIconString() + ".";
    }

    @Override
    public String getName() {
        return "Pocket Darkness";
    }
}
