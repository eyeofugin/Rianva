package game.entities.heroes.thehealer.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.Skill;
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_HolyWords extends Skill {

    public S_HolyWords(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/thehealer/icons/holywords.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;
        this.healMultipliers = List.of(new Multiplier(Stat.LIFE, 0.2),
                new Multiplier(Stat.MANA, 0.3));
        this.shield = 5;
    }
    @Override
    public int getAIRating(Hero target) {
        return target.getMissingLifePercentage() / 25;
    }

    @Override
    public String getName() {
        return "Holy Words";
    }
}
