package game.entities.heroes.duelist;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.logic.SkillTag;
import game.skills.logic.TargetType;
import game.skills.changeeffects.effects.status.Taunted;

import java.util.List;

public class S_GiveMeYourWorst extends Skill {

    public S_GiveMeYourWorst(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/duelist/icons/givemeyourworst.png";
        addSubscriptions();
        setToInitial();
    }
    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;
        this.effects = List.of(new Taunted(2));
        this.level = 2;
    }

    @Override
    public String getName() {
        return "Give me your worst";
    }
}
