package game.deprecated.equipments;

import game.objects.Equipment;

public class BastardSword extends Equipment {

    public BastardSword() {
        super("bastardsword", "Bastard Sword");
        this.statBonus = loadBaseStatBonus();
    }
}
