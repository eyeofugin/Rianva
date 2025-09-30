package game.entities.heroes.divinemage.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;

import java.util.List;

public class S_HolyWords extends Skill {

    public S_HolyWords(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/divinemage/icons/holywords.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE_OTHER;
        this.heal = 2;
        this.healMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.5));
        this.faithRequirement = 15;
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{0,1,2};
    }

    @Override
    public String getName() {
        return "Holy Words";
    }
}
