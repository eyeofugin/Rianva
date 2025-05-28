package game.objects.equipments;

import game.objects.Equipment;
import game.objects.equipments.skills.S_PocketDarkness;
import game.skills.Stat;
import game.skills.changeeffects.effects.RegenBoost;

public class PocketDarkness extends Equipment {
    public PocketDarkness() {
        super("pocketdarkness", "Pocket Darkness");
        this.tempStatBonus = this.loadTempStatBonus();
        this.skill = new S_PocketDarkness(this);
    }

    @Override
    public String getInfoStatBonus() {
        return getTempStatBonusString();
    }

    @Override
    public String getDescription() {
        return "Consume: Give target -30" + Stat.ACCURACY.getIconString() + ".";
    }
}
