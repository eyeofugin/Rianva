package game.entities.heroes.darkmage;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;
import game.skills.changeeffects.effects.other.StatEffect;

import java.util.List;

public class S_DarkSecrets extends Skill {

    public S_DarkSecrets(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/darkmage/icons/darksecrets.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;
        this.effects = List.of(StatEffect.deadly.getNew());
    }


    @Override
    public int getAIRating(Hero target) {
        int highestATKStat = Math.max(target.getStat(Stat.MAGIC), target.getStat(Stat.ATTACK));
        return highestATKStat / 4;
    }

    @Override
    public String getName() {
        return "Dark Secrets";
    }
}
