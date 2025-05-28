package game.entities.individuals.battleaxe;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;

import java.util.List;

public class S_Kick extends Skill {

    public S_Kick(Hero hero) {
        super(hero);
        this.iconPath = "entities/battleaxe/icons/kick.png";
        addSubscriptions();
        setToInitial();

    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.1));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{2};
        this.possibleTargetPositions = new int[]{3,4};
        this.dmg = 3;
        this.cdMax = 4;
        this.damageMode = DamageMode.PHYSICAL;
        this.priority = -1;
        this.level = 2;
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
        target.changeStatTo(Stat.SHIELD, 0);
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Push 1, target loses all their "+Stat.SHIELD.getIconString()+".";
    }

    @Override
    public String getName() {
        return "Kick";
    }
}
