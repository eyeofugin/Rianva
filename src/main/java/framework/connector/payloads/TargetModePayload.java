package framework.connector.payloads;

import framework.connector.ConnectionPayload;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.logic.TargetMode;

public class TargetModePayload extends ConnectionPayload {
    public Hero target;
    public Skill cast;
    public TargetMode targetMode = TargetMode.NORMAL;

    public TargetModePayload setTarget(Hero target) {
        this.target = target;
        return this;
    }

    public TargetModePayload setCast(Skill cast) {
        this.cast = cast;
        return this;
    }
}
