package game.entities.heroes.sniper.stash;

import game.entities.Hero;
import game.skills.*;

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
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{3,4};
        this.dmg = 8;
        this.damageMode = DamageMode.PHYSICAL;
    }

    @Override
    public String getName() {
        return "Hard Round";
    }
}
