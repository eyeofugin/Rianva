package game.skills;

import framework.connector.Subscription;
import game.entities.Multiplier;
import game.skills.logic.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SkillDTO {

  public String name;
  public String className;
  public String description;
  public String iconPath;
  public String animationName;
  public List<SkillTag> tags = new ArrayList<>();
  public List<AiSkillTag> aiTags = new ArrayList<>();
  public TargetType targetType;
  public DamageMode damageMode;
  public DamageType damageType;
  public DamageType staticDamageType;
  public DamageMode staticDamageMode;
  public double lifeSteal = 0.0;

  public List<SkillEffectDTO> effects = new ArrayList<>();
  public List<SkillEffectDTO> casterEffects = new ArrayList<>();
  public SkillEffectDTO globalEffect;
  public List<Resource> targetResources = new ArrayList<>();
  public List<Resource> casterResources = new ArrayList<>();
  public List<Integer> staticDmgTargets = new ArrayList<>();
  public List<Multiplier> staticDmgMultipliers = new ArrayList<>();
  public List<Multiplier> dmgMultipliers = new ArrayList<>();
  public List<Multiplier> healMultipliers = new ArrayList<>();
  public List<Multiplier> shieldMultipliers = new ArrayList<>();
  public Integer manaCost = 0;
  public Integer lifeCost = 0;
  public Integer dodgeCost = 0;
  public Integer critChance = 0;
  public Integer staticDmg = 0;
  public Integer dmg = 0;
  public Integer heal = 0;
  public Integer shield = 0;
  public Integer lethality = 0;

  public Integer push = 0;
  public Integer pull = 0;
  public Integer maxCd = 0;
  public Integer accuracy = 100;
  public Boolean cannotMiss = false;
  public Integer countsAsHits = 1;
  public Integer move = 0;
  public boolean moveTo;
  public List<Subscription> subscriptions;
  public Map<String, Object> keyValues;

  public int[] possibleTargetPositions;
  public int[] possibleCastPositions;
}
