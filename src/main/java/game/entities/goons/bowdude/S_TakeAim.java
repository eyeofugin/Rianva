package game.entities.goons.bowdude;

import game.entities.Hero;
import game.entities.heroes.dualpistol.stash.S_LuckyShot;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.StatEffect;
import game.skills.changeeffects.effects.Threatening;

import java.util.List;

public class S_TakeAim extends Skill {

    public S_TakeAim(Hero hero) {
        super(hero);
        this.iconPath = "entities/goons/bowdude/icons/takeaim.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SELF;
        this.possibleCastPositions = new int[]{0,1,2};
        this.effects = List.of(new StatEffect(3, Stat.CRIT_CHANCE, 50));
        this.cdMax = 5;
    }

    @Override
    public String getName() {
        return "Take Aim";
    }
}
