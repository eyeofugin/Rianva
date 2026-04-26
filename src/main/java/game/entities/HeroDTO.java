package game.entities;

import java.util.List;

public class HeroDTO {

  public String name;
  public String heroClass;
  public String heroRace;
  public String heroRole;
  public String idleAnim;
  public String damagedAnim;
  public String actionAnim;
  public int[] idleKeys;
  public int[] damagedKeys;
  public int[] actionKeys;
  public List<String> equipments;
  public List<String> learnedSkills;
  public List<String> additionalSkills;
}
