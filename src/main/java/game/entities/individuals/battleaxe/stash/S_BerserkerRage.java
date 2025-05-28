package game.entities.individuals.battleaxe.stash;

import game.entities.Hero;
import game.skills.*;
import game.skills.changeeffects.effects.LifeSteal;
import game.skills.changeeffects.effects.Threatening;

import java.util.List;

public class S_BerserkerRage extends Skill {

    public S_BerserkerRage(Hero hero) {
        super(hero);
        this.iconPath = "entities/battleaxe/icons/berserkerrage.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.aiTags = List.of(AiSkillTag.COMBO_ENABLED);
        this.targetType = TargetType.SELF;
        this.possibleCastPositions = new int[]{0,1,2};
        this.effects = List.of(new LifeSteal(2), new Threatening(2));
        this.lifeCost = 15;
        this.cdMax = 3;
    }
//
//    @Override
//    public void applySkillEffects(Hero target) {
//        super.applySkillEffects(target);
//        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
//            this.hero.removePermanentEffectOfClass(Combo.class);
//            this.hero.getEnemies().forEach(e->e.changeStatTo(Stat.SHIELD, 0));
//        }
//    }

    @Override
    public int getAIRating(Hero target) {
        if (this.hero.getCurrentLifePercentage() < 50) {
            return -1;
        }
        return 1;
    }

//    @Override
//    public String getComboDescription(Hero hero) {
//        return "Enemies lose all "+Stat.SHIELD.getIconString()+".";
//    }


    @Override
    public String getName() {
        return "Berserker Rage";
    }
}
