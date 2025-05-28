package framework.graphics.containers;

import framework.Engine;
import framework.Property;
import framework.graphics.GUIElement;
import framework.graphics.elements.Bar;
import framework.graphics.elements.EffectField;
import framework.graphics.elements.ItemField;
import framework.graphics.elements.StatField;
import framework.graphics.elements.TextField;
import framework.graphics.text.Color;
import framework.graphics.text.TextAlignment;
import framework.graphics.text.TextEditor;
import framework.resources.SpriteLibrary;
import framework.states.Arena;
import framework.states.Draft;
import game.entities.Hero;
import game.skills.Stat;

import java.util.List;

public class ActiveCharCard extends GUIElement {

    private enum Mode{
        ARENA,
        DRAFT;
    }
    public Engine engine;
    public Arena arena;
    public Draft draft;
    private Mode mode;
    private Hero activeHero;
    private TextField name;
    private TextField health;
    private TextField resource;
    private StatField stats;
    private TextField shieldField;
    private EffectField effects;
    private ItemField items;

    private GUIElement[] modeIcons;

    private Bar healthBar;
    private Bar resourceBar;

    private static final int MODES_Y = 40;

    private int modePointer = 0;
    private final int maxPointer;
    private final List<String> modes;

    public ActiveCharCard(Engine e, Arena arena) {
        super(Property.ACTIVE_CHAR_OV_WIDTH, Property.ACTIVE_CHAR_OV_HEIGHT);
        this.x = Property.ACTIVE_CHAR_OV_X;
        this.y = Property.HUD_BOXES_Y;
        this.engine = e;
        this.arena = arena;
        this.mode = Mode.ARENA;
        this.maxPointer = 2;
        this.modes = List.of("stats","effects", "items");
        this.activateMode();
    }

    public ActiveCharCard(Engine e, Draft draft) {
        super(Property.ACTIVE_CHAR_OV_WIDTH, Property.ACTIVE_CHAR_OV_HEIGHT);
        this.x = Property.ACTIVE_CHAR_OV_X;
        this.y = Property.HUD_BOXES_Y;
        this.engine = e;
        this.draft = draft;
        this.mode = Mode.DRAFT;
        this.modes = List.of("stats","items");
        this.maxPointer = 1;
        this.activateMode();
    }

    @Override
    public void update(int frame) {
        if (this.active) {
            if (engine.keyB._upPressed) {
                if (modePointer != 0) {
                    modePointer--;
                    activateMode();
                }
            }

            if (engine.keyB._downPressed) {
                if (modePointer != maxPointer) {
                    modePointer++;
                    activateMode();
                }
            }
            updateChildren(frame);
        }
    }

    private void activateMode() {
        deactivateModes();
        buildIcons();
        switch (this.modes.get(modePointer)) {
            case "stats" -> activateStats();
            case "effects" -> activateEffects();
            case "items" -> activateItems();
        }
    }

    private void activateStats() {
        this.stats = new StatField(this.activeHero);
        this.stats.setPosition(16, MODES_Y);
        this.stats.setActive(true);
        this.children.add(this.stats);
    }
    private void activateEffects() {
        this.effects = new EffectField(this.activeHero, engine);
        this.effects.setPosition(16,MODES_Y);
        this.effects.setActive(true);
        this.children.add(this.effects);
    }

    private void activateItems() {
        this.items = new ItemField(this.activeHero, engine);
        this.items.setPosition(16,MODES_Y);
        this.items.setActive(true);
        this.children.add(this.items);
    }

    private void buildIcons() {
        this.modeIcons = new GUIElement[this.modes.size()];
        int y = MODES_Y;
        int modePointer = 0;
        if (modes.contains("stats")) {
            addIcon("stats", y, modePointer++);
            y+=16;
        }
        if (modes.contains("effects")) {
            addIcon("effects", y, modePointer++);
            y+=16;
        }
        if (modes.contains("items")) {
            addIcon("items", y, modePointer);
        }
    }

    private void addIcon(String spriteName, int yPosition, int mode) {
        GUIElement icon = new GUIElement();
        icon.setSize(16,16);
        icon.setPixels(new int[16*16]);
        icon.setPosition(0, yPosition);
        int[] pixels = SpriteLibrary.getSprite(spriteName);
        GUIElement.setBackGround(pixels, modePointer == mode ? Color.BLACK : Color.DARKGREY);
        icon.setPixels(pixels);
        modeIcons[mode] = icon;
    }

    private void deactivateModes() {
        this.children.remove(items);
        this.children.remove(effects);
        this.children.remove(stats);
    }

    public void setActiveHero(Hero e) {

        this.modePointer = 0;
        this.children.remove(name);
        this.children.remove(healthBar);
        this.children.remove(health);

        this.activeHero = e;
        name = new TextField();
        name.setSize(124, 11);
        name.setPosition(2, 2);
        name.setString(this.activeHero.getName());
        name.setTextSize(1);
        name.setFontColor(Color.WHITE);

        this.healthBar = new Bar();
        this.healthBar.setSize(45, 3);
        this.healthBar.setPosition(2, 14);
        this.healthBar.setHero(this.activeHero);
        this.healthBar.setResource(Stat.LIFE);
        this.healthBar.setColor(Color.MEDIUMGREEN);

        this.health = new TextField();
        this.health.setSize(45, 5);
        this.health.setPosition(2,18);
        this.health.setTextSize(1);
        this.health.setString(this.activeHero.getResourceString(Stat.LIFE));
        this.health.setFontColor(Color.WHITE);
        this.health.setSmall(true);

        if (this.activeHero.getSecondaryResource() != null) {
            this.resourceBar = new Bar();
            this.resourceBar.setSize(45, 3);
            this.resourceBar.setPosition(50, 18);
            this.resourceBar.setHero(this.activeHero);
            this.resourceBar.setResource(this.activeHero.getSecondaryResource());
            this.resourceBar.setColor(Hero.getResourceColor(this.activeHero.getSecondaryResource()));

            this.resource = new TextField();
            this.resource.setSize(45, 5);
            this.resource.setPosition(50, 22);
            this.resource.setTextSize(1);
            this.resource.setString(this.activeHero.getResourceString(this.activeHero.getSecondaryResource()));
            this.resource.setFontColor(Color.WHITE);
            this.resource.setAlignment(TextAlignment.RIGHT);
            this.resource.setSmall(true);

            this.children.add(resourceBar);
            this.children.add(resource);
        } else {
            this.children.remove(resourceBar);
            this.children.remove(resource);
        }

        if (this.activeHero.getStat(Stat.SHIELD) > 0) {
            shieldField = new TextField();
            shieldField.setSize(124, 11);
            shieldField.setPosition(2, 32);
            shieldField.setString(Stat.SHIELD.getIconString() + ": " + this.activeHero.getStat(Stat.SHIELD));
            shieldField.setTextSize(1);
            shieldField.setFontColor(Color.DARKYELLOW);
        }

        this.children.add(name);
        this.children.add(healthBar);
        this.children.add(health);

        this.activateMode();
    }

    @Override
    public int[] render() {
        background(Color.BLACK);
        if (this.activeHero != null) {
            for (GUIElement child: modeIcons) {
                int[] childP = child.render();
                fillWithGraphicsSize(child.getX(), child.getY(), child.getWidth(), child.getHeight(), childP, false);
            }
            renderChildren();
            if (this.active) {
                addBorder(this.width, this.height, this.pixels, Color.WHITE);
            }
        }
        return this.pixels;
    }
}
