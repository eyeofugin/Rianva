package game.entities.heroes.cryobrawler.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.other.Frost; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_FrostBreath extends Skill {

    public S_FrostBreath(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/cryobrawler/icons/frostbreath.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.effects = List.of(new Frost(1));
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.1),
                new Multiplier(Stat.MANA, 0.1));
        this.targetType = TargetType.ALL_ENEMY;  
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
