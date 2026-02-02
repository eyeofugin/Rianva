package game.entities.heroes.battleaxe.stash;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgChangesPayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*; 
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_CutDown extends Skill {

    public S_CutDown(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/battleaxe/icons/cutdown.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.ATTACK, 0.2));
        this.targetType = TargetType.SINGLE;
        this.dmg = 9;  
    }

    @Override
    public int getAIRating(Hero target) {
        return 1;
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_CHANGES, new Connection(this, DmgChangesPayload.class, "dmgChanges"));
    }

    public void dmgChanges(DmgChangesPayload pl) {
        if (pl.target != null && pl.caster != null
            && pl.caster.equals(this.hero) && pl.skill instanceof S_CutDown &&
                pl.target.getStat(Stat.SHIELD) > 0) {
            pl.dmg *= 2;
        }
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Double damage if the target has " + Stat.SHIELD.getIconString() + ".";
    }

    @Override
    public String getName() {
        return "Cut Down";
    }
}
