package game.entities.heroes.phoenixguy;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.changeeffects.effects.other.Burning;

import java.util.List;

public class S_HonorTheFirstFlame extends Skill {

    public S_HonorTheFirstFlame(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/phoenixguy/icons/honorthefirstflame.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL, SkillTag.PASSIVE);
        this.level = 2;
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "";//return "Whenever an opponent is dealt damage by "+Burning.getStaticIconString()+", get twice that much " + Stat.MANA.getIconString() + ".";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.EFFECT_DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
    }

    @Override
    public String getName() {
        return "Honor the flame";
    }

    public void dmgTrigger(DmgTriggerPayload pl) {
        if (pl.target != null && pl.effect != null && pl.effect.getClass().equals(Burning.class)) {
            if (pl.target.isTeam2() != this.hero.isTeam2()) {
//                this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 2*pl.dmgDone, this.hero);
            }
        }
    }
}
