package game.entities.heroes.battleaxe.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.status.Bleeding;
import game.skills.changeeffects.effects.status.Dazed;
import game.skills.logic.*;

import java.util.List;

public class S_Headsmash extends Skill {

    public S_Headsmash(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/battleaxe/icons/headsmash.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.aiTags = List.of(AiSkillTag.COMBO_ENABLED);
        this.dmgMultipliers = List.of(new Multiplier(Stat.DEFENSE, 0.3));
        this.targetType = TargetType.SINGLE;
        this.dmg = 1;  
        this.effects = List.of(new Dazed(3), new Bleeding(1));
    }
    @Override
    public String getName() {
        return "Headsmash";
    }
}
