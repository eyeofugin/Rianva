package game.entities.heroes.angelguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.other.Protected;
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_AngelicWings extends Skill {

    public S_AngelicWings(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/angelguy/icons/angelicwings.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE_OTHER;
        this.effects = List.of(new Protected(1));
        this.healMultipliers = List.of(new Multiplier(Stat.DEFENSE, 0.3), new Multiplier(Stat.MAGIC, 0.4));
    }

    @Override
    public String getName() {
        return "Angelic Wings";
    }
}
