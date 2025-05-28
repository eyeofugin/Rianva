package game.entities.individuals.darkmage;

import game.entities.Animator;
import game.entities.Hero;
import game.entities.Role;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.genericskills.S_Skip;

import java.util.List;

public class H_DarkMage extends Hero {

    public H_DarkMage() {
        super("Dark Mage");
        this.initBasePath("darkmage");
        this.secondaryResource = Stat.MANA;
        initAnimator();
        initSkills();
        this.initStats();
        setLevel(1);
        this.role = Role.SUPPORT;
        this.effectiveRange = 4;
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
        this.skills.addAll(List.of(new S_GiftOfTheLeech(this), new S_DarkSecrets(this)));
        this.learnableSkillList.addAll(List.of(new S_UnfairAdvantage(this), new S_DarkSchemes(this)));
//        randomizeSkills();
    }
}
