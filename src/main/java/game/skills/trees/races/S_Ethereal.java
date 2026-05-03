package game.skills.trees.races;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class S_Ethereal extends Skill {
  public void onHit(ConnectionPayload pl) {
    Logger.logLn("S_Ethereal.onHit()");
    this.hero.energy((int)keyValues.get("Energy"), this.hero, this, null, null, false);
  }
}