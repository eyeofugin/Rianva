package game.entities.heroes.divinemage.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.Skill;
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_ShieldRay extends Skill {

    public S_ShieldRay(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/divinemage/icons/shieldray.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.ALL_ENEMY;
        this.shieldMultipliers = List.of(new Multiplier(Stat.CURRENT_MANA, 0.5));
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "";
    }

    @Override
    public String getName() {
        return "Shield Ray";
    }
}
