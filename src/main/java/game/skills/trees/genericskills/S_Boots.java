package game.skills.trees.genericskills;

import game.skills.Skill;
import game.skills.SkillLibrary;

public class S_Boots extends Skill {
  public S_Boots() {
    super(SkillLibrary.getSkillDTO("Boots"));
    addSubscriptions();
  }
}
