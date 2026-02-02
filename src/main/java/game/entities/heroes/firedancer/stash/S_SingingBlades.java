package game.entities.heroes.firedancer.stash;

import framework.Logger;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.other.Burning; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;
import utils.MyMaths;

import java.util.List;

public class S_SingingBlades extends Skill {

    public S_SingingBlades(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/firedancer/icons/singingblades.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.15),
                new Multiplier(Stat.ATTACK, 0.4));
        this.targetType = TargetType.SINGLE;
        this.effects = List.of(new Burning(2));
        this.dmg = 10;  
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "If target has less than 50%" + Stat.LIFE.getReference()+ ", this hits twice.";
    }

    @Override
    public void individualResolve(Hero target) {
        this.baseDamageChanges(target, this.hero);
        this.baseHealChanges(target, this.hero);
        int dmg = getDmgWithMulti(target);
        int lethality = this.hero.getStat(Stat.LETHALITY);
        if (target.getMissingLifePercentage() > 50) {
            this.countAsHits = 2;
        }
        for (int i = 0; i < getCountsAsHits(); i++) {
            int dmgPerHit = dmg;
            int critChance = this.hero.getStat(Stat.CRIT_CHANCE);
            critChance += this.critChance;
            if (MyMaths.success(critChance)) {
                this.hero.arena.logCard.addToLog("Crit!");
                Logger.logLn("Crit!");
                dmgPerHit= (int)(dmgPerHit * 1.5);
                this.fireCritTrigger(target, this);
            }
            if (dmgPerHit>0) {
                int doneDamage = target.damage(this.hero, dmgPerHit, lethality, this);
                this.fireDmgTrigger(target,this, doneDamage);
            }
            int heal = this.getHealWithMulti(target);
            if (heal > 0) {
                target.heal(this.hero, heal, this, false);
            }
            int shield = this.getShieldWithMulti(target);
            if (shield > 0) {
                target.shield(shield, this.hero);
            }
            this.applySkillEffects(target);
        }
    }

    @Override
    public String getName() {
        return "Singing Blades";
    }

}
