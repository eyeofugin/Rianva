package game.entities.heroes.dualpistol.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.logic.*;

import java.util.List;

public class S_LuckyShot extends Skill {

    public S_LuckyShot(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/dualpistol/icons/carefulshot.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.aiTags = List.of(AiSkillTag.COMBO_ENABLED);
        this.targetType = TargetType.SINGLE;
        this.dmg = 3;
        this.dmgMultipliers = List.of(new Multiplier(Stat.ATTACK, 0.05));  
        this.critChance = 50;
    }



    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        Effect effectOne = Effect.getRdmDebuff();
        target.addEffect(effectOne, this.hero);
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "50%"+Stat.CRIT_CHANCE.getIconString()+". Gives a random debuff.";
    }
    @Override
    public String getName() {
        return "Lucky Shot";
    }
}
