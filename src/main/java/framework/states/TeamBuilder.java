package framework.states;

import framework.Engine;
import framework.graphics.GUIElement;
import framework.graphics.containers.HUD;
import framework.graphics.containers.TeamBuildAbilitiesCard;
import framework.graphics.containers.TeamBuildHeroesCard;
import framework.graphics.containers.TeamBuildItemsCard;
import framework.graphics.elements.StatField;
import framework.graphics.text.Color;
import game.entities.Hero;
import game.entities.HeroTeam;
import game.skills.logic.Stat;

import java.util.HashMap;
import java.util.Map;

public class TeamBuilder extends GUIElement {


    private final Engine engine;
    public final HUD hud;

    private int activeTeam;
    private HeroTeam[] teams;
    public int cardPointer = 0;
    private Hero activeHero;
    public boolean finished = false;

    TeamBuildHeroesCard heroesCard;
    Map<Hero, TeamBuildAbilitiesCard> abilitiesCardMap = new HashMap<>();
    Map<Hero, TeamBuildItemsCard> itemsCardMap = new HashMap<>();
    private StatField stats;

    public TeamBuilder(Engine e) {
        super(Engine.X, Engine.Y);
        this.id = StateManager.TEAM_BUILDER;
        this.engine = e;
        this.hud = new HUD(e);
        this.teams = this.engine.memory.teams;
        this.activeTeam = 1;
        this.active = true;
        this.heroesCard = new TeamBuildHeroesCard(this.engine, this.teams[0], this);
        this.children.add(heroesCard);

        initCards(0);
    }

    private void initCards(int teamIndex) {
        this.abilitiesCardMap = new HashMap<>();
        this.itemsCardMap = new HashMap<>();
        HeroTeam team = this.teams[teamIndex];
        for (Hero hero : team.heroes) {
            TeamBuildAbilitiesCard card = new TeamBuildAbilitiesCard(this.engine);
            card.setHero(hero);
            card.setActive(false);
            this.abilitiesCardMap.put(hero, card);
            TeamBuildItemsCard itemsCard = new TeamBuildItemsCard(this.engine);
            itemsCard.setHero(hero);
            card.setActive(false);
            this.itemsCardMap.put(hero, itemsCard);
        }
    }
    @Override
    public void update(int frame) {
        if (this.active) {
            if (engine.keyB._shoulderLeftPressed) {
                if (cardPointer != 0) {
                    cardPointer--;
                }
            }
            if (engine.keyB._shoulderRightPressed) {
                if (cardPointer != 3) {
                    cardPointer++;
                }
            }
            if (engine.keyB._contextPressed) {
                end();
            }
            updateCardSelection();
            updateChildren(frame);
        }
    }
    @Override
    protected void updateChildren(int frame) {
        super.updateChildren(frame);
        for (TeamBuildAbilitiesCard card : this.abilitiesCardMap.values()) {
            card.update(frame);
        }
        for (TeamBuildItemsCard card : this.itemsCardMap.values()) {
            card.update(frame);
        }
    }
    private void updateCardSelection() {
        deactivateAllChildren();
        this.heroesCard.setActive(this.cardPointer == 0);
        if (cardPointer == 1) {
            TeamBuildAbilitiesCard card = this.abilitiesCardMap.get(this.activeHero);
            if (card != null) {
                card.setActive(true);
            }
        }
        if (cardPointer == 2) {
            TeamBuildItemsCard card = this.itemsCardMap.get(this.activeHero);
            if (card != null) {
                this.itemsCardMap.get(this.activeHero).setActive(true);
            }
        }
    }
    @Override
    protected void deactivateAllChildren() {
        super.deactivateAllChildren();
        for (TeamBuildAbilitiesCard card : this.abilitiesCardMap.values()) {
            card.setActive(false);
        }
        for (TeamBuildItemsCard card : this.itemsCardMap.values()) {
            card.setActive(false);
        }
    }
    private void end() {
        finalizeHeroes();
        if (this.activeTeam == 1) {
            this.activeTeam = 2;
            this.children.remove(this.heroesCard);
            this.heroesCard = new TeamBuildHeroesCard(this.engine, this.teams[1], this);
            this.children.add(this.heroesCard);
            initCards(1);
        } else {
            this.engine.memory.teams = this.teams;
            this.finished = true;
        }
    }
    private void finalizeHeroes() {
        for (TeamBuildAbilitiesCard abilitiesCard : this.abilitiesCardMap.values()) {
            abilitiesCard.finish();
        }
        for (TeamBuildItemsCard itemCard : this.itemsCardMap.values()) {
            itemCard.finish();
        }
    }
    public void setActiveHero(Hero hero) {
        this.activeHero = hero;
        Stat[] lArray = new Stat[]{Stat.LIFE, Stat.LIFE_REGAIN, Stat.MANA, Stat.MANA_REGAIN, Stat.SHIELD};
        Stat[] rArray = new Stat[]{Stat.MAGIC, Stat.ATTACK, Stat.STAMINA, Stat.SPEED, Stat.ACCURACY, Stat.EVASION, Stat.CRIT_CHANCE, Stat.LETHALITY};
        this.stats = new StatField(this.activeHero, lArray, rArray);
    }
    public void activate() {
        this.active = true;
    }
    public void deactivate() {
        this.active = false;
    }

    @Override
    public int[] render() {
        background(Color.BLACK);
        renderDeco();
        renderTeam();
        renderAbilityCard();
        renderItemsCard();
        renderStats();
        return this.pixels;
    }
    private void renderStats() {
        if (this.activeHero == null) {
            return;
        }
        fillWithGraphicsSize(10, 250, this.stats.getWidth(), this.stats.getHeight(), this.stats.render(), false);
    }
    private void renderDeco() {
        fillWithGraphicsSize(10, 10, 200, 20, getTextLine("Team " + this.activeTeam, 200, 20, Color.WHITE), false);
    }

    private void renderTeam() {
        fillWithGraphicsSize(10, 20, this.heroesCard.getWidth(), this.heroesCard.getHeight(), this.heroesCard.render(), this.cardPointer == 0);
    }

    private void renderAbilityCard() {
        TeamBuildAbilitiesCard card = this.abilitiesCardMap.get(this.activeHero);
        if (card != null) {
            fillWithGraphicsSize(card.getX(), card.getY(), card.getWidth(), card.getHeight(), card.render(this.cardPointer == 1), this.cardPointer == 1);
        }
    }
    private void renderItemsCard() {
        TeamBuildItemsCard card = this.itemsCardMap.get(this.activeHero);
        if (card != null) {
            fillWithGraphicsSize(card.getX(), card.getY(), card.getWidth(), card.getHeight(), card.render(this.cardPointer == 2), this.cardPointer == 2);
        }
    }

}
