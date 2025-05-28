package game.entities.individuals.eldritchguy;

import game.entities.Hero;
import game.skills.*;
import game.skills.changeeffects.effects.StatEffect;
import game.skills.changeeffects.statusinflictions.Dazed;

import java.util.List;

public class S_HorrificGlare extends Skill {

    public S_HorrificGlare(Hero hero) {
        super(hero);
        this.iconPath = "entities/eldritchguy/icons/horrificglare.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{3,4};
        this.effects = List.of(new Dazed(1), new StatEffect(3, Stat.ENDURANCE, -15));
        this.manaCost = 3;
    }

    @Override
    public String getName() {
        return "Horrific Glare";
    }
}
