package game.entities.heroes.angelguy;

import game.entities.Animator;
import game.entities.Hero;
import game.entities.Role;
import game.entities.heroes.angelguy.stash.S_Reengage;
import game.skills.Stat;

import java.util.List;

public class H_AngelGuy extends Hero {

    public static final String NAME = "Holly Spirit";

    public H_AngelGuy() {
        super(NAME);
        this.initBasePath("angelguy");
        this.secondaryResource = Stat.FAITH;
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
        anim.setupAnimation(this.basePath + "/sprites/action_w.png", "action_w", new int[]{15, 30, 45});

        anim.setDefaultAnim("idle");
        anim.currentAnim = anim.getDefaultAnim();
        anim.onLoop = true;
    }

    @Override
    protected void initSkills() {
        this.skills.addAll(List.of(new S_DeepThrust(this), new S_AngelicWings(this)));
        this.learnableSkillList.addAll(List.of(new S_LightJavelin(this), new S_LightSpikes(this)));
    }
}
