package game.entities.heroes.sniper;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.StatEffect;

import java.util.List;

public class S_BlindingShot extends Skill {

    public S_BlindingShot(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/sniper/icons/blindingshot.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{0,1};
        this.possibleTargetPositions = new int[]{3,4};
        this.cdMax = 3;
        this.damageMode = DamageMode.PHYSICAL;
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.2));
        this.effects = List.of(new StatEffect(2, Stat.ACCURACY, -30));
    }

    @Override
    public int getAIRating(Hero target) {
        return 4;
    }


    @Override
    public String getName() {
        return "Blinding Shot";
    }
}
