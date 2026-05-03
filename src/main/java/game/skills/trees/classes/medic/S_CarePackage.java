package game.skills.trees.classes.medic;

import framework.Logger;
import game.entities.Hero;
import game.skills.Skill;

import java.util.ArrayList;
import java.util.List;

public class S_CarePackage extends Skill {

  @Override
  protected void individualResolve(Hero target) {
    Logger.logLn("S_CarePackage.individualResolve()");
    List<Hero> allyList = getAllHeroesToHeal(target);
    allyList.forEach(e -> e.heal(getHealWithMulti(e), this.hero, this, null, null, false));
    this.applySkillEffects(target);
    this.customTargetEffect(target);
  }

  private List<Hero> getAllHeroesToHeal(Hero until) {
    Logger.logLn("S_CarePackage.getAllHeroesToHeal()");
    int thisPos = this.hero.getSkillPos();
    int targetPos = until.getSkillPos();
    int dir = thisPos > targetPos ? -1 : 1;
    List<Hero> allies = new ArrayList<>();
    for (int i = thisPos + dir; i == targetPos; i += dir) {
      allies.add(this.hero.arena.getAtPosition(i));
    }
    return allies;
  }
}
