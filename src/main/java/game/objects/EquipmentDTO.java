package game.objects;

import game.skills.logic.Stat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EquipmentDTO {

  public String className;
  public String icon;
  public Map<Stat, Integer> statBonus = new HashMap<>();
  public boolean big = false;
  public List<String> learnableSkills = new ArrayList<>();
  public String specialSkill = "";
  public String name = "";
  public EquipmentType type;
  public Map<Stat, Integer> levelStats = new HashMap<>();
}
