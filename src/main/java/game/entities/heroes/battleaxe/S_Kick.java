package game.entities.heroes.battleaxe;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;

import java.util.List;

public class S_Kick extends Skill {

    public S_Kick(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/battleaxe/icons/kick.png";
        addSubscriptions();
        setToInitial();

    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.25));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{2};
        this.possibleTargetPositions = new int[]{3,4};
        this.cdMax = 4;
        this.damageMode = DamageMode.PHYSICAL;
        this.priority = -1;
        this.level = 2;
        this.move = 1;
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
    public String getDescriptionFor(Hero hero) {
        return "Push 1.";
    }

    @Override
    public String getName() {
        return "Kick";
    }
}
