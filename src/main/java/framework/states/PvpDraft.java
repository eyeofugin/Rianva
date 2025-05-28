package framework.states;

import framework.Engine;
import framework.graphics.GUIElement;
import framework.graphics.containers.HUD;
import framework.graphics.text.Color;
import game.entities.DraftBuilder;
import game.entities.Hero;
import game.entities.HeroTeam;

import java.util.ArrayList;
import java.util.List;

public class PvpDraft extends GUIElement {
    public final Engine engine;
    public final HUD hud;

    public int round;
    private HeroTeam[] teams = new HeroTeam[2];
    private int activeTeam;
    private int maxX = 5;
    private int maxY = 4;
    private int pointerX, pointerY = 0;
    private Hero[] choices;
    private boolean[] removed;
    private int[] numberOfChoices = new int[]{1,2,2,2,1};
    private int currentChoice = 0;
    private int choiceRound = 0;
    public boolean finished = false;

    public PvpDraft(Engine engine) {
        super(Engine.X, Engine.Y);
        this.engine = engine;
        this.hud = new HUD(engine);
        this.hud.setPvpDraft(this);
        this.engine.memory.pvpRound++;
        this.teams = this.engine.memory.teams;
        this.activeTeam = 1;

        this.initPvpDraft();
    }

    private void initPvpDraft() {
        List<Class<? extends Hero>> exclusionList = new ArrayList<>();
        for (HeroTeam team: this.teams) {
            for (Hero hero: team.heroes) {
                exclusionList.add(hero.getClass());
            }
        }
        List<Class<? extends Hero>> availableHeroes = DraftBuilder.getAllList();
        availableHeroes.removeIf(exclusionList::contains);
        choices = new Hero[this.maxX*this.maxY];
        removed = new boolean[this.maxX*this.maxY];
        int index = 0;
        for (int y = 0; y < this.maxY; y++) {
            for (int x = 0; x < this.maxX; x++) {
                if (index < availableHeroes.size() && availableHeroes.get(index) != null) {
                    try {
                        this.choices[x + y * this.maxX] = availableHeroes.get(index).getConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    index++;
                }
            }
        }
    }

    @Override
    public void update(int frame) {
        if (active) {
            updateKeys();
        }
        updateDraft(frame);
        this.hud.update(frame);
    }
    private void updateKeys() {
        if (engine.keyB._leftPressed) {
            if (this.lookUp(this.pointerX-1, this.pointerY)) {
                this.pointerX--;
            }
        }
        if (engine.keyB._rightPressed) {
            if (this.lookUp(this.pointerX+1, this.pointerY)) {
                this.pointerX++;
            }
        }
        if (engine.keyB._upPressed) {
            if (this.lookUp(this.pointerX, this.pointerY-1)) {
                this.pointerY--;
            }
        }
        if (engine.keyB._downPressed) {
            if (this.lookUp(this.pointerX, this.pointerY+1)) {
                this.pointerY++;
            }
        }
        if (engine.keyB._enterPressed) {
            this.choose();
        }
//        if (engine.keyB._backPressed) {
//            this.remove();
//        }
//        if (engine.keyB._contextPressed) {
//            this.activateHUD();
//        }
    }

    private void choose() {
        int index = this.pointerX + this.maxX * this.pointerY;
        if (this.removed[index] || this.choices[index] == null) {
            return;
        }
        this.currentChoice++;
        HeroTeam team = this.teams[this.activeTeam-1];
        List<Hero> heroes = new ArrayList<>();
        heroes.add(this.choices[index]);
        heroes.addAll(List.of(team.heroes));
        team.heroes = heroes.toArray(new Hero[0]);
        this.removed[index] = true;
        if (this.currentChoice >= this.numberOfChoices[this.choiceRound]) {
            this.activeTeam = this.activeTeam==1?2:1;
            this.currentChoice = 0;
            this.choiceRound++;
            if (choiceRound >= this.numberOfChoices.length) {
                finishDraft();
            }
        }
    }
    private void finishDraft() {
        this.finished = true;
        this.engine.memory.teams = this.teams;
    }
    private boolean lookUp(int x, int y) {
        if (x < 0 || x >= this.maxX || y < 0 || y >= this.maxY) {
            return false;
        }
        int index = x + this.maxX * y;
        return this.choices.length > index && this.choices[index] != null;
    }
    private void updateDraft(int frame) {
        for(Hero hero: this.choices) {
            if (hero != null) {
                hero.update(frame);
            }
        }
    }
    @Override
    public int[] render() {
        background(Color.BLACK);
        renderDraft();
        renderTeams();
//        renderHUD();
//        renderChildren();
        return this.pixels;
    }
    private void renderDraft() {
        int x = 160;
        int y = 10;
        for (int i = 0; i < choices.length; i++) {
            Hero hero = this.choices[i];
            if (hero != null) {
                boolean bordered = (this.pointerX + this.maxX * this.pointerY) == i;
                boolean removed = this.removed[i];
                fillWithGraphicsSize(x, y, Hero.draftDimensionX, Hero.draftDimensionY, hero.draftRender(), bordered || removed, bordered? Color.WHITE : removed ? Color.RED : Color.VOID);
                x += Hero.draftDimensionX;
            }
            if ((i+1)%this.maxX==0) {
                y += Hero.draftDimensionY;
                x = 160;
            }
        }
    }
    private void renderTeams() {
        for (HeroTeam team : this.teams) {
            int x = team.teamNumber == 2 ? 566 : 10;
            int y = 50;
            for (Hero hero: team.heroes) {
                fillWithGraphicsSize(x, y, hero.draftDimensionX, hero.draftDimensionY, hero.draftRender(), false);
                y += hero.draftDimensionY+5;
            }
        }
    }
    private void renderHUD() {
        fillWithGraphicsSize(0,0,640,360,hud.render(),null);
    }

}
