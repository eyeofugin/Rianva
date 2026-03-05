package game.skills.trees.genericskills;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillLibrary;

public class S_Skip extends Skill {

  public S_Skip(Hero hero) {
    super(SkillLibrary.getSkillDTO("Skip"));
    this.hero = hero;
  }
}
