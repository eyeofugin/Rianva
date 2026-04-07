package game.entities.classes;

import game.skills.logic.Stat;
import java.util.List;
import java.util.Map;

public class HeroClassDTO {
    public String name;
    public String icon;
    public List<String> learnableSkills;
    public Map<Stat, Integer> statsIncrease;
}
