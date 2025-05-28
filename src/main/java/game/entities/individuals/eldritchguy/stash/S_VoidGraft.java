package game.entities.individuals.eldritchguy.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;

import java.util.List;

public class S_VoidGraft extends Skill {

    public S_VoidGraft(Hero hero) {
        super(hero);
        this.iconPath = "entities/eldritchguy/icons/voidgraft.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.healMultipliers = List.of(new Multiplier(Stat.ENDURANCE, 0.8));
        this.targetType = TargetType.SELF;
        this.possibleCastPositions = new int[]{0,1,2};
        this.manaCost = 2;
    }


    @Override
    public String getName() {
        return "Void Graft";
    }
}
