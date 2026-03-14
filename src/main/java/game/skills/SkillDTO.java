package game.skills;

import framework.connector.Subscription;
import game.entities.Multiplier;
import game.skills.logic.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SkillDTO {

  public String name;
  public String description;
  public String iconPath;
  public String animationName;
  public List<SkillTag> tags;
  public List<AiSkillTag> aiTags;
  public TargetType targetType;
  public DamageMode damageMode;
  public DamageType damageType;
  public DamageType staticDamageType = null;
  public DamageMode staticDamageMode = null;
  public double lifeSteal;

  public List<SkillEffectDTO> effects;
  public List<SkillEffectDTO> casterEffects;
  public SkillEffectDTO globalEffect;
  public List<Resource> targetResources;
  public List<Resource> casterResources;
  public List<Integer> staticDmgTargets = new ArrayList<>();
  public List<Multiplier> staticDmgMultipliers = new ArrayList<>();
  public List<Multiplier> dmgMultipliers;
  public List<Multiplier> healMultipliers;
  public List<Multiplier> shieldMultipliers;
  public Integer manaCost;
  public Integer lifeCost;
  public Integer critChance;
  public Integer staticDmg = 0;
  public Integer dmg;
  public Integer heal;
  public Integer shield;
  public Integer lethality;

  public Integer push;
  public Integer pull;
  public Integer maxCd;
  public Integer accuracy;
  public Boolean cannotMiss;
  public Integer countAsHits;
  public Integer move;
  public boolean moveTo;
  public List<Subscription> subscriptions;
  public Map<String, Object> keyValues;

  public int[] possibleTargetPositions;
  public int[] possibleCastPositions;
}
