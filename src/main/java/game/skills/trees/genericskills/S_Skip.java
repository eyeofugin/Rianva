package game.skills.trees.genericskills;

import game.skills.Skill;
import game.libraries.SkillLibrary;

public class S_Skip extends Skill {
  public S_Skip() {
    super(SkillLibrary.getSkillDTO("Skip"));
  }
}