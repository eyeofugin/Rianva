package game.skills.trees.roles.inspiration;

import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class S_CoolVeins extends Skill {

    public void castChange(ConnectionPayload pl) {
        if (pl.skill.getMaxCd() < 2) {
            return;
        }
        if (this.hero.getSkills().stream().noneMatch(s->s.getCurrentCd() > 0)) {
            pl.skill.setMaxCd(Math.min(1, pl.skill.getMaxCd() - 2));
        }
    }
}
