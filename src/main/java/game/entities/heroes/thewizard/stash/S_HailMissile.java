package game.entities.heroes.thewizard.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Injured;

import java.util.List;

public class S_HailMissile extends Skill {

    public S_HailMissile(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/thewizard/icons/hailmissile.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.5));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{3,4};
        this.dmg = 4;
        this.damageMode = DamageMode.MAGICAL;
        this.aiTags = List.of(AiSkillTag.COMBO_ENABLED);
        this.effects = List.of(new Injured(1));
        this.canMiss = false;
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Never misses.";
    }


    @Override
    public String getName() {
        return "Hail Missile";
    }
}
