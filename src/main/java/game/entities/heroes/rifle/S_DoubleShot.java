package game.entities.heroes.rifle;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CastChangePayload;
import framework.graphics.text.TextEditor;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.logic.SkillTag;
import game.skills.logic.TargetType;
import game.skills.changeeffects.effects.other.DoubleShot;

import java.util.List;

public class S_DoubleShot extends Skill {


    public S_DoubleShot(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/rifle/icons/doubleshot.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SELF;
        this.effects = List.of(new DoubleShot(3));
        this.level = 2;
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
//        Skill skill = castChangePayload.skill;
//        if (skill != null && skill.hero.equals(this.hero) && skill instanceof S_Barrage) {
//            skill.setCdMax(skill.getCdMax() -1);
//        }
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "Passive: While not on cooldown -1["+ TextEditor.TURN_KEY+"] for Barrage.";
    }

    @Override
    public String getName() {
        return "Weapon Upgrade: Double Shot";
    }
}
