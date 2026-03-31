package game.skills.trees.roles.resolve;

import framework.connector.ConnectionPayload;
import game.objects.EquipmentType;
import game.skills.Skill;

public class S_Bulwark extends Skill {
    public void isMoveLegal(ConnectionPayload pl) {
        if (this.hero.getEquipments().stream().anyMatch(e->
            EquipmentType.ARMOR.equals(e.getType()) && e.isBig())) {
            pl.failure = true;
        }
    }
}
