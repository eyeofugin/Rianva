package game.skills.trees.classes.hunter;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.effects.hero.Marked;
import game.skills.Skill;

public class S_ShareTheSpoils extends Skill {
    boolean active = false;
    public void onMark(ConnectionPayload pl) {
        Logger.logLn("S_ShareTheSpoils.onMark()");
        if (active) {
            Marked marked = (Marked) pl.target.getPermanentEffectByName(Marked.class.getName());
            marked.used = false;
            active = false;
        }
    }
    public void startOfRound(ConnectionPayload pl) {
        Logger.logLn("S_ShareTheSpoils.startOfRound()");
        this.active = true;
    }
}
