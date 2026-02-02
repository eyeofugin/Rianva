package game.entities.heroes.angelguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.other.StatEffect;
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

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
        this.effects = List.of(StatEffect.exposed.getNew());
        this.level = 2;
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.8));
    }

    @Override
    public String getName() {
        return "Light Javelin";
    }
}
