package game.effects.status;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.skills.logic.SkillTag;

public class Dazed extends Effect {
  public void castChange(ConnectionPayload pl) {
    if (pl.skill.hero.equals(this.hero)
        && !pl.skill.tags.contains(SkillTag.PRIMARY)
        && !pl.skill.tags.contains(SkillTag.PASSIVE)) {
      pl.skill.setMaxCd(pl.skill.getMaxCd() + (int) keyValues.get("DazeCdMalus"));
    }
  }
}
