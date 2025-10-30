package game.entities.heroes.duelist.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.other.StatEffect; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_Reposte extends Skill {

    public S_Reposte(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/duelist/icons/reposte.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.ATTACK, 0.3));
        this.targetType = TargetType.SINGLE;
        this.dmg = 8;
        this.casterEffects = List.of(StatEffect.covered.getNew());  
    }

    @Override
    public String getName() {
        return "Reposte";
    }
}
