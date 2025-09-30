package game.objects.equipments.skills;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.StartOfMatchPayload;
import game.entities.Hero;
import game.objects.Equipment;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.StatEffect;

import java.util.List;

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
        this.effects = List.of(new StatEffect(5, Stat.ACCURACY, -30));
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
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
    public String getName() {
        return "Pocket Darkness";
    }
}
