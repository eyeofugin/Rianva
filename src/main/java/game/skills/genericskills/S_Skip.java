package game.skills.genericskills;

import framework.resources.SpriteLibrary;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.TargetType;

public class S_Skip extends Skill {
    public S_Skip(Hero hero) {
        super(hero);
        this.iconPath = "icons/skills/skip.png";
        this.iconPixels = SpriteLibrary.getSprite(this.getClass().getName());
        this.targetType = TargetType.SELF;
        this.possibleCastPositions = new int[]{0,1,2};
        this.animationName = null;
    }

    @Override
    public int getAIRating(Hero target) {
        return -1;
    }
    @Override
    public void resolve() {
        System.out.println("SKip");
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Skips the turn";
    }

    @Override
    public String getName() {
        return "Skip";
    }
}
