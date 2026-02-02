package game.entities.heroes.cryobrawler.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.other.Frost;
import game.skills.changeeffects.effects.status.Injured; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_FrostBite extends Skill {

    public S_FrostBite(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/cryobrawler/icons/frostbite.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.ATTACK, 0.2),
                new Multiplier(Stat.MAGIC, 0.2));
        this.effects = List.of(new Injured(1), new Frost(1));
        this.targetType = TargetType.SINGLE;
        this.dmg = 9;  
    }

    @Override
    public String getName() {
        return "Frost Bite";
    }
}
