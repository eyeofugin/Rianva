package game.entities.individuals.darkmage;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;

import java.util.List;

public class S_UnfairAdvantage extends Skill {

    public S_UnfairAdvantage(Hero hero) {
        super(hero);
        this.iconPath = "entities/darkmage/icons/unfairadvantage.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE_OTHER;
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{0,1,2};
        this.manaCost = 3;
        this.level = 2;
    }


    @Override
    public int getAIRating(Hero target) {
        return 2;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int magicAdd = target.getStat(Stat.MAGIC) / 15;
        int speedAdd = target.getStat(Stat.SPEED) / 15;
        target.addToStat(Stat.MAGIC, magicAdd);
        target.addToStat(Stat.SPEED, speedAdd);
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Give +15%"+Stat.MAGIC.getIconString()+" and +15%"+Stat.SPEED.getIconString()+".";
    }

    @Override
    public String getName() {
        return "Unfair Advantage";
    }
}
