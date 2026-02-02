package game.entities.heroes.dualpistol.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.status.Bleeding;
import game.skills.logic.*;

import java.util.List;

public class S_CarefulShot extends Skill {

    public S_CarefulShot(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/dualpistol/icons/carefulshot.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.aiTags = List.of(AiSkillTag.COMBO_ENABLED);
        this.targetType = TargetType.SINGLE;
        this.dmg = 6;
        this.dmgMultipliers = List.of(new Multiplier(Stat.ATTACK, 0.4));  
        this.critChance = 20;
    }



    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.addEffect(new Bleeding(1), this.hero);
    }
    @Override
    public String getComboDescription(Hero hero) {
        return "20%"+Stat.CRIT_CHANCE.getIconString()+".Give " + Bleeding.getStaticIconString() + "(1).";
    }
    @Override
    public String getName() {
        return "Careful Shot";
    }
}
