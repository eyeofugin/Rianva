package game.entities.heroes.rifle;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CastChangePayload;
import game.entities.Hero;
import game.entities.heroes.rifle.stash.S_AnkleShot;
import game.skills.Skill;
import game.skills.logic.SkillTag;
import game.skills.logic.TargetType;
import game.skills.changeeffects.effects.other.StatEffect;

import java.util.List;

public class S_UseTheScope extends Skill {


    public S_UseTheScope(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/rifle/icons/usethescope.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SELF;
        this.effects = List.of(StatEffect.focused.getNew());
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
//        if (cdCurrent > 0) {
//            return;
//        }
        Skill skill = castChangePayload.skill;
        if (skill != null && skill.hero.equals(this.hero) && (skill instanceof S_PiercingBolt || skill instanceof S_AnkleShot)) {
            skill.setAccuracy(skill.getAccuracy() + 20);
        }
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Passive: While not on cooldown +20 Accuracy for primary skills.";
    }

    @Override
    public String getName() {
        return "Weapon Upgrade: Scope";
    }
}
