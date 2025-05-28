package game.entities.individuals.eldritchguy;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Blight;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.effects.RegenStop;

import java.util.List;

public class S_GraspOfTheAbyss extends Skill {

    public S_GraspOfTheAbyss(Hero hero) {
        super(hero);
        this.iconPath = "entities/eldritchguy/icons/graspoftheabyss.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.2));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{3};
        this.dmg = 8;
        this.damageMode = DamageMode.TRUE;
        this.effects = List.of(new RegenStop(1));
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Heal for 15% of the damage dealt";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
    }
    public void dmgTrigger(DmgTriggerPayload pl) {
        if (this.equals(pl.cast)) {
            this.hero.heal(pl.cast.hero, (int)(pl.dmgDone*0.15), this, false);
        }
    }
    @Override
    public String getName() {
        return "Grasp of the Abyss";
    }
}
