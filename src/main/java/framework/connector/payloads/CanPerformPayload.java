package framework.connector.payloads;

import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class CanPerformPayload  extends ConnectionPayload {

    public Skill skill;
    public int[] targetPositions;

    public boolean success = true;

    public CanPerformPayload setTargetPositions(int[] targetPositions) {
        this.targetPositions = targetPositions;
        return this;
    }

    public CanPerformPayload setSkill(Skill skill) {
        this.skill = skill;
        return this;
    }
}
