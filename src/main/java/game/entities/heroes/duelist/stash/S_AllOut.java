package game.entities.heroes.duelist.stash;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_AllOut extends Skill {

    public S_AllOut(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/duelist/icons/allout.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SELF;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.addToStat(Stat.STAMINA, -3);
        this.hero.addToStat(Stat.ATTACK, 5);
        this.hero.addToStat(Stat.SPEED, this.hero.getStat(Stat.SPEED)/5);
    }

    @Override
    public int getAIRating(Hero target) {
        int rating = 5;
        rating -= this.hero.getMissingLifePercentage() / 20;
        return rating;
    }




    @Override
    public String getDescriptionFor(Hero hero) {
        return "Get +5"+Stat.ATTACK.getIconString()+", +20%"+Stat.SPEED.getIconString()+" and -3"+Stat.STAMINA.getIconString() +".";
    }

    @Override
    public String getName() {
        return "All Out";
    }
}
