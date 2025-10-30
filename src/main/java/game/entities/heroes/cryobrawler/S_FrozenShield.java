package game.entities.heroes.cryobrawler;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_FrozenShield extends Skill {

    public S_FrozenShield(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/cryobrawler/icons/frostshield.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SELF;
        this.shieldMultipliers = List.of(new Multiplier(Stat.MANA, 0.4));
    }

    @Override
    public String getName() {
        return "Frozen Shield";
    }
}
