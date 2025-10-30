package game.skills;

import framework.connector.payloads.BaseDmgChangesPayload;

public class SkillScripts {
    private Skill skill;

    public SkillScripts(Skill skill) {
        this.skill = skill;
    }

    public void burnBaseChanges(BaseDmgChangesPayload pl) {
        System.out.println(this.skill.name);
    }
}
