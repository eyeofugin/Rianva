package game.objects.equipments;

import game.objects.Equipment;

public class Arkenwand extends Equipment {

    public Arkenwand() {
        super("arkenwand", "Arkenwand");
        this.statBonus = this.loadBaseStatBonus();
    }
}
