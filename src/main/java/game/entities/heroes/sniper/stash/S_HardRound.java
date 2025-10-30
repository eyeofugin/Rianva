package game.entities.heroes.sniper.stash;

import game.entities.Hero;
import game.skills.*; 
import game.skills.logic.SkillTag;
import game.skills.logic.TargetType;

import java.util.List;

public class S_HardRound extends Skill {

    public S_HardRound(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/sniper/icons/hardround.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.targetType = TargetType.SINGLE;
        this.dmg = 8;  
    }

    @Override
    public String getName() {
        return "Hard Round";
    }
}
