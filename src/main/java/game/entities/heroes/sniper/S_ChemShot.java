package game.entities.heroes.sniper;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Injured;

import java.util.List;

public class S_ChemShot extends Skill {

    public S_ChemShot(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/sniper/icons/chemshot.png";
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
            target.heal(this.hero, _getHeal(), this, false);
        } else {
            target.addEffect(new Injured(2), this.hero);
        }
    }

    public int _getHeal() {
        return (int)(this.hero.getStat(Stat.CRIT_CHANCE) * 0.15);
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "If targeting an ally: Heal "+this._getHeal()+"(15%"+Stat.CRIT_CHANCE.getIconString()+"). Otherwise: Give " + Injured.getStaticIconString() + "(2).";
    }

    @Override
    public String getName() {
        return "Chemical arrow";
    }
}
