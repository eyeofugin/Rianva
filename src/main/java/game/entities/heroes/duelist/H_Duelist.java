package game.entities.heroes.duelist;

import game.entities.Animator;
import game.entities.Hero;
import game.entities.Role;

import java.util.List;

public class H_Duelist extends Hero {

    public H_Duelist() {
        super("Snr Stab");
        this.initBasePath("duelist");
        initAnimator();
        initSkills();
        this.initStats();
        setLevel(1);
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
        anim.setupAnimation(this.basePath + "/sprites/action_w.png", "action_w", new int[]{15, 30, 45});

        anim.setDefaultAnim("idle");
        anim.currentAnim = anim.getDefaultAnim();
        anim.onLoop = true;
    }

    @Override
    protected void initSkills() {
        this.skills.addAll(List.of(new S_Slash(this), new S_SwirlingBlades(this)));
        this.learnableSkillList.addAll(List.of(new S_GiveMeYourWorst(this), new S_DuelistDance(this)));
    }
}
