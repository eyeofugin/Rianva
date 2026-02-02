package framework.graphics.containers;

import framework.Engine;
import framework.Property;
import framework.graphics.GUIElement;
import framework.graphics.elements.ItemInfo;
import framework.graphics.elements.SkillElement;
import framework.graphics.elements.SkillInfo;
import framework.graphics.text.Color;
import framework.resources.SpriteLibrary;
import game.entities.DraftBuilder;
import game.entities.Hero;
import game.entities.HeroTeam;
import game.objects.Equipment;
import game.skills.Skill;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeamBuildItemsCard extends GUIElement {
    public static final int _WIDTH = 300;
    public static final int _HEIGHT = 300;

    private final Engine engine;

    private Hero hero;
    private int chosenAmount = 0;
    private int maxX = 5;
    private int maxY = 5;
    private int pointerX = 0;
    private int pointerY = 0;
    private Equipment[] choices;
    private List<Equipment> chosen = new ArrayList<>();

    public TeamBuildItemsCard(Engine e) {
        super(_WIDTH, _HEIGHT);
        this.engine = e;
        this.x = 300;
        this.y = 10;

        initItemDraft();
    }

    private void initItemDraft() {
        List<Class<? extends Equipment>> availableEquipments = DraftBuilder.getAllItems();
        choices = new Equipment[availableEquipments.size()];
        for (int i = 0; i < availableEquipments.size(); i++) {
            try {
                choices[i] = availableEquipments.get(i).getConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    @Override
    public void update(int frame) {
        if (active) {
            updateKeys();
        }
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
    }
    private boolean lookUp(int x, int y) {
        if (x < 0 || x >= this.maxX || y < 0 || y >= this.maxY) {
            return false;
        }
        int index = x + this.maxX * y;
        return this.choices.length > index && this.choices[index] != null;
    }

    private void choose() {
        int index = this.pointerX + this.maxX * this.pointerY;
        Equipment equipment = this.choices[index];
        if (this.chosen.contains(equipment)) {
            equipment.unEquipFromHero();
            this.hero.getEquipments().remove(equipment);
            this.chosen.remove(equipment);
            this.chosenAmount--;
        } else if (chosenAmount < this.engine.memory.pvpRound) {
            equipment.equipToHero(this.hero);
            this.chosen.add(equipment);
            this.chosenAmount++;
        }
    }
    public void finish() {
        this.hero.setEquipments(this.chosen);
        this.hero.getEquipments().forEach(e-> e.equipToHero(hero));
    }

    public int[] render(boolean full) {
        background(Color.VOID);
        renderDraft();
        if (full) {
            renderItemInfo();
        }
//        renderTeams();
//        renderHUD();
//        renderChildren();
        return this.pixels;
    }

    private void renderDraft() {
        int x = 170;
        int y = 10;
        for (int i = 0; i < choices.length; i++) {
            Equipment equipment = this.choices[i];
            if (equipment != null) {
                Color borderColor = getBorderColorFor(equipment, i);
                fillWithGraphicsSize(x, y, Property.EQUIPMENT_ICON_SIZE, Property.EQUIPMENT_ICON_SIZE, equipment.getIcon(), true, borderColor);
                x += 19;
            }
            if ((i+1)%this.maxX==0) {
                y += 19;
                x = 170;
            }
        }
    }
    private Color getBorderColorFor(Equipment equipment, int i) {
        if (this.chosen.contains(equipment)) {
            return Color.GREEN;
        } else if (!this.active) {
            return Color.VOID;
        } else if ((this.pointerX + this.maxX * this.pointerY) == i) {
            return Color.WHITE;
        }
        return Color.VOID;
    }
    private void renderItemInfo() {
        ItemInfo info = new ItemInfo(this.choices[this.pointerX + this.maxX * this.pointerY]);
        info.setSize(200, 200);
        fillWithGraphicsSize(0, 200, info.getWidth(), info.getHeight(), info.render(), false);
    }
}
