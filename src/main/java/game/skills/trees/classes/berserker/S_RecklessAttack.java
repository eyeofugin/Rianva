package game.skills.trees.classes.berserker;

import framework.connector.ConnectionPayload;
import game.objects.EquipmentType;
import game.skills.Skill;
import utils.MyMaths;

public class S_RecklessAttack extends Skill {
  public void statChange(ConnectionPayload pl) {
    if (this.hero.getEquipments().stream()
        .noneMatch(e -> e.getType().equals(EquipmentType.TORSO))) {
      int increase = MyMaths.percentageOf((double)keyValues.get("Percentage") * this.hero.getLevel(), pl.value);
      pl.value += increase;
    }
  }
}
