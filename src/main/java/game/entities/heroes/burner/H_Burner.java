package game.entities.heroes.burner;

import game.entities.Animator;
import game.entities.Hero;
import game.entities.Role;
import game.skills.logic.Stat;

import java.util.List;

public class H_Burner extends Hero {

    public final static String NAME = "Bunsen";
    public H_Burner() {
        super(NAME);
        this.initBasePath("burner");
        this.secondaryResource = Stat.MANA;
        this.initAnimator();
        this.initSkills();
        this.initStats();
        this.setLevel(1);
        this.role = Role.DPS;
        this.effectiveRange = 3;
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
        this.skills.addAll(List.of(new S_Burn(this), new S_FlareUp(this)));
        this.learnableSkillList.addAll(List.of(new S_Heat(this), new S_SpreadingFlames(this)));
//        randomizeSkills();
    }
}
