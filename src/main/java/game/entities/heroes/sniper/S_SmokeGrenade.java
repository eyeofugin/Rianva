package game.entities.heroes.sniper;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.StatEffect;

import java.util.List;

public class S_SmokeGrenade extends Skill {

    public S_SmokeGrenade(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/sniper/icons/smokegrenade.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.ALL_TARGETS;
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{0,1,2};
        this.effects = List.of(new StatEffect(2, Stat.EVASION, 25));
        this.cdMax = 4;
        this.priority = 2;
    }

    @Override
    public int getAIRating(Hero target) {
        return 1 + target.getMissingLifePercentage() / 50;
    }

    @Override
    public String getName() {
        return "Smoke Grenade";
    }
}
