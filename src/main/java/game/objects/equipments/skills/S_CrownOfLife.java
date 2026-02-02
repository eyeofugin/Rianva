package game.objects.equipments.skills;

import game.entities.Multiplier;
import game.objects.Equipment;
import game.skills.Skill;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_CrownOfLife extends Skill {

    public S_CrownOfLife(Equipment equipment) {
        super();
        this.equipment = equipment;
        this.iconPath = "equipments/crownoflife/sprite.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.ALL_TARGETS;
        this.healMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.2));
    }

    @Override
    public String getName() {
        return "Crown of Life";
    }
}
