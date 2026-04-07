package game.libraries;

import game.skills.Skill;
import game.skills.SkillDTO;
import utils.CollectionUtils;
import utils.FileWalker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkillLibrary {

  private static Map<String, String> baseSkillsJson = new HashMap<>();
  private static Map<String, String> cantripSkillsJson = new HashMap<>();
  private static Map<String, String> classSkillsJson = new HashMap<>();
  private static Map<String, String> roleSkillsJson = new HashMap<>();
  private static Map<String, String> weaponSkillsJson = new HashMap<>();
  private static Map<String, String> raceSkillsJson = new HashMap<>();

  public static void init() {
    baseSkillsJson = FileWalker.loadJsonMap("data/skills/baseSkills.json");
    cantripSkillsJson = FileWalker.loadJsonMap("data/skills/cantripSkills.json");
    classSkillsJson = FileWalker.loadJsonMap("data/skills/classSkills.json");
    roleSkillsJson = FileWalker.loadJsonMap("data/skills/roleSkills.json");
    weaponSkillsJson = FileWalker.loadJsonMap("data/skills/equipmentSkills.json");
    raceSkillsJson = FileWalker.loadJsonMap("data/skills/raceSkills.json");
  }

  public static List<Skill> fullSkillList() {
    List<String> allNames = new ArrayList<>();
    allNames.addAll(cantripSkillsJson.keySet());
    allNames.addAll(classSkillsJson.keySet());
    allNames.addAll(roleSkillsJson.keySet());
    allNames.addAll(weaponSkillsJson.keySet());
    allNames.addAll(raceSkillsJson.keySet());
    return allNames.stream().map(SkillLibrary::getSkillByName).toList();
  }
  public static Skill getSkillByName(String name) {
    System.out.println("Get skill by name: " +name);
    SkillDTO dto = getSkillDTO(name);
    if (dto != null && dto.className != null) {
      try {
        Class<?> skillClass = Class.forName(dto.className);
          return (Skill) skillClass.getDeclaredConstructor().newInstance();
      } catch (Exception e) {
        e.printStackTrace();
      }

    }

    return null;
  }
  public static Skill getSkillByClassName(String className) {
    System.out.println("Get skill by className: " +className);
    if (CollectionUtils.isNotEmpty(className)) {
      try {
        Class<?> skillClass = Class.forName(className);
          return (Skill) skillClass.getDeclaredConstructor().newInstance();
      } catch (Exception e) {}
    }

    return null;
  }
  public static SkillDTO getSkillDTO(String name) {
    if (baseSkillsJson.containsKey(name)) {
      return FileWalker.mapJson(SkillDTO.class, baseSkillsJson.get(name));
    } else if (classSkillsJson.containsKey(name)) {
      return FileWalker.mapJson(SkillDTO.class, classSkillsJson.get(name));
    } else if (cantripSkillsJson.containsKey(name)) {
      return FileWalker.mapJson(SkillDTO.class, cantripSkillsJson.get(name));
    } else if (roleSkillsJson.containsKey(name)) {
      return FileWalker.mapJson(SkillDTO.class, roleSkillsJson.get(name));
    } else if (weaponSkillsJson.containsKey(name)) {
      return FileWalker.mapJson(SkillDTO.class, weaponSkillsJson.get(name));
    } else if (raceSkillsJson.containsKey(name)) {
      return FileWalker.mapJson(SkillDTO.class, raceSkillsJson.get(name));
    }
    return null;
  }
}
