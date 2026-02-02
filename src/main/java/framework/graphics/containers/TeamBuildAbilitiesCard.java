package framework.graphics.containers;

import framework.Engine;
import framework.graphics.GUIElement;
import framework.graphics.elements.SkillElement;
import framework.graphics.elements.SkillInfo;
import framework.graphics.text.Color;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.logic.SkillTag;

import java.util.ArrayList;
import java.util.List;

public class TeamBuildAbilitiesCard extends GUIElement {
    public static final int _WIDTH = 300;
    public static final int _HEIGHT = 300;

    private final Engine engine;

    private Hero hero;
    private int abilityPointer = 0;
    private final List<Skill> availableSkills = new ArrayList<>();
    private int primaryChosen = 0;
    private int tacticalChosen = 0;
    private int ultimateChosen = 0;

    public TeamBuildAbilitiesCard(Engine e) {
        super(_WIDTH, _HEIGHT);
        this.engine = e;
        this.x = 300;
        this.y = 10;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
        this.availableSkills.addAll(this.hero.getSkills());
        this.availableSkills.addAll(this.hero.getLearnableSkillList());
    }
    public void finish() {
        this.hero.setSkills(this.availableSkills);
    }
    @Override
    public void update(int frame) {
        if (this.active) {
            if (engine.keyB._upPressed) {
                if (this.abilityPointer > 0) {
                    this.abilityPointer--;
                }
            }
            if (engine.keyB._downPressed) {
                if (this.abilityPointer < this.availableSkills.size()-1) {
                    this.abilityPointer++;
                }
            }
//            if (engine.keyB._enterPressed) {
//                chooseAbility();
//            }
//            if (engine.keyB._backPressed) {
//                removeAbility();
//            }
        }
    }

//    private void chooseAbility() {
//        Skill skill = this.availableSkills.get(this.abilityPointer);
//        if (this.chosenSkills.contains(skill)) {
//            removeAbility();
//            return;
//        }
//        if (skill.tags.contains(SkillTag.PRIMARY)) {
//            if (this.primaryChosen == 0) {
//                this.primaryChosen++;
//                this.chosenSkills.add(skill);
//            }
//        } else if (skill.tags.contains(SkillTag.TACTICAL)) {
//            if (this.tacticalChosen < 2) {
//                this.tacticalChosen++;
//                this.chosenSkills.add(skill);
//            }
//        } else if (skill.tags.contains(SkillTag.ULT)) {
//            if (this.ultimateChosen == 0) {
//                this.ultimateChosen++;
//                this.chosenSkills.add(skill);
//            }
//        }
//    }

//    private void removeAbility() {
//        Skill skill = this.availableSkills.get(this.abilityPointer);
//        if (this.chosenSkills.contains(skill)) {
//            this.chosenSkills.remove(skill);
//            if (skill.tags.contains(SkillTag.PRIMARY)) {
//                this.primaryChosen--;
//            } else if (skill.tags.contains(SkillTag.TACTICAL)) {
//                this.tacticalChosen--;
//            } else if (skill.tags.contains(SkillTag.ULT)) {
//                this.ultimateChosen--;
//            }
//        }
//    }

    public int[] render(boolean full) {
        background(Color.VOID);
        renderAvailableSkills();
        if (full) {
            renderSkillInfo();
        }
        return this.pixels;
    }
    private void renderAvailableSkills() {
        int x = 0;
        int y = 0;
        for (int i = 0; i < this.availableSkills.size(); i++) {
            Skill skill = this.availableSkills.get(i);
            Color borderColor = getBorderColorFor(skill, i);
            SkillElement element = new SkillElement(skill);
            fillWithGraphicsSize(x, y, SkillElement._WIDTH, SkillElement._HEIGHT, element.render(), true, borderColor);
            y += SkillElement._HEIGHT + 2;
        }
    }

    private void renderSkillInfo() {
        SkillInfo info = new SkillInfo(this.availableSkills.get(this.abilityPointer));
        fillWithGraphicsSize(0, 200, info.getWidth(), info.getHeight(), info.render(), false);
    }

    private Color getBorderColorFor(Skill skill, int i) {
//        if (this.chosenSkills.contains(skill)) {
//            return Color.GREEN;
//        } else
//        if (isFullFor(skill)) {
//            return Color.RED;
//        } else
//        if (!this.active) {
//            return Color.VOID;
//        } else
        if (i == this.abilityPointer) {
            return Color.WHITE;
        }

        return Color.VOID;
    }

    private boolean isFullFor(Skill skill) {
        if (skill.tags.contains(SkillTag.PRIMARY)) {
            return this.primaryChosen > 0;
        } else if (skill.tags.contains(SkillTag.TACTICAL)) {
            return this.tacticalChosen > 1;
        } else if (skill.tags.contains(SkillTag.ULT)) {
            return this.ultimateChosen > 0;
        }
        return false;
    }
}
