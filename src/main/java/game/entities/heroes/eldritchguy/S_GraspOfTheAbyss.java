package game.entities.heroes.eldritchguy;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;

import java.util.List;

public class S_GraspOfTheAbyss extends Skill {

    public S_GraspOfTheAbyss(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/eldritchguy/icons/graspoftheabyss.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.6), new Multiplier(Stat.MAGIC, 0.75));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{3};
        this.damageMode = DamageMode.TRUE;
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Heal for 50% of the damage dealt";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
    }
    public void dmgTrigger(DmgTriggerPayload pl) {
        if (this.equals(pl.cast)) {
            this.hero.heal(pl.cast.hero, (int)(pl.dmgDone*0.5), this, false);
        }
    }
    @Override
    public String getName() {
        return "Grasp of the Abyss";
    }
}
