package game.entities.heroes.paladin.stash;

import game.entities.Hero;
import game.skills.*;
import game.skills.changeeffects.effects.status.Dazed;
import game.skills.logic.SkillTag;
import game.skills.logic.TargetType;

import java.util.List;

public class S_ShieldAssault extends Skill {

    public S_ShieldAssault(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/paladin/icons/shieldassault.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int targetPosition = target.getPosition();
        this.hero.arena.moveTo(this.hero, targetPosition);
        Hero firstEnemy = this.hero.arena.getEntitiesAt(new int[]{3})[0];
        firstEnemy.addEffect(new Dazed(2), this.hero);
    }

    @Override
    public int getAIRating(Hero target) {
        return getRollRating(target);
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "Move to position. Give "+Dazed.getStaticIconString()+"(2) to the first opponent.";
    }

    @Override
    public String getName() {
        return "Shield Assault";
    }
}
