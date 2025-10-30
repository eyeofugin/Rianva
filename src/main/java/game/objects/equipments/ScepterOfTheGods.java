package game.objects.equipments;

import game.objects.Equipment;
import game.skills.logic.Stat;

public class ScepterOfTheGods extends Equipment {

    public ScepterOfTheGods() {
        super("scepterofthegods", "Scepter of the Gods");
        this.statBonus = this.loadBaseStatBonus();
    }

    @Override
    public String getDescription() {
        return"";//return "Get 10" + Stat.FAITH.getIconString() + " at the end of each turn.";
    }

    @Override
    public void turn() {
//        if (this.active && this.hero.getSecondaryResource().equals(Stat.FAITH)) {
//            this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 10, this.hero);
//        }
    }
}
