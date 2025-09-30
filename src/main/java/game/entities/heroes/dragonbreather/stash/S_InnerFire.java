package game.entities.heroes.dragonbreather.stash;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.globals.Heat;

import java.util.List;

public class S_InnerFire extends Skill {

    public S_InnerFire(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/dragonbreather/icons/innerfire.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SELF;
        this.possibleCastPositions = new int[]{0,1,2};
        this.manaCost = 8;
    }



    @Override
    public int getAIRating(Hero target) {
        int rating = 10;
        rating -= this.hero.getMissingLifePercentage() / 20;
        return rating;
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "+6"+Stat.POWER.getIconString()+", +6"+Stat.MAGIC.getIconString()+" during Heat. +4"+Stat.ENDURANCE.getIconString()+", +4"+Stat.STAMINA.getIconString()+ " otherwise.";
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        if (this.hero.arena.globalEffect instanceof Heat) {
            this.hero.addToStat(Stat.POWER, 6);
            this.hero.addToStat(Stat.MAGIC, 6);
        } else {
            this.hero.addToStat(Stat.ENDURANCE, 4);
            this.hero.addToStat(Stat.STAMINA, 4);
        }
    }

    @Override
    public String getName() {
        return "Inner Fire";
    }
}
