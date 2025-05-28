package game.entities.individuals.sniper;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.TargetType;
import game.skills.changeeffects.effects.RegenBoost;
import game.skills.changeeffects.statusinflictions.Injured;

import java.util.List;

public class S_ChemShot extends Skill {

    public S_ChemShot(Hero hero) {
        super(hero);
        this.iconPath = "entities/sniper/icons/chemshot.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.targetType = TargetType.ANY;
        this.possibleCastPositions = new int[]{0,1,2};
    }

    @Override
    public int getAIRating(Hero target) {
        return target.getMissingLifePercentage() / 25;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        if (target.isTeam2() == this.hero.isTeam2()) {
            target.addEffect(new RegenBoost(1), this.hero);
        } else {
            target.addEffect(new Injured(1), this.hero);
        }
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "If targeting an ally: Give "+RegenBoost.getStaticIconString()+"(1). Otherwise: Give " + Injured.getStaticIconString() + "(1).";
    }

    @Override
    public String getName() {
        return "Chemical arrow";
    }
}
