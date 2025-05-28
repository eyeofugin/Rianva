package game.entities.individuals.cryobrawler;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.effects.Frost;

import java.util.List;

public class S_FrostBreath extends Skill {

    public S_FrostBreath(Hero hero) {
        super(hero);
        this.iconPath = "entities/cryobrawler/icons/frostbreath.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.effects = List.of(new Frost(1));
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.2),
                new Multiplier(Stat.MANA, 0.3));
        this.targetType = TargetType.ALL_TARGETS;
        this.possibleCastPositions = new int[]{2};
        this.possibleTargetPositions = new int[]{3,4};
        this.dmg = 5;
        this.damageMode = DamageMode.MAGICAL;
        this.manaCost = 10;
    }



    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.addToStat(Stat.SPEED, -1);
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Give -1" + Stat.SPEED.getIconString() + ".";
    }

    @Override
    public String getName() {
        return "Frost Breath";
    }
}
