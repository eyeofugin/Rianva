package game.skills.genericskills;

import game.entities.Hero;
import game.entities.individuals.dev.dummy.DUMMY;
import game.skills.Skill;
import game.skills.TargetType;
import java.util.List;

public class AllyHeal extends Skill {

    public AllyHeal(Hero hero) {
        super(hero);
        this.iconPath = "/icons/spark.png";
        setToInitial();

    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SELF;
        this.possibleCastPositions = new int[]{0,1,2};
        this.heal = 1;
    }

    protected void initAnimation() {
        String path = "sprites/enemies/blorgon/blorgon_melee.png";
        switch(((DUMMY)this.hero).colorIndex) {
            case 1:
                path = "sprites/enemies/blorgon/blorgon_melee.png";
                break;
            case 2:
                path = "sprites/enemies/blorgon_red/blorgon_melee.png";
                break;
            case 3:
                path = "sprites/enemies/blorgon_blue/blorgon_melee.png";
                break;
            case 4:
                path = "sprites/enemies/blorgon_green/blorgon_melee.png";
                break;

        }
        this.hero.anim.setupAnimation(path, this.getName(), new int[]{15, 30, 45, 60});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Descr.";
    }

    @Override
    public String getName() {
        return "Allyheal";
    }
}
