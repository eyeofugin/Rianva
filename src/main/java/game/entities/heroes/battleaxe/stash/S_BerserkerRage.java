package game.entities.heroes.battleaxe.stash;

import game.entities.Hero;
import game.skills.*;
import game.skills.changeeffects.effects.other.LifeSteal;
import game.skills.changeeffects.effects.other.Threatening;
import game.skills.logic.AiSkillTag;
import game.skills.logic.SkillTag;
import game.skills.logic.TargetType;

import java.util.List;

public class S_BerserkerRage extends Skill {

    public S_BerserkerRage(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/battleaxe/icons/berserkerrage.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.aiTags = List.of(AiSkillTag.COMBO_ENABLED);
        this.targetType = TargetType.SELF;
        this.effects = List.of(new LifeSteal(2), new Threatening(2));
        this.lifeCost = 15;
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
