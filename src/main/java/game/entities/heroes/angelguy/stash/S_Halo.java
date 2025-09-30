package game.entities.heroes.angelguy.stash;

import game.entities.Hero;
import game.skills.*;
import game.skills.changeeffects.effects.Protected;

import java.util.List;

public class S_Halo extends Skill {

    public S_Halo(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/angelguy/icons/halo.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.ULT);
        this.targetType = TargetType.SINGLE;
        this.possibleTargetPositions = new int[]{0,1,2};
        this.possibleCastPositions = new int[]{0,1,2};
        this.priority = 5;
        this.level = 5;
    }

    @Override
    public void resolve() {
        int invincible = this.hero.getStat(Stat.CURRENT_FAITH) / 25;
        this.hero.changeStatTo(Stat.CURRENT_FAITH, 0);
        for (Hero target : targets) {
            target.addEffect(new Protected(invincible), this.hero);
        }
    }

    @Override
    public String getName() {
        return "Halo";
    }
}
