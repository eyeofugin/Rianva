package game.entities.individuals.rifle.stash;

import game.entities.Hero;
import game.skills.*;

import java.util.List;

public class S_Mobilize extends Skill {

    public S_Mobilize(Hero hero) {
        super(hero);
        this.iconPath = "entities/rifle/icons/mobilize.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE_OTHER;
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{0,1,2};
        this.cdMax = 2;
    }


    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int currentPosition = this.hero.getPosition();
        int targetPosition = target.getPosition();
        this.hero.arena.moveTo(this.hero, targetPosition);

        boolean forward = this.hero.isTeam2() && currentPosition > targetPosition
                || !this.hero.isTeam2() && currentPosition < targetPosition;
        if (forward) {
            this.hero.addToStat(Stat.SPEED, 1);
        } else {
            this.hero.addToStat(Stat.EVASION, 5);
        }
    }

    @Override
    public int getAIRating(Hero target) {
        return getRollRating(target);
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Move to position. If you moved backwards: Gain +5"+Stat.EVASION.getIconString()+". If you moved forward: +1"+Stat.SPEED.getIconString()+".";
    }


    @Override
    public String getName() {
        return "Mobilize";
    }
}
