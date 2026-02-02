package game.entities.heroes.eldritchguy.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_VoidGraft extends Skill {

    public S_VoidGraft(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/eldritchguy/icons/voidgraft.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.healMultipliers = List.of(new Multiplier(Stat.STAMINA, 0.8));
        this.targetType = TargetType.SELF;
    }


    @Override
    public String getName() {
        return "Void Graft";
    }
}
