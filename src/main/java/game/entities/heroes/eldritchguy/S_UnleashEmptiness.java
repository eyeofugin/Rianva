package game.entities.heroes.eldritchguy;

import game.entities.Hero;
import game.skills.*;

import java.util.List;

public class S_UnleashEmptiness extends Skill {

    public S_UnleashEmptiness(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/eldritchguy/icons/unleashemptiness.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.ULT);
        this.targetType = TargetType.SELF;
        this.possibleCastPositions = new int[]{0,1,2};
        this.manaCost = 15;
        this.lifeCost = 15;
        this.level = 5;
    }



    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.removeNegativeEffects();
        this.hero.addToStat(Stat.MAGIC, this.hero.getStat(Stat.ENDURANCE) / 2);
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Remove all debuffs. Permanently +X"+Stat.MAGIC.getIconString()+" where X is 50% of your"+Stat.ENDURANCE.getIconString()+".";
    }


    @Override
    public String getName() {
        return "Unleash Emptiness";
    }
}
