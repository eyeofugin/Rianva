package framework.graphics.containers;

import framework.Engine;
import framework.graphics.GUIElement;
import framework.graphics.elements.SkillElement;
import framework.graphics.text.Color;
import framework.states.TeamBuilder;
import game.entities.Hero;
import game.entities.HeroTeam;
import game.skills.Skill;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeamBuildHeroesCard extends GUIElement {
    public static final int _WIDTH = 270;
    public static final int _HEIGHT = 300;

    private final Engine engine;

    private HeroTeam team;
    private final TeamBuilder teamBuilder;

    private int activeHeroPointer = 0;
    private boolean selected = false;
    private int selectedPos = -1;

    public TeamBuildHeroesCard(Engine e, HeroTeam team, TeamBuilder teamBuilder) {
        super(_WIDTH, _HEIGHT);
        this.x = 2;
        this.y = 100;
        this.engine = e;
        this.team = team;
        this.teamBuilder = teamBuilder;
        setSelectedHero();
    }

    @Override
    public void update(int frame) {
        if (this.active) {
            if (engine.keyB._leftPressed) {
                this.activeHeroPointer = this.activeHeroPointer > 0 ? this.activeHeroPointer-1: 3;
                setSelectedHero();
            }
            if (engine.keyB._rightPressed) {
                this.activeHeroPointer = this.activeHeroPointer < 3 ? this.activeHeroPointer+1: 0;
                setSelectedHero();
            }
            if (engine.keyB._enterPressed) {
                choice();
            }
        }
    }

    private void choice() {
        if (this.selected) {
            if (this.activeHeroPointer == this.selectedPos) {
                this.selectedPos = -1;
                this.selected = false;
            } else {
                swap();
            }
        } else {
            this.selectedPos = this.activeHeroPointer;
            this.selected = true;
        }
    }

    private void setSelectedHero() {
        this.teamBuilder.setActiveHero(this.team.heroes[this.activeHeroPointer]);
    }

    private void swap() {
        Hero from = this.team.heroes[this.selectedPos];
        Hero to = this.team.heroes[this.activeHeroPointer];
        this.team.heroes[this.activeHeroPointer] = from;
        this.team.heroes[this.selectedPos] = to;
        this.selectedPos = -1;
        this.selected = false;
    }

    @Override
    public int[] render() {
        background(Color.BLACK);
        renderTeams();
        return this.pixels;
    }

    private void renderTeams() {
        int x = 0;
        int y = 0;
        for (int i = 0; i < this.team.heroes.length; i++) {
            Hero hero = this.team.heroes[i];
            Color borderColor = i == this.selectedPos ? Color.GREEN : i == this.activeHeroPointer ? Color.WHITE : Color.VOID;
            fillWithGraphicsSize(x, y, hero.getWidth(), hero.getHeight(), hero.render(Hero.BUILDER), true, borderColor, Color.RED);
            x += hero.getWidth();
        }
    }
}
