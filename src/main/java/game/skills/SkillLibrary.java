package game.skills;

import utils.FileWalker;
import java.util.Map;

public class SkillLibrary {

  private static Map<String, String> baseSkillsJson;
  private static Map<String, String> classSkillsJson;
  private static Map<String, String> roleSkillsJson;
  private static Map<String, String> weaponSkillsJson;
  private static Map<String, String> raceSkillsJson;

  public static void init() {
    baseSkillsJson = FileWalker.loadJsonMap("data/baseSkills.json");
    classSkillsJson = FileWalker.loadJsonMap("data/classSkills.json");
    roleSkillsJson = FileWalker.loadJsonMap("data/roleSkills.json");
    weaponSkillsJson = FileWalker.loadJsonMap("data/weaponSkills.json");
    raceSkillsJson = FileWalker.loadJsonMap("data/raceSkills.json");
  }

  public static SkillDTO getSkillDTO(String name) {
    if (baseSkillsJson.containsKey(name)) {
      return FileWalker.mapJson(SkillDTO.class, baseSkillsJson.get(name));
    } else if (classSkillsJson.containsKey(name)) {
      return FileWalker.mapJson(SkillDTO.class, classSkillsJson.get(name));
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
