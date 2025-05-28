package game.entities.individuals.paladin;

import game.entities.Animator;
import game.entities.Hero;
import game.entities.Role;
import game.skills.Skill;
import game.skills.Stat;

import java.util.List;

public class H_Paladin extends Hero {

    public H_Paladin() {
        super("Paladin");
        this.initBasePath("paladin");
        this.secondaryResource = Stat.FAITH;
        initAnimator();
        initSkills();
        this.initStats();
        setLevel(1);
        this.role = Role.TANK;
        this.effectiveRange = 2;
    }

    @Override
    protected void initAnimator() {
        this.anim = new Animator();
        anim.width = 64;
        anim.height = 64;

        anim.setupAnimation(this.basePath + "/sprites/idle_w.png", "idle", new int[]{40,80});
        anim.setupAnimation(this.basePath + "/sprites/damaged_w.png", "damaged", new int[]{3,6,9,12});
        anim.setupAnimation(this.basePath + "/sprites/action_w.png", "action_w", new int[]{15, 60, 100});

        anim.setDefaultAnim("idle");
        anim.currentAnim = anim.getDefaultAnim();
        anim.onLoop = true;
    }

    @Override
    protected void initSkills() {
        this.skills.addAll(List.of(new S_ShatteringSwing(this), new S_FierceGlow(this)));
        this.learnableSkillList.addAll(List.of(new S_LightPillar(this), new S_HoliestShield(this)));
//        randomizeSkills();
    }
}
