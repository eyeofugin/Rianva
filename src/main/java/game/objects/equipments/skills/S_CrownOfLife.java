package game.objects.equipments.skills;

import game.entities.Hero;
import game.entities.Multiplier;
import game.objects.Equipment;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_CrownOfLife extends Skill {

    public S_CrownOfLife(Equipment equipment) {
        super(null);
        this.equipment = equipment;
        this.iconPath = "icons/skills/crownoflife.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.ALL_TARGETS;
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{0,1,2};
        this.healMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.2));
        this.cdMax = 3;
    }

    @Override
    public boolean performCheck(Hero hero) {
        return super.performCheck(hero) && this.equipment.isActive();
    }

    @Override
    public String getName() {
        return "Crown of Life";
    }
}
