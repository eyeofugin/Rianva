package game.skills.trees.classes.fauna;

import framework.connector.ConnectionPayload;
import game.effects.hero.*;
import game.skills.Skill;
import game.skills.logic.DamageType;

public class S_BeastBlast extends Skill {
    public void castChange(ConnectionPayload pl) {
        if (this.equals(pl.skill)) {
            if (this.hero.hasPermanentEffect(WendigoForm.class)) {
                this.damageType = DamageType.COLD;
            }
            if (this.hero.hasPermanentEffect(ThunderbirdForm.class)) {
                this.damageType = DamageType.SHOCK;
            }
            if (this.hero.hasPermanentEffect(BearForm.class)) {
                this.lifeSteal = (double) keyValues.get("LifeSteal");
            }
            if (this.hero.hasPermanentEffect(DragonForm.class)) {
                this.damageType = DamageType.HEAT;
            }
            if (this.hero.hasPermanentEffect(SnakeForm.class)) {
                this.damageType = DamageType.TOXIC;
            }
        }
    }
}
