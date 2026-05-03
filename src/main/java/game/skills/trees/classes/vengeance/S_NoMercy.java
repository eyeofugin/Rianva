package game.skills.trees.classes.vengeance;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.skills.Skill;
import game.skills.logic.DamageMode;
import game.skills.logic.DamageType;
import java.util.List;

import utils.MyMaths;
import utils.Utils;

public class S_NoMercy extends Skill {
  int marks = 0;

  public void onMark(ConnectionPayload pl) {
    Logger.logLn("S_NoMercy.onMark()");
    this.marks++;
  }

  public void dmgChangesMult(ConnectionPayload pl) {
    Logger.logLn("S_NoMercy.dmgChangesMult()");
    ConnectionPayload.CondEffectImpact impact =
        Utils.condTriggerChanges(this.hero, this, null, null);
    int percentage = (int) keyValues.get("BasePercentage");
    if (impact.equals(ConnectionPayload.CondEffectImpact.ALLOW) || marks > 4) {
      percentage = (int) keyValues.get("FullPercentage");
    }
    pl.dmg += MyMaths.percentageOf(percentage, pl.dmg);
  }
}
