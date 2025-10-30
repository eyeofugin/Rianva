package game.objects.equipments;

import framework.graphics.text.TextEditor;
import game.objects.Equipment;
import game.objects.equipments.skills.S_WinterOrb;
import game.skills.logic.Stat;

public class WinterOrb extends Equipment {
    public WinterOrb() {
        super("winterorb", "Winter Orb");
        this.statBonus = this.loadBaseStatBonus();
        this.skill = new S_WinterOrb(this);
    }

    @Override
    public String getDescription() {
        return "Activate: All enemies lose 5" + Stat.MANA.getIconString() + ". 5[" + TextEditor.TURN_KEY + "].";
    }
}
