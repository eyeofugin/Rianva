package game.entities.classes;

import game.skills.logic.Stat;

import java.util.List;
import java.util.Map;

public class HeroClass {
    public String name;
    public String icon;
    public ClassBackground magicalBackground;
    public List<String> learnableSkills;
    public Map<Stat, Integer> statsIncrease;
}
