package game.entities.heroes.phoenixguy;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgChangesPayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.other.Burning; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_Fireblast extends Skill {

    public S_Fireblast(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/phoenixguy/icons/fireblast.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.dmgMultipliers = List.of(new Multiplier(Stat.CURRENT_MANA, 0.5));
        this.targetType = TargetType.SINGLE;
        this.effects = List.of(new Burning(2));  
    }

    @Override
    public int getAIRating(Hero target) {
        return (target.getMissingLifePercentage() / 50) * 2;
    }



    @Override
    public String getDescriptionFor(Hero hero) {
        return "Deals double the damage, if the target has less than 50%" + Stat.LIFE.getReference() + ".";
    }
    @Override
    public String getName() {
        return "Fireblast";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.BASE_DMG_CHANGES, new Connection(this,  DmgChangesPayload.class, "dmgChanges"));
    }

    public void dmgChanges(DmgChangesPayload pl) {
        if (pl.caster != null && pl.target != null) {
            if (pl.caster == this.hero && (pl.target.getCurrentLifePercentage() < 50)) {
                pl.dmg*=2;
            }
        }
    }
}
