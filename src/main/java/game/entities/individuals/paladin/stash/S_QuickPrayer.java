package game.entities.individuals.paladin.stash;

import game.entities.Hero;
import game.skills.*;

import java.util.List;

public class S_QuickPrayer extends Skill {

    public S_QuickPrayer(Hero hero) {
        super(hero);
        this.iconPath = "entities/paladin/icons/quickprayer.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.targetType = TargetType.SELF;
        this.possibleCastPositions = new int[]{0,1,2};
        this.targetResources = List.of(new Resource(Stat.CURRENT_FAITH, Stat.FAITH, 5));
        this.faithGain = 40;
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gain +40"+Stat.FAITH.getIconString()+".";
    }
    @Override
    public String getName() {
        return "Quick Prayer";
    }
}
