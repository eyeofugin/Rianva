package game.objects.equipments.skills;

import game.entities.Hero;
import game.objects.Equipment;
import game.skills.Skill;
import game.skills.logic.TargetType;
import game.skills.changeeffects.effects.other.StatEffect;

import java.util.List;

public class S_PocketDarkness extends Skill {

    public S_PocketDarkness(Equipment equipment) {
        super();
        this.equipment = equipment;
        this.iconPath = "icons/skills/pocketdarkness.png";
        setToInitial();
    }
    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.effects = List.of(StatEffect.blinded.getNew());
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.equipment.unEquipFromHero();
    }

    public int getAIRating(Hero target) {
        return 2;
    }

    @Override
    public String getName() {
        return "Pocket Darkness";
    }
}
