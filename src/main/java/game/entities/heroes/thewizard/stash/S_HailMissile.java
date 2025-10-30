package game.entities.heroes.thewizard.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.status.Injured;
import game.skills.logic.*;

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
        this.dmg = 4;  
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
