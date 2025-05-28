package game.entities.individuals.rifle;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CastChangePayload;
import game.entities.Hero;
import game.entities.individuals.rifle.stash.S_AnkleShot;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Scoped;

import java.util.List;

public class S_UseTheScope extends Skill {


    public S_UseTheScope(Hero hero) {
        super(hero);
        this.iconPath = "entities/rifle/icons/usethescope.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SELF;
        this.possibleCastPositions = new int[]{0,1,2};
        this.effects = List.of(new Scoped(2));
        this.cdMax = 2;
    }

    @Override
    public int getAIRating(Hero target) {
        return 3;
    }
    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.CAST_CHANGE, new Connection(this, CastChangePayload.class,"castChange"));
    }

    public void castChange(CastChangePayload castChangePayload) {
        Skill skill = castChangePayload.skill;
        if (skill != null && skill.hero.equals(this.hero) && (skill instanceof S_PiercingBolt || skill instanceof S_AnkleShot)) {
            skill.setAccuracy(skill.getAccuracy() + 20);
        }
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Passive: +20 Accuracy for primary skills.";
    }

    @Override
    public String getName() {
        return "Weapon Upgrade: Scope";
    }
}
