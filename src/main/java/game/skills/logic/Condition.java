package game.skills.logic;

import game.effects.Effect;
import game.entities.Hero;
import game.skills.Skill;
import utils.MyMaths;
import utils.Utils;

public class Condition {
  public ConditionReference trigger;

  public String effectName;
  public Boolean hasCheck;

  public Boolean hasStatus;

  public Boolean healthComparison;
  public Boolean manaComparison;
  public Double percentage;
  public Boolean lessThan;

  public Integer successChance;
  public Stat successByCasterStat;

  public Condition setTrigger(ConditionReference trigger) {
    this.trigger = trigger;
    return this;
  }

  public Condition setEffectName(String effectName) {
    this.effectName = effectName;
    return this;
  }

  public Condition setHasCheck(Boolean hasCheck) {
    this.hasCheck = hasCheck;
    return this;
  }

  public Condition setHasStatus(Boolean hasStatus) {
    this.hasStatus = hasStatus;
    return this;
  }

  public Condition setHealthComparison(Boolean healthComparison) {
    this.healthComparison = healthComparison;
    return this;
  }

  public Condition setManaComparison(Boolean manaComparison) {
    this.manaComparison = manaComparison;
    return this;
  }

  public Condition setPercentage(Double percentage) {
    this.percentage = percentage;
    return this;
  }

  public Condition setLessThan(Boolean lessThan) {
    this.lessThan = lessThan;
    return this;
  }

  public Condition setSuccessChance(Integer successChance) {
    this.successChance = successChance;
    return this;
  }

  public Condition setSuccessByCasterStat(Stat successByCasterStat) {
    this.successByCasterStat = successByCasterStat;
    return this;
  }

  public boolean isMet(Skill skill, Effect effect, Hero hero, Hero target) {
    return successCheck(hero, target, skill, effect)
        && hasStatusCheck(hero, target)
        && hasEffectCheck(hero, target)
        && healthCheck(hero, target);
  }

  private boolean successCheck(Hero hero, Hero target, Skill skill, Effect effect) {

    if (successChance != null) {
      int value;
      if (successByCasterStat != null) {
        value = hero.getStat(successByCasterStat) * this.successChance / 100;
      } else {
        value = this.successChance;
      }
      value = Utils.chanceChanges(target, hero, value, skill, null, effect, 1);
      return MyMaths.success(value);
    }
    return true;
  }

  private boolean hasStatusCheck(Hero hero, Hero target) {

    if (hasStatus != null) {
      boolean statusExists = false;
      switch (trigger) {
        case CASTER -> {
          statusExists = hero.hasStatus();
        }
        case TARGET -> {
          statusExists = target.hasStatus();
        }
        case ANY ->
            statusExists = hero.arena.getAllLivingEntities().stream().anyMatch(Hero::hasStatus);
        case ANY_ALLY -> statusExists = hero.getAllies().stream().anyMatch(Hero::hasStatus);
        case ANY_ENEMY -> statusExists = hero.getEnemies().stream().anyMatch(Hero::hasStatus);
      }
      return hasStatus == statusExists;
    }
    return true;
  }

  private boolean healthCheck(Hero hero, Hero target) {

    if (this.healthComparison != null) {
      if (trigger == ConditionReference.CASTER) {
        return resourceCheck(hero.getCurrentLifePercentage());
      }
      if (trigger == ConditionReference.TARGET) {
        return resourceCheck(target.getCurrentLifePercentage());
      }
    }
    if (this.manaComparison) {
      if (trigger == ConditionReference.CASTER) {
        return resourceCheck(hero.getCurrentManaPercentage());
      }
      if (trigger == ConditionReference.TARGET) {
        return resourceCheck(target.getCurrentManaPercentage());
      }
    }
    return true;
  }

  private boolean hasEffectCheck(Hero hero, Hero target) {
    if (this.effectName != null) {
      int amnt = 0;
      switch (trigger) {
        case CASTER -> {
          return hero.hasPermanentEffect(this.effectName);
        }
        case TARGET -> {
          return target.hasPermanentEffect(this.effectName);
        }
        case ANY -> amnt = hero.arena.amountEffects(this.effectName);
        case ANY_ALLY -> amnt = hero.team.amountEffects(this.effectName);
        case ANY_ENEMY -> amnt = hero.enemyTeam.amountEffects(this.effectName);
        case ARENA ->
            amnt =
                hero.arena.globalEffect != null
                        && hero.arena.globalEffect.name.equals(this.effectName)
                    ? 1
                    : 0;
      }
      return hasCheck ? amnt > 0 : amnt < 1;
    }
    return true;
  }

  private boolean resourceCheck(int resourcePercentage) {
    return lessThan ? resourcePercentage < percentage : resourcePercentage > percentage;
  }
}
