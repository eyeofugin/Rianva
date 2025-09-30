package game.entities.heroes.dualpistol.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Bleeding;

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
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{6,7};
        this.dmg = 6;
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.4));
        this.damageMode = DamageMode.PHYSICAL;
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
