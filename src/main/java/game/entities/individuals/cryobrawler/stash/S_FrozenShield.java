package game.entities.individuals.cryobrawler.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;

import java.util.List;

public class S_FrozenShield extends Skill {

    public S_FrozenShield(Hero hero) {
        super(hero);
        this.iconPath = "entities/cryobrawler/icons/frostshield.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SELF;
        this.possibleCastPositions = new int[]{0,1,2};
        this.shield = 5;
        this.shieldMultipliers = List.of(new Multiplier(Stat.MANA, 0.2));
        this.manaCost = 6;
    }

    @Override
    public String getName() {
        return "Frozen Shield";
    }
}
