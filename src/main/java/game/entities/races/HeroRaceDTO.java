package game.entities.races;

import game.skills.logic.Stat;

import java.util.List;
import java.util.Map;

public class HeroRaceDTO {
    public String name;
    public String icon;
    public List<String> learnableSkills;
    public Map<Stat, Integer> stats;
}
