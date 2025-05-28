package game.entities.individuals.divinemage;

import game.entities.Hero;
import game.skills.*;

import java.util.List;
import java.util.Optional;

public class S_Praise extends Skill {

    public S_Praise(Hero hero) {
        super(hero);
        this.iconPath = "entities/divinemage/icons/praise.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.aiTags = List.of(AiSkillTag.FAITH_GAIN);
        this.targetType = TargetType.ALL_TARGETS;
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{0,1,2};
        this.heal = 2;
        this.faithGain = 10;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        List<Hero> over50 = this.hero.team.getHeroesAsList().stream().filter(e->e.getCurrentLifePercentage() > 50).toList();
        this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 5*over50.size(), this.hero);
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gain 5"+Stat.FAITH.getIconString()+". Gain 5"+Stat.FAITH.getIconString()+" for each ally with more than 50%" + Stat.LIFE.getReference()+ ".";
    }
    @Override
    public String getName() {
        return "Praise";
    }
}
