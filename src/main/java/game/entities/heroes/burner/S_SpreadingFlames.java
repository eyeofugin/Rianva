package game.entities.heroes.burner;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;

import java.util.List;
import java.util.Random;

public class S_SpreadingFlames extends Skill {

    public S_SpreadingFlames(Hero hero) {
        super(hero);
        this.iconPath = "entities/heroes/burner/icons/spreadingflames.png";
        addSubscriptions();
        setToInitial();
    }
    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.ULT);
        this.targetType = TargetType.ALL_TARGETS;
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{3,4,5};
        this.faithRequirement = 80;
        this.faithCost = 20;
        this.level = 5;
    }



    @Override
    public String getDescriptionFor(Hero hero) {
        return "For each "+Burning.getStaticIconString()+" stack among enemies, give another " +Burning.getStaticIconString()+" stack to a random enemy.";
    }

    @Override
    public int getAIRating(Hero target) {
        return target.getPermanentEffectStacks(Burning.class) / 2;
    }

    @Override
    public void resolve() {
        List<Hero> enemies = this.hero.getEnemies();
        int enemyBurningStacks = enemies.stream().mapToInt(e->e.hasPermanentEffect(Burning.class)).sum();
        int amntEnemies = enemies.size();
        for (int i = 0; i < enemyBurningStacks; i++) {
            Random random = new Random();
            int enemyIndex = random.nextInt(amntEnemies);
            Hero enemy = enemies.get(enemyIndex);
            enemy.addEffect(new Burning(1), this.hero);
        }
        this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, -30, this.hero);
    }

    @Override
    public String getName() {
        return "Spreading Flames";
    }
}
