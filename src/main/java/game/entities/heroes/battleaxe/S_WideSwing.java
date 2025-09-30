package game.entities.heroes.battleaxe;

import framework.graphics.text.Color;
import game.entities.Hero;
import game.skills.*;
import game.skills.changeeffects.effects.Bleeding;

import java.util.List;

public class S_WideSwing extends Skill {

    public S_WideSwing(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/battleaxe/icons/wideswing.png";
        addSubscriptions();
        setToInitial();

    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.ULT);
        this.targetType = TargetType.ALL_TARGETS;
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{3,4};
        this.effects = List.of(new Bleeding(1));
        this.damageMode = DamageMode.PHYSICAL;
        this.cdMax = 2;
        this.level = 5;
    }
    @Override
    public int getDmg(Hero target) {
        return (this.hero.getStat(Stat.LIFE) - this.hero.getStat(Stat.CURRENT_LIFE)) / 3;
    }

    @Override
    public boolean performCheck(Hero hero) {
        return super.performCheck(hero) && hero.getCurrentLifePercentage() < 40;
    }

    @Override
    public int getAIRating(Hero target) {
        if (this.hero.getCurrentLifePercentage() > 40) {
            return -10;
        }
        return 1;
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "Activate only when less than " + this.hero.getStat(Stat.LIFE) * 4 / 10 + Stat.LIFE.getColorKey() + "(40%)" + Stat.LIFE.getReference()+ Color.WHITE.getCodeString() +" remaining. Deals damage equal to a third of your missing "+Stat.LIFE.getIconString()+".";
    }

    @Override
    public String getName() {
        return "Wide Swing";
    }
}
