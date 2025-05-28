package game.entities.individuals.cryobrawler.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Frost;
import game.skills.changeeffects.statusinflictions.Injured;

import java.util.List;

public class S_FrostBite extends Skill {

    public S_FrostBite(Hero hero) {
        super(hero);
        this.iconPath = "entities/cryobrawler/icons/frostbite.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.2),
                new Multiplier(Stat.MAGIC, 0.2));
        this.effects = List.of(new Injured(1), new Frost(1));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{3};
        this.dmg = 9;
        this.damageMode = DamageMode.PHYSICAL;
    }

    @Override
    public String getName() {
        return "Frost Bite";
    }
}
