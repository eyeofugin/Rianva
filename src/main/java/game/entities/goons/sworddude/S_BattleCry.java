package game.entities.goons.sworddude;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.LifeSteal;
import game.skills.changeeffects.effects.StatEffect;

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
        this.possibleCastPositions = new int[]{0,1,2};
        this.effects = List.of(new LifeSteal(3));
        this.cdMax = 3;
    }

    @Override
    public String getName() {
        return "Battle Cry";
    }
}
