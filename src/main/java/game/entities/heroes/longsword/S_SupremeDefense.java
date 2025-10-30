package game.entities.heroes.longsword;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgChangesPayload;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;
import game.skills.changeeffects.effects.other.StatEffect;

import java.util.List;

public class S_SupremeDefense extends Skill {

    public S_SupremeDefense(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/longsword/icons/supremedefense.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.ULT);
        this.targetType = TargetType.SELF;
        this.casterEffects = List.of(StatEffect.robust.getNew());
        this.level = 5;
    }

//    @Override
//    public String getUpperDescriptionFor(Hero hero) {
//        return "Active: Gain permanently +5" + Stat.DEFENSE.getIconString() + ".";
//    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Passive: Reduces damage that is less than 10%"+Stat.LIFE.getReference()+" to 0.";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_CHANGES, new Connection(this, DmgChangesPayload.class, "dmgChanges"));
    }
    public void dmgChanges(DmgChangesPayload pl) {
        if (this.hero.equals(pl.target)) {
            int tenPercent = this.hero.getStat(Stat.LIFE) / 10;
            if (pl.dmg < tenPercent) {
                pl.dmg *= 0;
            }
        }
    }

    @Override
    public String getName() {
        return "Supreme Defense";
    }
}
