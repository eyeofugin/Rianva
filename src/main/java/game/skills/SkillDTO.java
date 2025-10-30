package game.skills;


import framework.connector.ConnectionType;
import game.entities.Multiplier;
import game.skills.logic.*;

import java.util.List;
import java.util.Map;

public class SkillDTO {

    public String name;
    public String description;
    public String iconPath;
    public String animationName;
    public List<SkillTag> tags;
    public List<AiSkillTag> aiTags;
    public int level;
    public TargetType targetType;
    public DamageType damageType;
    public double lifeSteal;

    public List<SkillEffectDTO> effects;
    public List<SkillEffectDTO> casterEffects;
    public List<Resource> targetResources;
    public List<Multiplier> dmgMultipliers;
    public List<Multiplier> healMultipliers;
    public List<Multiplier> shieldMultipliers;
    public int manaCost;
    public int lifeCost;
    public int accuracy;
    public int critChance;
    public int dmg;
    public int heal;
    public int shield;
    public boolean canMiss;
    public int countAsHits;
    public int lethality;
    public int move;
    public boolean moveTo;
    public Map<ConnectionType, String> triggerMap;
}
