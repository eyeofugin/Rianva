package game.deprecated.equipments;

import game.objects.Equipment;

public class SimpleBow extends Equipment {

    public SimpleBow() {
        super("simplebow", "Simple Bow");
        this.statBonus = this.loadBaseStatBonus();

    }
}
