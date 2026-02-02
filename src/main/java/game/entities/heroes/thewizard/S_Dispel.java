package game.entities.heroes.thewizard;

import game.entities.Hero;
import game.skills.*;
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_Dispel extends Skill {

    public S_Dispel(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/thewizard/icons/dispel.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;
    }

    @Override
    public int getAIRating(Hero target) {
        if (target.getSecondaryResource() == null) {
            return -2;
        } else if (target.getSecondaryResource() == Stat.MANA) {
            return 5;
        } else {
            return 3;
        }
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "";//return "Target loses 25% of either "+Stat.FAITH.getIconString()+","+Stat.MANA.getIconString()+" or "+Stat.HALO.getIconString()+".";
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
//        if (target.getSecondaryResource().equals(Stat.MANA)) {
//            int resourceLoss = target.getStat(Stat.MANA)/4;
//            target.addResource(Stat.CURRENT_MANA, Stat.MANA, -1*resourceLoss, this.hero);
//        } else if (target.getSecondaryResource().equals(Stat.FAITH)) {
//            int resourceLoss = target.getStat(Stat.FAITH)/4;
//            target.addResource(Stat.CURRENT_FAITH, Stat.FAITH, -1*resourceLoss, this.hero);
//        } else if (target.getSecondaryResource().equals(Stat.HALO)) {
//            target.addResource(Stat.CURRENT_HALO, Stat.HALO, -1, this.hero);
//        }
    }

    @Override
    public String getName() {
        return "Dispel";
    }
}
