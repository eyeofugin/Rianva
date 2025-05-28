package game.entities.individuals.battleaxe.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.statusinflictions.Bleeding;
import game.skills.changeeffects.statusinflictions.Dazed;

import java.util.List;

public class S_Headsmash extends Skill {

    public S_Headsmash(Hero hero) {
        super(hero);
        this.iconPath = "entities/battleaxe/icons/headsmash.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.aiTags = List.of(AiSkillTag.COMBO_ENABLED);
        this.dmgMultipliers = List.of(new Multiplier(Stat.STAMINA, 0.3));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{2};
        this.possibleTargetPositions = new int[]{3};
        this.dmg = 1;
        this.cdMax = 2;
        this.damageMode = DamageMode.PHYSICAL;
        this.effects = List.of(new Dazed(3), new Bleeding(1));
    }
    @Override
    public String getName() {
        return "Headsmash";
    }
}
