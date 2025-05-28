package game.entities.individuals.thehealer.stash;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.TargetType;
import game.skills.changeeffects.statusinflictions.Blinded;

import java.util.List;

public class S_BlindingLight extends Skill {

    public S_BlindingLight(Hero hero) {
        super(hero);
        this.iconPath = "entities/thehealer/icons/blindinglight.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{3,4};
        this.manaCost = 5;
        this.effects = List.of(new Blinded(2));
    }

    @Override
    public int getAIRating(Hero target) {
        return 4;
    }

    @Override
    public String getName() {
        return "Blinding Light";
    }
}
