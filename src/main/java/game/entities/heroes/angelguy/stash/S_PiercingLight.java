package game.entities.heroes.angelguy.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_PiercingLight extends Skill {

    public S_PiercingLight(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/angelguy/icons/piercinglight.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;
        this.dmg = 13;  
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.3));

    }

    @Override
    public int getLethality() {
//        return 20 * this.hero.getStat(Stat.CURRENT_HALO);
        return 10;
    }

    @Override
    public String getDescriptionFor(Hero hero) {
//        return "Piercing Light has 20%" + Stat.LETHALITY.getIconString() + " per " + Stat.HALO.getIconString() + " stack.";
        return "";
    }

    @Override
    public String getName() {
        return "Piercing Light";
    }
}
