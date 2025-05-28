package game.entities.individuals.battleaxe;

import game.entities.Animator;
import game.entities.Hero;
import game.entities.Role;

import java.util.List;

public class H_BattleAxe extends Hero {

    public final static String NAME = "Battle Axe";
    public H_BattleAxe() {
        super(NAME);
        this.initBasePath("battleaxe");
        this.initAnimator();
        this.initSkills();
        this.initStats();
        this.setLevel(1);
        this.role = Role.FIGHTER;
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
        this.skills.addAll(List.of(new S_BloodCleave(this), new S_Bloodlust(this)));
        this.learnableSkillList.addAll(List.of(new S_Kick(this), new S_WideSwing(this)));
//        randomizeSkills();
    }
}
