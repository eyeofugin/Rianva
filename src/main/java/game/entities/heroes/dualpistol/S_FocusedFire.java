package game.entities.heroes.dualpistol;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgChangesPayload;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.Stat;

import java.util.List;

public class S_FocusedFire extends Skill {
    public S_FocusedFire(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/dualpistol/icons/focusedfire.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL, SkillTag.PASSIVE);
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Deal double damage to targets with less than 50%" + Stat.LIFE.getReference()+ ".";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_CHANGES, new Connection(this, DmgChangesPayload.class, "dmgChanges"));
    }

    public void dmgChanges(DmgChangesPayload pl) {
        if (this.hero.equals(pl.caster)) {
            if (pl.target.getCurrentLifePercentage() <= 50) {
                pl.dmg *= 2;
            }
        }
    }

    @Override
    public String getName() {
        return "Focused Fire";
    }
}
