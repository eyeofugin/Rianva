package game.entities.individuals.sniper;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.effects.Cover;

import java.util.List;

public class S_SmokeGrenade extends Skill {

    public S_SmokeGrenade(Hero hero) {
        super(hero);
        this.iconPath = "entities/sniper/icons/smokegrenade.png";
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
        this.cdMax = 4;
    }



    @Override
    public int getAIRating(Hero target) {
        return 1 + target.getMissingLifePercentage() / 50;
    }

    @Override
    public void resolve() {
        int turns = this.hero.hasPermanentEffect(Combo.class)>0?2:1;
        for (Hero target : this.targets) {
            target.addEffect(new Cover(turns), this.hero);
        }
        this.hero.removePermanentEffectOfClass(Combo.class);
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Give " +Cover.getStaticIconString()+"(1).";
    }

    @Override
    public String getComboDescription(Hero hero) {
        return "Give " + Cover.getStaticIconString() + "(2) instead.";
    }


    @Override
    public String getName() {
        return "Smoke Grenade";
    }
}
