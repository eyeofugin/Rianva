package game.entities.individuals.eldritchguy.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;

import java.util.List;

public class S_VoidLeech extends Skill {

    public S_VoidLeech(Hero hero) {
        super(hero);
        this.iconPath = "entities/eldritchguy/icons/voidleech.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.1));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{3,4};
        this.dmg = 2;
        this.damageMode = DamageMode.PHYSICAL;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        Stat resource = target.getSecondaryResource();
        if (resource != null) {
            switch (resource) {
                case MANA -> target.addResource(Stat.CURRENT_MANA, Stat.MANA, -5, this.hero);
                case FAITH -> target.addResource(Stat.CURRENT_FAITH, Stat.FAITH, -10, this.hero);
            }
        }
        this.hero.addResource(Stat.CURRENT_MANA, Stat.MANA, 2, this.hero);
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Target loses either 10"+Stat.FAITH.getIconString()+" or 5"+Stat.MANA.getIconString()+". Gain +5" +Stat.MANA.getIconString() +".";
    }


    @Override
    public String getName() {
        return "Void Leech";
    }
}
