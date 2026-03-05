package game.entities.goons.sworddude;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.logic.SkillTag;
import game.skills.logic.TargetType;
import game.skills.changeeffects.effects.other.LifeSteal;

import java.util.List;

public class S_BattleCry extends Skill {

    public S_BattleCry(Hero hero) {
        super(hero);
        this.iconPath = "entities/goons/sworddude/icons/battlecry.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SELF;
        this.effects = List.of(new LifeSteal(3));
    }

    @Override
    public String getName() {
        return "Battle Cry";
    }
}
