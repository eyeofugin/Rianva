package game.entities.goons.axedude;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.TargetType;
import game.skills.changeeffects.effects.LifeSteal;
import game.skills.changeeffects.effects.Threatening;

import java.util.List;

public class S_ScaryCry extends Skill {

    public S_ScaryCry(Hero hero) {
        super(hero);
        this.iconPath = "entities/goons/axedude/icons/scyarycry.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SELF;
        this.possibleCastPositions = new int[]{0,1,2};
        this.effects = List.of(new Threatening(1));
        this.cdMax = 2;
    }

    @Override
    public String getName() {
        return "Scary Cry";
    }
}
