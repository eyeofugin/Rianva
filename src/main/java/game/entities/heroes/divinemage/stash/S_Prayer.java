package game.entities.heroes.divinemage.stash;

import game.entities.Hero;
import game.skills.*;

import java.util.List;
import java.util.Optional;

public class S_Prayer extends Skill {

    public S_Prayer(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/divinemage/icons/prayer.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.aiTags = List.of(AiSkillTag.FAITH_GAIN);
        this.targetType = TargetType.SELF;
        this.possibleCastPositions = new int[]{0,1,2};
        this.faithGain = 15;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        Optional<Hero> heroUnder50 = this.hero.team.getHeroesAsList().stream().filter(e->e.getCurrentLifePercentage() < 50).findAny();
        if (heroUnder50.isPresent()) {
            this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 15, this.hero);
        }
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gain 15"+Stat.FAITH.getIconString()+". Gain an extra 15"+Stat.FAITH.getIconString()+", if an ally has less than 50%" + Stat.LIFE.getReference()+ ".";
    }
    @Override
    public String getName() {
        return "Prayer";
    }
}
