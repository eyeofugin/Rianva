package framework.states;

import framework.Engine;
import framework.graphics.elements.SkillElement;
import framework.graphics.elements.SkillInfo;
import framework.graphics.elements.StatField;
import framework.graphics.text.Color;
import game.entities.DraftBuilder;
import game.entities.Hero;
import game.libraries.HeroLibrary;
import game.entities.HeroTeam;
import game.libraries.EffectLibrary;
import game.skills.Skill;
import game.libraries.SkillLibrary;
import game.skills.logic.Stat;

import java.util.ArrayList;
import java.util.List;

public class DevState extends State {

  List<Hero> heroList = new ArrayList<>();
  private String activeMode = "";
  public static String LIST_MODE = "LIST_MODE";
  public static String AI_EVAL_TEST = "AI_EVAL_TEST";

  Hero hero;
  List<Skill> skillList;
  private StatField stats;

  private int x = 0;
  private int y = 0;

  public DevState(Memory memory) {
    super(memory);
    this.id = StateManager.DEV;
    HeroLibrary.init();
    SkillLibrary.init();
    EffectLibrary.init();
    setUpHeroList();
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

  private void setUpHeroList() {
    this.heroList.addAll(HeroLibrary.getAll());
    this.setHero(x);
    this.activeMode = LIST_MODE;
  }

  @Override
  public void update(int frame) {
    if (active) {
      updateKeys();
      //            updateKeysHeroDevSelections();
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
      this.x = this.x == 0 ? this.heroList.size() - 1 : this.x - 1;
      this.setHero(x);
      this.y = 0;
    }
    if (Engine.KeyBoard._rightPressed) {
      this.x = this.x == this.heroList.size() - 1 ? 0 : this.x + 1;
      this.setHero(x);
      this.y = 0;
    }
    if (Engine.KeyBoard._upPressed) {
      this.y = this.y == 0 ? 3 : this.y - 1;
    }
    if (Engine.KeyBoard._downPressed) {
      this.y = this.y == 3 ? 0 : this.y + 1;
    }
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
          Stat.MIND, Stat.BODY, Stat.DEXTERITY, Stat.ACCURACY, Stat.DODGE, Stat.CRIT_CHANCE
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

  private void renderSkillInfo() {
    SkillInfo info = new SkillInfo(this.skillList.get(this.y));
    fillWithGraphicsSize(200, 200, info.getWidth(), info.getHeight(), info.render(), false);
  }
}
