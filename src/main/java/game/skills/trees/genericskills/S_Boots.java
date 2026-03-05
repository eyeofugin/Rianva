package game.skills.trees.genericskills;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillLibrary;

public class S_Boots extends Skill {

  public S_Boots(Hero hero) {
    super(SkillLibrary.getSkillDTO("Boots"));
    this.hero = hero;
  }
}
