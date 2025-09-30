package game.entities.heroes.angelguy.stash;

import game.entities.Hero;
import game.skills.*;

import java.util.List;

public class S_AngelicBlessing extends Skill {

    public S_AngelicBlessing(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/angelguy/icons/piercinglight.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE_OTHER;
        this.possibleTargetPositions = new int[]{0,1,2};
        this.possibleCastPositions = new int[]{0,1,2};
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int amount = this.hero.getStat(Stat.CURRENT_FAITH) / 2;
        this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, (-1) * amount, this.hero);
        target.addResource(Stat.CURRENT_FAITH, Stat.FAITH, amount, this.hero);
    }

    @Override
    public int getFaithRequirement() {
        return this.hero.getStat(Stat.CURRENT_FAITH) / 2;
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Spend " + Stat.FAITH.getIconString() + " to give target ally the same amount of " + Stat.MANA.getIconString() + "/" + Stat.FAITH.getIconString() + ".";
    }

    @Override
    public String getName() {
        return "Angelic Blessing";
    }
}
