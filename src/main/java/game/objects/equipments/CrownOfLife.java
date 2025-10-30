package game.objects.equipments;

import framework.graphics.text.Color;
import framework.graphics.text.TextEditor;
import game.objects.Equipment;
import game.objects.equipments.skills.S_CrownOfLife;
import game.skills.logic.Stat;

public class CrownOfLife extends Equipment {

    public CrownOfLife() {
        super("crownoflife", "Crown of Life");
        this.statBonus = this.loadBaseStatBonus();
        this.skill = new S_CrownOfLife(this);
    }

    @Override
    public String getDescription() {
        return "Active: Heal all allies for "+Stat.MAGIC.getColorKey()+"20%"+ Color.WHITE.getCodeString() + Stat.MAGIC.getIconString()+". ["+ TextEditor.TURN_KEY+"]3.";
    }
}
