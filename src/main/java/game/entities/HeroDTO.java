package game.entities;

import game.skills.logic.Stat;

import java.util.List;
import java.util.Map;

public class HeroDTO {

    public String name;
    public String path;
    public String idleAnim;
    public String damagedAnim;
    public String actionAnim;
    public List<String> learnedSkills;
    public List<String> learnableSkills;
    public Role role;
    public Map<Stat, Integer> baseStats;

}
