package game.entities.heroes.paladin.stash;

import game.entities.Hero;
import game.skills.*;
import game.skills.logic.Resource;
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_QuickPrayer extends Skill {

    public S_QuickPrayer(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/paladin/icons/quickprayer.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.targetType = TargetType.SELF;
//        this.targetResources = List.of(new Resource(Stat.CURRENT_FAITH, Stat.FAITH, 5));
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "";//return "Gain +40"+Stat.FAITH.getIconString()+".";
    }
    @Override
    public String getName() {
        return "Quick Prayer";
    }
}
