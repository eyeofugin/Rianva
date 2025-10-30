package game.entities.heroes.phoenixguy;

import framework.graphics.text.Color;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.other.Burning; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;
import utils.MyMaths;

import java.util.List;

public class S_Hotwings extends Skill {

    public S_Hotwings(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/phoenixguy/icons/hotwings.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.35));
        this.targetType = TargetType.SINGLE;  
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int success = this.hero.getStat(Stat.MAGIC) + 30;
        if (MyMaths.success(success)) {
            target.addEffect(new Burning(1), this.hero);
        }
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return this.hero.getStat(Stat.MAGIC) + 30 +" ("+Stat.MAGIC.getColorKey() + "100%" + Stat.MAGIC.getIconString()+ Color.WHITE.getCodeString()+"+30)% chance to give " + Burning.getStaticIconString() + "(1).";
    }
    @Override
    public String getName() {
        return "Hot Wings";
    }
}
