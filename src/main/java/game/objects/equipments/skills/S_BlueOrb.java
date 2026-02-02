package game.objects.equipments.skills;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.StartOfMatchPayload;
import game.entities.Hero;
import game.objects.Equipment;
import game.skills.Skill;
import game.skills.logic.TargetType;
import game.skills.changeeffects.effects.other.Frost;

public class S_BlueOrb extends Skill {

    private boolean isUsedUp = false;

    public S_BlueOrb(Equipment equipment) {
        super();
        this.equipment = equipment;
        this.iconPath = "icons/skills/blueorb.png";
        setToInitial();
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.START_OF_MATCH, new Connection(this, StartOfMatchPayload.class, "start"));
    }
    public void start(StartOfMatchPayload pl) {
        this.isUsedUp = false;
    }
    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE_OTHER;
        this.moveTo = true;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        for (Hero enemy : this.hero.getEnemies()) {
            enemy.addEffect(new Frost(1), this.hero);
        }
        this.isUsedUp = true;
    }


    @Override
    public void reset() {
        super.reset();
        this.isUsedUp = false;
    }
    public int getAIRating(Hero target) {
        return getRollRating(target);
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Move 1, give 1 Frost to each enemy. Only use once.";
    }

    @Override
    public String getName() {
        return "Blue Orb";
    }
}
