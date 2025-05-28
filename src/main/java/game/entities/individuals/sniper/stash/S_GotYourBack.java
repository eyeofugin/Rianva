package game.entities.individuals.sniper.stash;

import game.entities.Hero;
import game.skills.*;
import game.skills.changeeffects.effects.Combo;

import java.util.List;

public class S_GotYourBack extends Skill {

    public S_GotYourBack(Hero hero) {
        super(hero);
        this.iconPath = "entities/sniper/icons/gotyourback.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE_OTHER;
        this.possibleCastPositions = new int[]{0,1};
        this.possibleTargetPositions = new int[]{0,1,2};
        this.cdMax = 1;
        this.effects = List.of(new Combo());
    }

    @Override
    public int getAIRating(Hero target) {
        int rating = 2;
        for (Skill skill : target.getSkills()) {
            if (skill.aiTags.contains(AiSkillTag.COMBO_ENABLED)) {
                rating += 2;
            }
        }
        return rating;
    }



    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.addToStat(Stat.SPEED, 3);
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gives +3" + Stat.SPEED.getIconString();
    }


    @Override
    public String getName() {
        return "Got Your Back";
    }
}
