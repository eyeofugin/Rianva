package game.skills.trees.roles.inspiration;

import game.skills.Skill;
import game.skills.SkillLibrary;

public class S_WindChant extends Skill {

  public S_WindChant() {
    super(SkillLibrary.getSkillDTO("Wind Chant"));
    addSubscriptions();
  }
}
