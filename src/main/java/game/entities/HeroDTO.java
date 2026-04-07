package game.entities;

import game.entities.classes.HeroClassDTO;
import game.entities.races.HeroRaceDTO;
import game.entities.roles.HeroRoleDTO;
import game.skills.logic.Stat;
import utils.Utils;

import java.util.List;
import java.util.Map;

public class HeroDTO {

  public String name;
  public HeroClassDTO heroClass;
  public HeroRaceDTO heroRace;
  public HeroRoleDTO heroRole;
  public String path;
  public String idleAnim;
  public String damagedAnim;
  public String actionAnim;
  public List<String> learnedSkills;
  public List<String> learnableSkills;
  public Role role;
  public Map<Stat, Integer> baseStats;

  public void calcStatsForLevel(int level) {
    baseStats = Utils.copyStats(heroRace.stats);
    for (int i = 0; i < level; i++) {
      for (Map.Entry<Stat, Integer> increase: heroClass.statsIncrease.entrySet()) {
          baseStats.compute(increase.getKey(), (k, oldVal) -> oldVal + increase.getValue());
      }
    }
  }

  public int getStat(Stat stat) {
    if (this.baseStats == null || this.baseStats.get(stat) == null) {
      return 0;
    }
    return this.baseStats.get(stat);
  }

}
