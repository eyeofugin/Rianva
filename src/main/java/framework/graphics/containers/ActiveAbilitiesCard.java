package framework.graphics.containers;

import framework.Engine;
import framework.Logger;
import framework.Property;
import framework.graphics.GUIElement;
import framework.graphics.elements.SkillInfo;
import framework.graphics.text.Color;
import framework.states.Arena;
import framework.states.Draft;
import game.entities.Hero;
import game.skills.Skill;

public class ActiveAbilitiesCard extends GUIElement {
    private enum Mode{
        ARENA,
        DRAFT;
    }
    public Engine engine;
    public Arena arena;
    public Draft draft;
    private Mode mode;
    private Hero activeHero;
    public int abilityPointer = 0;
    public int maxAbilityPointer = 0;

    GUIElement[] skillIcons;
    SkillInfo skillInfo;

    public ActiveAbilitiesCard(Engine e, Arena arena) {
        super(Property.ACTIVE_ABILITY_WIDTH, Property.ACTIVE_ABILITY_HEIGHT);
        this.x = Property.ACTIVE_ABILITY_X;
        this.y = Property.HUD_BOXES_Y;
        this.engine = e;
        this.arena = arena;
        this.mode = Mode.ARENA;
    }

    public ActiveAbilitiesCard(Engine e, Draft draft) {
        super(Property.ACTIVE_ABILITY_WIDTH, Property.ACTIVE_ABILITY_HEIGHT);
        this.x = Property.ACTIVE_ABILITY_X;
        this.y = Property.HUD_BOXES_Y;
        this.engine = e;
        this.draft = draft;
        this.mode = Mode.DRAFT;
    }

    @Override
    public void update(int frame) {
        if (this.active) {
            if (engine.keyB._upPressed) {
                if (abilityPointer != 0) {
                    abilityPointer--;
                    this.activateIcon();
                }
            }
            if (engine.keyB._downPressed) {
                if (abilityPointer != maxAbilityPointer) {
                    abilityPointer++;
                    this.activateIcon();
                }
            }
            if (engine.keyB._enterPressed && this.mode.equals(Mode.ARENA)) {
                act();
            }
            if (engine.keyB._backPressed) {
                this.active = false;
//                this.arena.switchToHeroChoice();
            }
        }
    }
    private void act() {
        if (this.arena.activeHero == this.activeHero) {
            Skill s = getSkill();
            if (s != null) {
                if (this.activeHero.canPerform(s, new int[0])) {
                    this.active = false;
                    this.arena.switchToTargetChoice(s);
                } else {
                    Logger.logLn("cannot perform");
                }
            }
        }
    }
    public void setActiveHero(Hero e) {
        this.activeHero = e;
        this.abilityPointer = 0;
        this.maxAbilityPointer = this.activeHero.getSkills().size() - 1;
        createSkillIcons();
        activateIcon();
    }
    private void createSkillIcons() {
        skillIcons = new GUIElement[this.activeHero.getSkills().size()];
        int y = 2;
        for (int i = 0; i < this.activeHero.getSkills().size(); i++) {
            GUIElement skillIcon = new GUIElement();
            skillIcon.setSize(16, 16);
            skillIcon.setPixels(new int[16*16]);
            skillIcon.setPosition(2, y);
            if (i < this.activeHero.getSkills().size()) {
                Skill s = this.activeHero.getSkills().get(i);
                if (s!=null && s.getIconPixels()!=null) {
                    skillIcon.setPixels(s.getIconPixels());
                }
            }
            this.skillIcons[i] = skillIcon;
            y+=18;
        }
    }
    private void activateIcon() {
        for (GUIElement skillIcon : this.skillIcons) {
            if (skillIcon != null) {
                skillIcon.removeBorder();
            }
        }
        this.skillIcons[this.abilityPointer].setBorder(Color.WHITE, 1);
        setSkillInfo();
    }
    private void setSkillInfo() {
        Skill s = getSkill();
        if (s != null) {
            this.skillInfo = new SkillInfo(s);
            this.skillInfo.setPosition(24,2);
        }
    }

    @Override
    public int[] render() {
        background(Color.VOID);
        if (this.activeHero != null) {
            for (GUIElement icon : this.skillIcons) {
                fillWithGraphicsSize(icon.getX(), icon.getY(), icon.getWidth(), icon.getHeight(), icon.render(), this.active && icon.isSimpleBorder());
            }
            if (skillInfo != null) {
                fillWithGraphicsSize(this.skillInfo.getX(), this.skillInfo.getY(), this.skillInfo.getWidth(), this.skillInfo.getHeight(),
                        this.skillInfo.render(), this.active && this.skillInfo.isSimpleBorder());
            }
            if (this.active) {
                addBorder(this.width, this.height, this.pixels, Color.WHITE);
            }
        }
        return this.pixels;
    }

    private Skill getSkill() {
        if (this.activeHero != null && this.activeHero.getSkills().size() > this.abilityPointer) {
            return this.activeHero.getSkills().get(this.abilityPointer);
        }
        return null;
    }
}
