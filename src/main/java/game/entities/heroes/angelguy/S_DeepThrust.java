package game.entities.heroes.angelguy;

import framework.graphics.text.Color;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.status.Bleeding;
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_DeepThrust extends Skill {

    public S_DeepThrust(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/angelguy/icons/deepthrust.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.targetType = TargetType.SINGLE;
        this.dmgMultipliers = List.of(new Multiplier(Stat.ATTACK, 1));
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return this.hero.getStat(Stat.MAGIC) + 30 +" ("+Stat.MAGIC.getColorKey() + this.hero.getStat(Stat.MAGIC) + Stat.MAGIC.getIconString()+ Color.WHITE.getCodeString()+"+30)% chance to give " + Bleeding.getStaticIconString() + "(1).";
    }

    @Override
    public String getName() {
        return "Deep Thrust";
    }
}
