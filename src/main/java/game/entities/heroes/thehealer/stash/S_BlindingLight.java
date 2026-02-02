package game.entities.heroes.thehealer.stash;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.logic.SkillTag;
import game.skills.logic.TargetType;
import game.skills.changeeffects.effects.other.StatEffect;

import java.util.List;

public class S_BlindingLight extends Skill {

    public S_BlindingLight(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/thehealer/icons/blindinglight.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;
        this.effects = List.of(StatEffect.blinded.getNew());
    }

    @Override
    public int getAIRating(Hero target) {
        return 4;
    }

    @Override
    public String getName() {
        return "Blinding Light";
    }
}
