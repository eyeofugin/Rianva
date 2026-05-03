package game.skills.trees.roles.resolve;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.objects.EquipmentType;
import game.skills.Skill;

public class S_HoldTheBanner extends Skill {
    public void castChange(ConnectionPayload pl) {
        Logger.logLn("S_HoldTheBanner.castChange()");
        int distance = Math.abs(this.hero.getPosition()-pl.caster.getPosition());
        if (distance == 1) {
            pl.skill.setAccuracy(pl.skill.getAccuracy() + 10);
        }
    }
    public void endOfTurn(ConnectionPayload pl) {
        Logger.logLn("S_HoldTheBanner.endOfTurn()");
        int distance = Math.abs(this.hero.getPosition()-pl.caster.getPosition());
        if (distance == 1) {
            pl.caster.percentageEnergy((int)keyValues.get("Nrg"), this.hero, this, null, null, false);
        }
    }
}
