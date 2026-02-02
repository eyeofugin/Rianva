package game.entities.heroes.duelist.stash;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_Mobilize extends Skill {

    public S_Mobilize(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/duelist/icons/mobilize.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE_OTHER;
    }

    @Override
    public int getAIRating(Hero target) {
        return getRollRating(target);
    }


    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int targetPosition = target.getPosition();
        this.hero.arena.moveTo(this.hero, targetPosition);
        this.hero.addToStat(Stat.ATTACK, this.hero.getStat(Stat.ATTACK)/10);
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Move. Get +10%" + Stat.ATTACK.getIconString() + ".";
    }

    @Override
    public String getName() {
        return "Mobilize";
    }
}
