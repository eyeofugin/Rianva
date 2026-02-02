package game.entities.heroes.eldritchguy.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_VoidLeech extends Skill {

    public S_VoidLeech(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/eldritchguy/icons/voidleech.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.1));
        this.targetType = TargetType.SINGLE;
        this.dmg = 2;  
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        Stat resource = target.getSecondaryResource();
        if (resource != null) {
            switch (resource) {
                case MANA -> target.addResource(Stat.CURRENT_MANA, Stat.MANA, -5, this.hero);
            }
        }
        this.hero.addResource(Stat.CURRENT_MANA, Stat.MANA, 2, this.hero);
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "";//return "Target loses either 10"+Stat.FAITH.getIconString()+" or 5"+Stat.MANA.getIconString()+". Gain +5" +Stat.MANA.getIconString() +".";
    }


    @Override
    public String getName() {
        return "Void Leech";
    }
}
