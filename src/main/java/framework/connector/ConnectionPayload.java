package framework.connector;

import framework.states.Arena;
import game.entities.Hero;
import game.objects.Equipment;
import game.skills.Skill;
import game.effects.Effect;
import game.skills.logic.Stat;
import game.skills.logic.TargetMode;

public class ConnectionPayload {
  public int dmg;
  public int heal;
  public int shield;
  public int energy;
  public int value;
  public int oldValue;
  public int[] targetPositions;
  public boolean regen;
  public boolean simulate;
  public boolean failure;
  public boolean newEffect;
  public TargetMode targetMode = TargetMode.NORMAL;
  public Effect effect;
  public Effect globalEffect;
  public Effect oldGlobalEffect;
  public Hero caster;
  public Hero target;
  public Skill skill;
  public Arena arena;
  public Equipment equipment;
  public EquipmentChangeMode mode;
  public Stat stat;

  public enum EquipmentChangeMode {
    EQUIP,
    UNEQUIP,
    ACTIVATE,
    DEACTIVATE;
  }

  public ConnectionPayload setDmg(int dmg) {
    this.dmg = dmg;
    return this;
  }

  public ConnectionPayload setHeal(int heal) {
    this.heal = heal;
    return this;
  }

  public ConnectionPayload setEnergy(int energy) {
    this.energy = energy;
    return this;
  }

  public ConnectionPayload setShield(int shield) {
    this.shield = shield;
    return this;
  }

  public ConnectionPayload setValue(int value) {
    this.value = value;
    return this;
  }

  public ConnectionPayload setOldValue(int oldValue) {
    this.oldValue = oldValue;
    return this;
  }

  public ConnectionPayload setTargetPositions(int[] targetPositions) {
    this.targetPositions = targetPositions;
    return this;
  }

  public ConnectionPayload setRegen(boolean regen) {
    this.regen = regen;
    return this;
  }

  public ConnectionPayload setSimulate(boolean simulate) {
    this.simulate = simulate;
    return this;
  }

  public ConnectionPayload setFailure(boolean failure) {
    this.failure = failure;
    return this;
  }

  public ConnectionPayload setTargetMode(TargetMode targetMode) {
    this.targetMode = targetMode;
    return this;
  }

  public ConnectionPayload setEffect(Effect effect) {
    this.effect = effect;
    return this;
  }

  public ConnectionPayload setGlobalEffect(Effect globalEffect) {
    this.globalEffect = globalEffect;
    return this;
  }

  public ConnectionPayload setOldGlobalEffect(Effect oldGlobalEffect) {
    this.oldGlobalEffect = oldGlobalEffect;
    return this;
  }

  public ConnectionPayload setCaster(Hero caster) {
    this.caster = caster;
    return this;
  }

  public ConnectionPayload setTarget(Hero target) {
    this.target = target;
    return this;
  }

  public ConnectionPayload setSkill(Skill skill) {
    this.skill = skill;
    return this;
  }

  public ConnectionPayload setArena(Arena arena) {
    this.arena = arena;
    return this;
  }

  public ConnectionPayload setEquipment(Equipment equipment) {
    this.equipment = equipment;
    return this;
  }

  public ConnectionPayload setMode(EquipmentChangeMode mode) {
    this.mode = mode;
    return this;
  }

  public ConnectionPayload setStat(Stat stat) {
    this.stat = stat;
    return this;
  }

  public ConnectionPayload setNewEffect(boolean newEffect) {
    this.newEffect = newEffect;
    return this;
  }
}
