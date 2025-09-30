package game.entities.heroes.angelguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.StatEffect;

import java.util.List;

public class S_LightJavelin extends Skill {

    public S_LightJavelin(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/angelguy/icons/lightjavelin.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;
        this.possibleTargetPositions = new int[]{3,4,5};
        this.possibleCastPositions = new int[]{0,1,2};
        this.effects = List.of(new StatEffect(1, Stat.EVASION, -0.1));
        this.damageMode = DamageMode.PHYSICAL;
        this.faithGain = 10;
        this.faithRequirement = 15;
        this.level = 2;
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.8));
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.addToStat(Stat.EVASION, -10);
    }

    @Override
    public String getName() {
        return "Light Javelin";
    }
}
