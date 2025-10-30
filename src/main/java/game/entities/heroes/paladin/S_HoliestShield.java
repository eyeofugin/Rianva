package game.entities.heroes.paladin;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.StartOfMatchPayload;
import framework.connector.payloads.StartOfTurnPayload;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.logic.SkillTag;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

import java.util.List;

public class S_HoliestShield extends Skill {

    public S_HoliestShield(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/paladin/icons/holiestshield.png";
        addSubscriptions();
        setToInitial();
        this.hero.shield(this.hero.getStat(Stat.LIFE) * 10 / 100, this.hero);
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.ULT, SkillTag.PASSIVE);
        this.targetType = TargetType.SELF;
        this.level = 5;
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Passive: Start each round with "+(int)(0.15 * this.hero.getStat(Stat.LIFE))+"(15%"+Stat.LIFE.getReference()+")"+Stat.SHIELD+".";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(this.hero.getId() + Connector.START_OF_TURN, new Connection(this, StartOfTurnPayload.class, "startOfTurn"));
        Connector.addSubscription(Connector.START_OF_MATCH, new Connection(this, StartOfMatchPayload.class, "startOfMatch"));
    }
    public void startOfTurn(StartOfTurnPayload pl) {
        int shield = this.hero.getStat(Stat.SHIELD);
        int shiningShield = this.hero.getStat(Stat.LIFE) * 15 / 100;
        if (shield < shiningShield) {
            this.hero.changeStatTo(Stat.SHIELD, shiningShield);
        }
    }
    public void startOfMatch(StartOfMatchPayload pl) {
        this.startOfTurn(null);
    }

    @Override
    public String getName() {
        return "Holy Shield";
    }
}
