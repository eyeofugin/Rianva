package game.entities.heroes.dualpistol;

import game.entities.Hero;
import game.skills.AiSkillTag;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Dazed;

import java.util.List;

public class S_Outmaneuver extends Skill {

    public S_Outmaneuver(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/dualpistol/icons/outmaneuver.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.aiTags = List.of(AiSkillTag.COMBO_ENABLED);
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{4,5};
        this.effects = List.of(new Dazed(1));
        this.cdMax = 5;
        this.level = 2;
        this.move = -1;
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
    public String getDescriptionFor(Hero hero) {
        return "Pull 1.";
    }

    @Override
    public String getName() {
        return "Outmaneuver";
    }
}
