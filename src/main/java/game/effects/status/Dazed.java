package game.effects.status;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.skills.logic.SkillTag;

public class Dazed extends Effect {
  public void castChange(ConnectionPayload pl) {
    pl.skill.setMaxCd(pl.skill.getMaxCd() + (int) keyValues.get("DazeCdMalus"));
  }
}
