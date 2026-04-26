package framework.states.dev;

import framework.Engine;
import framework.Logger;
import framework.graphics.GUIElement;
import framework.graphics.elements.SkillElement;
import framework.graphics.elements.SkillInfo;
import framework.graphics.elements.StatField;
import framework.graphics.text.Color;
import framework.states.*;
import game.entities.*;
import game.entities.classes.HeroClassDTO;
import game.entities.races.HeroRaceDTO;
import game.entities.roles.HeroRoleDTO;
import game.libraries.*;
import game.objects.EquipmentDTO;
import game.skills.Skill;
import game.skills.logic.Stat;
import utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DevState extends State {

  List<Hero> heroList = new ArrayList<>();
  List<HeroClassDTO> heroClasses = new ArrayList<>();
  List<HeroRaceDTO> heroRaces = new ArrayList<>();
  List<HeroRoleDTO> heroRoles = new ArrayList<>();
  List<EquipmentDTO> equipments = new ArrayList<>();
  HeroClassDTO classDTO;
  HeroRaceDTO raceDTO;
  HeroRoleDTO roleDTO;
  EquipmentDTO equipmentDTO;

  private String activeMode = "HERO";
  public static String HERO = "HERO";
  public static String SKILL = "SKILL";
  public static String AI_EVAL_TEST = "AI_EVAL_TEST";

  Hero hero;
  Skill activeSkill;
  HeroDTO heroDTO;
  List<Skill> skillList;
  private StatField stats;

  private int s = 0;
  private int h = 1;

  public DevState(Memory memory) {
    super(memory);
    this.id = StateManager.DEV;
    HeroLibrary.init();
    SkillLibrary.init();
    EffectLibrary.init();
    EquipmentLibrary.init();
    HeroBackgroundLibrary.init();
    HeroBuilder.init();
    this.hero = HeroLibrary.getHero(h+ "");
    this.stats = new StatField(hero);
  }

  @Override
  public void update(int frame) {
    if (active) {
      this.hero.animate(frame);
      updateKeys();
//      updateKeysHeroDevSelections();
    }
  }

  private void updateKeys() {
    if (Engine.KeyBoard._leftPressed) {
      if (this.activeMode.equals(HERO)) {
        this.h = Math.max(1, h - 1);
      } else {
        this.s = Math.max(0, s - 1);
      }
      this.hero = HeroLibrary.getHero(h + "");
      this.stats = new StatField(hero);
    }
    if (Engine.KeyBoard._rightPressed) {
      if (this.activeMode.equals(HERO)) {
        this.s = Math.min(8, h + 1);
      } else {
        this.s = Math.min(5, s + 1);
      }
      this.activeSkill = this.hero.getSkills().get(s);
    }
    if (Engine.KeyBoard._enterPressed) {
      this.activeMode = SKILL;
    }
    if (Engine.KeyBoard._backPressed) {
      this.activeMode = HERO;
    }
  }
  @Override
  public int[] render() {
    background(Color.BLACK);
    renderHero();
    renderStats();
    renderSkillGrid();
    renderSkillInfo();
    return this.pixels;
  }

  private void renderHero() {
    if (this.hero == null) {
      return;
    }
    fillWithGraphicsSize(
        10, 10, this.hero.getWidth(), this.hero.getHeight(), this.hero.render(Hero.BUILDER), false);
  }

  private void renderStats() {
    if (this.hero == null) {
      return;
    }
    fillWithGraphicsSize(
        200, 10, this.stats.getWidth(), this.stats.getHeight(), this.stats.render(), false);
  }

  private void renderSkillGrid() {
    if (this.hero == null) {
      return;
    }
    for (int x = 0; x < 6; x++) {
      Skill skill = this.hero.getSkills().get(x);
      GUIElement icon = new GUIElement();
      icon.setSize(16,16);
      icon.setPixels(skill.getIconPixels());
      icon.setPosition(250+x*20,250);
      fillWithGraphicsSize(icon.getX(), icon.getY(), icon.getWidth(), icon.getHeight(), icon.render(), this.s == x);
    }
  }
  private void renderSkillInfo() {
    if (this.activeSkill == null) { return; }
    SkillInfo info = new SkillInfo(this.activeSkill);
    fillWithGraphicsSize(200, 150, info.getWidth(), info.getHeight(), info.render(), false);
  }
}
