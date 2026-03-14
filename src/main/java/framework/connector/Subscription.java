package framework.connector;

import game.effects.Effect;
import game.skills.Skill;

public class Subscription {
    public String topicName;
    public String methodName;
    public SubscriptionTrigger trigger;

    public boolean triggered(ConnectionPayload pl) {
        return trigger.triggered(pl);
    }
    public void setPosition(int position) {
        this.trigger.setPosition(position);
    }
    public void setEffect(Effect effect) {
        this.trigger.setEffect(effect);
    }
    public void setSkill(Skill skill) {
        this.trigger.setSkill(skill);
    }
}
