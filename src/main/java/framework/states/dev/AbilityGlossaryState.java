package framework.states.dev;

import framework.Engine;
import framework.Logger;
import framework.graphics.elements.SkillInfo;
import framework.graphics.text.Color;
import framework.states.Memory;
import framework.states.State;
import game.entities.Hero;
import game.libraries.SkillLibrary;
import game.skills.Skill;
import utils.CollectionUtils;

import java.util.List;

public class AbilityGlossaryState extends State {

    private List<Skill> skills;
    private Hero hero;
    private int index = 0;
    public AbilityGlossaryState(Memory memory) {
        super(memory);
        SkillLibrary.init();
        this.hero = new Hero();
        this.skills = SkillLibrary.fullSkillList();
        this.skills.forEach(s->s.hero = this.hero);
    }

    @Override
    public void update(int frame) {
        if (active) {
            updateKeys();
        }
    }
    private void updateKeys() {
        if (Engine.KeyBoard._leftPressed) {
            index = index - 1 < 0 ? skills.size()-1 : index - 1;
        }
        if (Engine.KeyBoard._rightPressed) {
            index = index + 1 == skills.size() ? 0 : index + 1;
        }
    }

    @Override
    public int[] render() {
        background(Color.BLACK);
        renderSkillInfo();
        return this.pixels;
    }

    private void renderSkillInfo() {
        if (CollectionUtils.isNotEmpty(skills)) {
            Skill skill = this.skills.get(index);
            SkillInfo info = new SkillInfo(skill);
            Logger.logLn(skill.name);
            fillWithGraphicsSize(200, 150, info.getWidth(), info.getHeight(), info.render(), false);
        }
    }
}
