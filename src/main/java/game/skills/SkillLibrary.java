package game.skills;

import utils.FileWalker;

import java.util.Map;

public class SkillLibrary {

    private static Map<String, String> skillsJson;

    public static void init() {
        skillsJson = FileWalker.loadJsonMap("skills.json");
    }

    public static Skill getSkill(String name) {
        if (skillsJson != null && skillsJson.containsKey(name)) {
            SkillDTO dto = FileWalker.mapJson(SkillDTO.class, skillsJson.get(name));
            return new Skill(dto);
        }
        return null;
    }
}
