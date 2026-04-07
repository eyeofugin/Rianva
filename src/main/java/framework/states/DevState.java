package framework.states;

import framework.Engine;
import framework.graphics.GUIElement;
import framework.graphics.elements.SkillElement;
import framework.graphics.elements.SkillInfo;
import framework.graphics.elements.StatField;
import framework.graphics.text.Color;
import game.entities.DraftBuilder;
import game.entities.Hero;
import game.entities.HeroDTO;
import game.entities.classes.HeroClass;
import game.entities.classes.HeroClassDTO;
import game.entities.races.HeroRace;
import game.entities.races.HeroRaceDTO;
import game.entities.roles.HeroRoleDTO;
import game.libraries.*;
import game.entities.HeroTeam;
import game.objects.EquipmentDTO;
import game.skills.Skill;
import game.skills.SkillDTO;
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

  private String activeMode = "";
  public static String LIST_MODE = "LIST_MODE";
  public static String BUILDER_MODE = "BUILDER_MODE";
  public static String AI_EVAL_TEST = "AI_EVAL_TEST";

  Hero hero;
  HeroDTO heroDTO;
  List<Skill> skillList;
  private StatField stats;

  private int sx, sy = 0;
  private int hr, hc, hro, he = 0;

  public DevState(Memory memory) {
    super(memory);
    this.id = StateManager.DEV;
    HeroLibrary.init();
    SkillLibrary.init();
    EffectLibrary.init();
    EquipmentLibrary.init();
    HeroBackgroundLibrary.init();
    this.activeMode = BUILDER_MODE;
    testStatDistribution();
    setUpBackgroundList();
    updateHeroBuilder();

//    setUpHeroList();
    //        Hero hero1 = HeroLibrary.getHero("Burner");
    //        hero1.getSkills().forEach(Skill::addSubscriptions);
    //        Connector.fireTopic(Connector.BASE_DMG_CHANGES, new BaseDmgChangesPayload());

    //        FileWalker.getHeroes("test.json");
    //        setUpHeroList();
    //        S_WindChant wc = new S_WindChant();
    //        System.out.println(wc.description);
  }

  private void setUpAiEvalTest() {
    for (int i = 0; i < 5; i++) {
      this.memory.mode = GameMode.PVP;

      Arena arena = new Arena(memory, false);
      arena.setTeams(
          DraftBuilder.getRandomTeam(1, 1), new HeroTeam(-1, DraftBuilder.getTestTeam(i), 2));
      //            arena.aiController.chooseActions();
    }
  }

  private void testStatDistribution() {
//    testClassStatDistribution();
    testRaceStatDistribution();
  }

  private void testClassStatDistribution() {
    List<HeroClassDTO> heroClasses = HeroBackgroundLibrary.getAllClasses();
    for (HeroClassDTO classDTO : heroClasses) {
      System.out.println(classDTO.name);
      evaluateStats(classDTO.statsIncrease);
      System.out.println("\n\n");
    }
  }
  private void testRaceStatDistribution() {
    List<HeroRaceDTO> heroRaces = HeroBackgroundLibrary.getAllRaces();
    for (HeroRaceDTO raceDTO : heroRaces) {
      System.out.println(raceDTO.name);
//      evaluateStats(classDTO.stats, "BST");
      evaluateStats(raceDTO.stats);
      System.out.println("\n\n");
    }
  }

  private void evaluateStats(Map<Stat, Integer> stats) {
    if (stats == null) {return;}
    int total = 0;

    int bodyVal = stats.get(Stat.BODY) == null? 0 : stats.get(Stat.BODY);
    int mindVal = stats.get(Stat.MIND) == null? 0 : stats.get(Stat.MIND);
    int dextVal = stats.get(Stat.DEXTERITY) == null? 0 : stats.get(Stat.DEXTERITY);

    int vitalityVal = stats.get(Stat.VITALITY) == null? 0 : stats.get(Stat.VITALITY);
    int resTotal = 0;

    for (Map.Entry<Stat, Integer> statEntry: stats.entrySet()) {
      if (Stat.resistances.contains(statEntry.getKey())) {
        resTotal += statEntry.getValue();
      }
    }

    int energyVal = stats.get(Stat.ENERGY) == null? 0 : stats.get(Stat.ENERGY);
    int staminaVal = stats.get(Stat.FOCUS) == null? 0 : stats.get(Stat.FOCUS);
    total = bodyVal + mindVal + dextVal + vitalityVal + resTotal + energyVal + staminaVal;

    System.out.println("Stat\tBODY\tMIND\tDEXT\tVITA\tRES\t\tNRG\t\tFOCU");
    System.out.print(
        "Val \t"
            + bodyVal
            + "\t\t"
            + mindVal
            + "\t\t"
            + dextVal
            + "\t\t"
            + vitalityVal
            + "\t\t"
            + resTotal
            + "\t\t"
            + energyVal
            + "\t\t"
            + staminaVal + "\t\tTotal: "+ total);

    if (Math.abs(total-490) > 20) {
      System.out.println("  Diff:" + Math.abs(total-490));
    }
    else {
      System.out.println();
    }
//
//    for (Map.Entry<Stat, Integer> statEntry: stats.entrySet()) {
//      int multiplier = 0;
//      if (List.of(Stat.VITALITY, Stat.ENERGY).contains(statEntry.getKey())) {
//        multiplier = 1;
//      }
//      if (List.of(Stat.LIFE_REGAIN, Stat.ENERGY_REGAIN).contains(statEntry.getKey())) {
//        multiplier = 10;
//      }
//      if (List.of(Stat.BODY, Stat.MIND, Stat.DEXTERITY).contains(statEntry.getKey())) {
//        multiplier = 4;
//      }
//      if (List.of(Stat.CRIT_CHANCE, Stat.ARMOR).contains(statEntry.getKey())) {
//        multiplier = 15;
//      }
//      if (Stat.resistances.contains(statEntry.getKey())) {
//        resTotal += statEntry.getValue();
//      } else {
//        total += multiplier * statEntry.getValue();
//      }
//    }
//    System.out.println(statMode + ": " + total +"/" + resTotal);
  }
  private void setUpHeroList() {
//    this.heroList.addAll(HeroLibrary.getAll());
//    this.setHero(x);
//    this.activeMode = LIST_MODE;
  }

  private void setUpBackgroundList() {
    this.heroClasses.addAll(HeroBackgroundLibrary.getAllClasses());
    this.heroRaces.addAll(HeroBackgroundLibrary.getAllRaces());
    this.heroRoles.addAll(HeroBackgroundLibrary.getAllRoles());
    this.equipments.addAll(EquipmentLibrary.getAllEquipments());
  }

  @Override
  public void update(int frame) {
    if (active) {
      updateKeys();
      updateKeysHeroDevSelections();
    }
  }

  private void updateKeys() {
    if (Engine.KeyBoard.upPressed) {
      System.out.println("up");
    }
    if (Engine.KeyBoard.downPressed) {
      System.out.println("down");
    }
  }

  private void updateKeysHeroDevSelections() {
    if (Engine.KeyBoard._leftPressed) {
      if (raceDTO == null) {
        this.hr = this.hr == 0 ? this.heroRaces.size() - 1 : this.hr - 1;
        updateHeroBuilder();
      } else if (classDTO == null) {
        this.hc = this.hc == 0 ? this.heroClasses.size() - 1 : this.hc - 1;
        updateHeroBuilder();
      }  else if (roleDTO == null) {
        this.hro = this.hro == 0 ? this.heroRoles.size() - 1 : this.hro - 1;
        updateHeroBuilder();
      } else if (equipmentDTO == null) {
        this.he = this.he == 0 ? this.equipments.size() - 1 : this.he - 1;
        updateHeroBuilder();
      } else {
        this.x = Math.max(0, x-1);
      }
    }
    if (Engine.KeyBoard._rightPressed) {
      if (raceDTO == null) {
        this.hr = this.hr == this.heroRaces.size() - 1 ? 0 : this.hr + 1;
        updateHeroBuilder();
      } else if (classDTO == null) {
        this.hc = this.hc == this.heroClasses.size() - 1 ? 0 : this.hc + 1;
        updateHeroBuilder();
      } else if (equipmentDTO == null) {
        this.he = this.he == this.equipments.size() - 1 ? 0 : this.he + 1;
        updateHeroBuilder();
      } else {
        this.x = Math.min(5, x+1);
      }
    }
    if (Engine.KeyBoard._upPressed) {
      if (raceDTO != null && classDTO != null && equipmentDTO != null) {
        this.y = Math.max(0,y-1);
      }
    }
    if (Engine.KeyBoard._downPressed) {
      if (raceDTO != null && classDTO != null && equipmentDTO != null) {
        this.y = Math.min(2,y+1);
      }
    }
    if (Engine.KeyBoard._enterPressed) {
      if (raceDTO == null) {
        raceDTO = heroRaces.get(hr);
      } else if (classDTO == null) {
        classDTO = heroClasses.get(hc);
      } else if (roleDTO == null) {
        roleDTO = heroRoles.get(hro);
        buildHero();
      } else {
        equipmentDTO = equipments.get(he);
        this.initSkillList();
        this.sx = 0;
        this.sy = 0;
      }
    }
  }

  private void buildHero() {
    this.hero = new Hero(raceDTO, classDTO, roleDTO);
  }
  private void initSkillList() {
    List<String> skillNames = new ArrayList<>();
    skillNames.addAll(this.classDTO.learnableSkills);
    skillNames.addAll(this.raceDTO.learnableSkills);
    skillNames.addAll(this.roleDTO.learnableSkills);
    skillNames.addAll(this.equipmentDTO.learnableSkills);
    this.skillList = skillNames.stream().map(SkillLibrary::getSkillByName).toList();
    this.skillList.forEach(s->s.hero = this.hero);
  }

  private void updateHeroBuilder() {
    this.heroDTO = new HeroDTO();
    heroDTO.heroRace = this.heroRaces.get(hr);
    heroDTO.heroClass = this.heroClasses.get(hc);
    heroDTO.heroRole = this.heroRoles.get(hro);
    heroDTO.calcStatsForLevel(1);
    this.stats = new StatField(this.heroDTO);
  }
  private void setHero(int index) {
    this.hero = this.heroList.get(index);
    this.skillList = new ArrayList<>();

    this.skillList.addAll(hero.getSkills());
    //        this.skillList.addAll(hero.getLearnableSkillList());
    Stat[] lArray =
        new Stat[] {Stat.VITALITY, Stat.LIFE_REGAIN, Stat.ENERGY, Stat.ENERGY_REGAIN, Stat.SHIELD};
    Stat[] rArray =
        new Stat[] {
          Stat.MIND, Stat.BODY, Stat.DEXTERITY, Stat.LETHALITY, Stat.DODGE, Stat.CRIT_CHANCE
        };
    this.stats = new StatField(this.hero);
  }

  @Override
  public int[] render() {
    background(Color.BLACK);
    if (this.activeMode.equals(LIST_MODE)) {
      renderHero();
      renderAbilities();
      renderSkillInfo();
      renderStats();
    }
    if (this.activeMode.equals(BUILDER_MODE)) {
      renderBackgrounds();
      renderStats();
      renderSkillGrid();
      renderSkillInfo();

    }
    return this.pixels;
  }

  private void renderHero() {
    if (this.hero == null) {
      return;
    }
    fillWithGraphicsSize(
        10, 10, this.hero.getWidth(), this.hero.getHeight(), this.hero.render(Hero.BUILDER), false);
  }

  private void renderBackgrounds() {
    if (heroRaces.get(hr) != null) {
      fillWithGraphicsSize(
              10, 10, 100, 20, getTextLine(this.heroRaces.get(hr).name, 100, 20, Color.WHITE), false);
    }
    if (raceDTO != null && this.heroClasses.get(hc) != null) {
      fillWithGraphicsSize(
              10, 25, 100, 20, getTextLine(this.heroClasses.get(hc).name, 100, 20, Color.WHITE), false);
    }
    if (classDTO != null && this.heroRoles.get(hro) != null) {
      fillWithGraphicsSize(
              10, 40, 100, 20, getTextLine(this.heroRoles.get(hro).name, 100, 20, Color.WHITE), false);
    }
    if (roleDTO != null && this.equipments.get(he) != null) {
      fillWithGraphicsSize(
              10, 55, 100, 20, getTextLine(this.equipments.get(he).name, 100, 20, Color.WHITE), false);

    }
  }

  private void renderStats() {
    if (this.hero == null && this.heroDTO == null) {
      return;
    }
    fillWithGraphicsSize(
        200, 10, this.stats.getWidth(), this.stats.getHeight(), this.stats.render(), false);
  }

  private void renderAbilities() {
    if (this.hero == null || this.skillList.isEmpty()) {
      return;
    }
    int y = 200;
    for (int i = 0; i < this.skillList.size(); i++) {
      Skill skill = this.skillList.get(i);
      SkillElement element = new SkillElement(skill);
      fillWithGraphicsSize(
          10,
          y,
          SkillElement._WIDTH,
          SkillElement._HEIGHT,
          element.render(),
          true,
          this.y == i ? Color.WHITE : Color.VOID);
      y += SkillElement._HEIGHT + 2;
    }
  }

  private void renderSkillGrid() {
    if (this.skillList == null || this.skillList.isEmpty()) {
      return;
    }
    for (int x = 0; x < 6; x++) {
      for (int y = 0; y < 3; y++) {
        int index = y * 6 + x;
        if (this.skillList.size() - 1 >= index) {
          Skill skill = this.skillList.get(index);
          GUIElement icon = new GUIElement();
          icon.setSize(16,16);
          icon.setPixels(skill.getIconPixels());
          icon.setPosition(250+x*20,250+y*20);
          fillWithGraphicsSize(icon.getX(), icon.getY(), icon.getWidth(), icon.getHeight(), icon.render(), this.x == x && this.y ==y);

        }
      }
    }
  }
  private void renderSkillInfo() {

    int index = y*6 + x;
    if (CollectionUtils.isNotEmpty(skillList)) {
      Skill skill = this.skillList.get(index);
      SkillInfo info = new SkillInfo(skill);
      fillWithGraphicsSize(200, 150, info.getWidth(), info.getHeight(), info.render(), false);
    }
  }
}
