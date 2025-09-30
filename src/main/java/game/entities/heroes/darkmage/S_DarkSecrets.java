package game.entities.heroes.darkmage;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.StatEffect;

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
        this.effects = List.of(new StatEffect(3, Stat.LETHALITY, 100));
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{0,1,2};
        this.manaCost = 4;
    }


    @Override
    public int getAIRating(Hero target) {
        int highestATKStat = Math.max(target.getStat(Stat.MAGIC), target.getStat(Stat.POWER));
        return highestATKStat / 4;
    }

    @Override
    public String getName() {
        return "Dark Secrets";
    }
}
