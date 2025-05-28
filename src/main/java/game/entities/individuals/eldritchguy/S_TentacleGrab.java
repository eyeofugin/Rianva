package game.entities.individuals.eldritchguy;

import framework.graphics.text.Color;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.statusinflictions.Dazed;

import java.util.List;

public class S_TentacleGrab extends Skill {

    public S_TentacleGrab(Hero hero) {
        super(hero);
        this.iconPath = "entities/eldritchguy/icons/tentaclegrab.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{2};
        this.possibleTargetPositions = new int[]{4,5};
        this.damageMode = DamageMode.PHYSICAL;
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.3));
        this.manaCost = 6;
        this.level = 2;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.arena.moveTo(target, target.getPosition() + (target.isTeam2()?-1:1));
    }

    @Override
    public int getDmg(Hero target) {
        return target.getStat(Stat.LIFE) * 10 / 100;
    }
    @Override
    public int getAIRating(Hero target) {
        int rating = 0;
        if (target.getPosition() == target.team.getFirstPosition()) {
            return --rating;
        }
        if (target.getCurrentLifePercentage() < 50) {
            rating += 2;
        }
        if (this.hero.arena.getAtPosition(target.team.getFirstPosition()).getCurrentLifePercentage() < 50) {
            rating -= 2;
        }
        return rating;
    }

    @Override
    public String getDmgOrHealString() {
        return DamageMode.PHYSICAL.getColor().getCodeString()+"DMG"+ Color.WHITE.getCodeString()+": 10% of target's "+ Stat.LIFE.getReference() + ".";
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Pull 1.";
    }

    @Override
    public String getName() {
        return "Tentacle Grab";
    }
}
