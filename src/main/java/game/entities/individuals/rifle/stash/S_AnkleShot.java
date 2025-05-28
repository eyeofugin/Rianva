package game.entities.individuals.rifle.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;

import java.util.List;

public class S_AnkleShot extends Skill {

    public S_AnkleShot(Hero hero) {
        super(hero);
        this.iconPath = "entities/rifle/icons/ankleshot.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{3,4,5};
        this.dmg = 11;
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.1));
        this.damageMode = DamageMode.PHYSICAL;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.addToStat(Stat.SPEED, -2);
    }




    @Override
    public String getDescriptionFor(Hero hero) {
        return "Target gets -2" + Stat.SPEED.getIconString() + ".";
    }


    @Override
    public String getName() {
        return "Ankle Shot";
    }
}
