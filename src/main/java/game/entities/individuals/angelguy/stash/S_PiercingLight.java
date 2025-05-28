package game.entities.individuals.angelguy.stash;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;

import java.util.List;

public class S_PiercingLight extends Skill {

    public S_PiercingLight(Hero hero) {
        super(hero);
        this.iconPath = "entities/angelguy/icons/piercinglight.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;
        this.possibleTargetPositions = new int[]{3};
        this.possibleCastPositions = new int[]{1,2};
        this.dmg = 13;
        this.cdMax = 2;
        this.damageMode = DamageMode.MAGICAL;
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.3));

    }

    @Override
    public int getLethality() {
        return 20 * this.hero.getStat(Stat.CURRENT_HALO);
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Piercing Light has 20%" + Stat.LETHALITY.getIconString() + " per " + Stat.HALO.getIconString() + " stack.";
    }

    @Override
    public String getName() {
        return "Piercing Light";
    }
}
