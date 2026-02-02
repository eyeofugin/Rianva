package game.objects.equipments;

import framework.graphics.text.TextEditor;
import game.objects.Equipment;

public class WingedBoots extends Equipment {

    public WingedBoots() {
        super("wingedboots", "Winged Boots");
        this.statBonus = this.loadBaseStatBonus();
//        this.skill = new S_WingedBoots(this);
    }

    @Override
    public String getDescription() {
        return "Activate: Move to any place. 5[" + TextEditor.TURN_KEY + "].";
    }

}
