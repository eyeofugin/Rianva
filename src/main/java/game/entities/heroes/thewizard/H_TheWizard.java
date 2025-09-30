package game.entities.heroes.thewizard;

import game.entities.Animator;
import game.entities.Hero;
import game.entities.Role;
import game.skills.Stat;

import java.util.List;

public class H_TheWizard extends Hero {
    public H_TheWizard() {
        super("The Wizard");
        this.initBasePath("thewizard");
        this.secondaryResource = Stat.MANA;
        initAnimator();
        initSkills();
        initStats();
        setLevel(1);
        this.role = Role.DPS;
        effectiveRange = 3;
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
        this.skills.addAll(List.of(new S_LightningBolt(this),new S_Dispel(this)));
        this.learnableSkillList.addAll(List.of(new S_Recharge(this), new S_LightningStorm(this)));
    }
}
