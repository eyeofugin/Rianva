package game.entities.individuals.paladin.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;

import java.util.List;

public class S_LightBlast extends Skill {

    public S_LightBlast(Hero hero) {
        super(hero);
        this.iconPath = "entities/paladin/icons/lightblast.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.4));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{2};
        this.possibleTargetPositions = new int[]{3};
        this.dmg = 2;
        this.faithRequirement = 40;
        this.damageMode = DamageMode.MAGICAL;
    }


    @Override
    public int getAIRating(Hero target) {
        int rating = 0;
        if (target.getCurrentLifePercentage() > 75) {
            rating++;
        }
        Hero targetBehind = this.hero.arena.getAtPosition(target.getPosition()-1);
        if (targetBehind != null) {
            rating += targetBehind.getMissingLifePercentage() / 25;
        }
        if (target.getPosition() == target.getLastEffectivePosition()) {
            rating +=5;
        }
        return rating;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.arena.moveTo(target, target.getPosition() + (target.isTeam2()?1:-1));
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Push 1 back";
    }


    @Override
    public String getName() {
        return "Light Blast";
    }
}
