package framework.connector;

import game.effects.Effect;
import game.entities.Hero;
import game.objects.EquipmentType;
import game.skills.Skill;
import game.skills.logic.*;
import utils.CollectionUtils;

import java.util.HashSet;
import java.util.List;

public class SubscriptionTrigger {

  public Effect effect;
  public Skill skill;
  public int position;
  private ConnectionPayload pl;

  SubscriptionTriggerHeroReference targetHeroReference;
  SubscriptionTriggerHeroReference casterHeroReference;
  Boolean samePosition;
  Boolean dmgTypeIsElemental;
  Boolean statIsElementalDef;
  Boolean skillNotNull;
  Boolean equipmentNotNull;
  Boolean hasEnergyCost;
  Boolean targetHasDebuff;
  Boolean targetHasStatusDebuff;
  Boolean targetHasStatusBuff;
  String skillName;
  ConditionalList<EquipmentType> equipmentTypes;
  ConditionalList<DamageType> damageTypes;
  ConditionalList<DamageMode> damageModes;
  ConditionalList<Stat> stats;
  ConditionalList<String> effects;
  ConditionalList<String> targetEffects;
  ConditionalList<String> casterEffects;
  ConditionalList<TargetType> targetTypes;
  ConditionalList<SkillTag> skillTags;

  public boolean triggered(ConnectionPayload pl) {
    this.pl = pl;
    return isTargetHeroReferenceMet()
        && isCasterHeroReferenceMet()
        && isSamePositionMet()
        && dmgTypeIsElementalMet()
        && statIsElementalDefMet()
        && skillNotNull()
        && equipmentNotNull()
        && equipmentTypeMet()
        && hasEnergyCostMet()
        && targetHasStatusBuffMet()
        && targetHasStatusDebuffMet()
        && targetHasDebuffMet()
        && checkDamageTypes()
        && checkDamageMode()
        && checkStat()
        && checkEffects()
        && checkCasterEffects()
        && checkTargetEffects()
        && checkTargetTypes()
        && checkSkillTags()
        && checkSkillName();
  }

  private boolean checkDamageTypes() {
    if (damageTypes == null) {
      return true;
    }
    return conditionalListIsMet(damageTypes, List.of(pl.damageType));
  }

  private boolean checkSkillName() {
    if (skillName == null) {
      return true;
    }
    return pl.skill != null && pl.skill.name.equals(skillName);
  }

  private boolean checkDamageMode() {
    if (damageModes == null) {
      return true;
    }
    return conditionalListIsMet(damageModes, List.of(pl.damageMode));
  }

  private boolean checkStat() {
    if (stats == null) {
      return true;
    }
    return conditionalListIsMet(stats, List.of(pl.stat));
  }

  private boolean checkEffects() {
    if (effects == null) {
      return true;
    }
    if (pl.effect == null) {
      return false;
    }
    return conditionalListIsMet(effects, List.of(pl.effect.name));
  }

  private boolean checkTargetEffects() {
    if (targetEffects == null) {
      return true;
    }
    return checkHeroEffects(targetEffects, pl.target);
  }

  private boolean checkCasterEffects() {
    if (casterEffects == null) {
      return true;
    }
    return checkHeroEffects(casterEffects, pl.caster);
  }

  private boolean checkHeroEffects(ConditionalList<String> condList, Hero hero) {
    if (hero == null || CollectionUtils.isEmpty(hero.getEffects())) {
      return false;
    }
    List<String> targetEffectNames = hero.getEffects().stream().map(Effect::getName).toList();
    return conditionalListIsMet(condList, targetEffectNames);
  }

  private boolean checkTargetTypes() {
    if (targetTypes == null) {
      return true;
    }
    return pl.skill != null && conditionalListIsMet(targetTypes, List.of(pl.skill.getTargetType()));
  }

  private boolean checkSkillTags() {
    if (skillTags == null) {
      return true;
    }
    return pl.skill != null && conditionalListIsMet(skillTags, pl.skill.tags);
  }

  private boolean equipmentTypeMet() {
    if (equipmentTypes == null) {
      return true;
    }
    return pl.equipment != null && conditionalListIsMet(equipmentTypes, List.of(pl.equipment.getType()));
  }
  private <T> boolean conditionalListIsMet(
      ConditionalList<T> checkList, List<T> connectionObjects) {
    if (checkList.objects == null) {
      return false;
    }
    switch (checkList.boolLogic) {
      case SOME_OF -> {
        return hasSome(connectionObjects, checkList.objects);
      }
      case NONE_OF -> {
        return hasNone(connectionObjects, checkList.objects);
      }
      case ALL_OF -> {
        return hasAll(connectionObjects, checkList.objects);
      }
    }
    return false;
  }

  public <T> boolean hasSome(List<T> connectionObjects, List<T> check) {
    return check.stream().anyMatch(connectionObjects::contains);
  }

  public <T> boolean hasNone(List<T> connectionObjects, List<T> check) {
    return check.stream().noneMatch(connectionObjects::contains);
  }

  public <T> boolean hasAll(List<T> connectionObjects, List<T> check) {
    return new HashSet<>(connectionObjects).containsAll(check);
  }

  private boolean targetHasStatusBuffMet() {
    if (targetHasStatusBuff == null) {
      return true;
    }
    return pl.target != null && pl.target.hasStatusBuff();
  }

  private boolean targetHasStatusDebuffMet() {
    if (targetHasStatusDebuff == null) {
      return true;
    }
    return pl.target != null && pl.target.hasStatusDebuff();
  }

  private boolean targetHasDebuffMet() {
    if (targetHasDebuff == null) {
      return true;
    }
    return pl.target != null && pl.target.hasDebuff();
  }

  private boolean hasEnergyCostMet() {
    if (hasEnergyCost == null) {
      return true;
    }
    return pl.skill != null && pl.skill.getManaCost() != 0;
  }

  private boolean skillNotNull() {
    if (skillNotNull == null) {
      return true;
    }
    return pl.skill != null;
  }

  private boolean equipmentNotNull() {
    if (equipmentNotNull == null) {
      return true;
    }
    return pl.equipment != null;
  }


  private boolean statIsElementalDefMet() {
    if (statIsElementalDef == null) {
      return true;
    }
    return Stat.elementalResistances.contains(pl.stat);
  }

  private boolean dmgTypeIsElementalMet() {
    if (dmgTypeIsElemental == null) {
      return true;
    }
    return pl.skill != null
        && pl.skill.getDamageType() != null
        && pl.skill.getDamageType().isElemental();
  }

  private boolean isSamePositionMet() {
    if (samePosition == null) {
      return true;
    }
    return pl.target != null && pl.target.getPosition() == this.position;
  }

  private boolean isTargetHeroReferenceMet() {
    if (targetHeroReference == null) {
      return true;
    }
    return isHeroReferenceMet(targetHeroReference, pl.target);
  }

  private boolean isCasterHeroReferenceMet() {
    if (casterHeroReference == null) {
      return true;
    }
    return isHeroReferenceMet(casterHeroReference, pl.caster);
  }

  private boolean isHeroReferenceMet(SubscriptionTriggerHeroReference reference, Hero plHero) {
    if (reference == null) {
      return true;
    }
    Hero hero = getThisHero();
    if (hero == null) {
      return false;
    }
    switch (reference) {
      case SAME -> {
        return hero.equals(plHero);
      }
      case OTHER -> {
        return !hero.equals(plHero);
      }
      case ALLY -> {
        return hero.isAlly(plHero);
      }
      case OTHER_ALLY -> {
        return hero.isAlly(plHero) && !hero.equals(plHero);
      }
      case ENEMY -> {
        return !hero.isAlly(plHero);
      }
    }
    return false;
  }

  private Hero getThisHero() {
    if (this.effect != null) {
      return this.effect.hero;
    }
    if (this.skill != null) {
      return this.skill.hero;
    }
    return null;
  }

  public void setEffect(Effect effect) {
    this.effect = effect;
  }

  public void setSkill(Skill skill) {
    this.skill = skill;
  }

  public void setPosition(int position) {
    this.position = position;
  }
}
