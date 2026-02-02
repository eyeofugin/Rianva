package game.entities.heroes.thewizard;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_Recharge extends Skill {

    public S_Recharge(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/thewizard/icons/recharge.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SELF;
        this.level = 2;
    }


    @Override
    public int getAIRating(Hero target) {
        if (this.hero.getResourcePercentage(Stat.CURRENT_MANA) < 25) {
            return 4;
        }
        if (this.hero.getResourcePercentage(Stat.CURRENT_MANA) < 50) {
            return 2;
        }
        return 0;
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gain +" + (int)(this.hero.getStat(Stat.MAGIC) * 0.4) + "(40%" + Stat.MAGIC.getIconString() + ")" + Stat.MANA.getIconString() + ".";
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int manaRegain = this.hero.getStat(Stat.MAGIC) * 4 / 10;
        this.hero.addResource(Stat.CURRENT_MANA, Stat.MANA, manaRegain, this.hero);
    }

    @Override
    public String getName() {
        return "Recharge";
    }
}
