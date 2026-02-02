package game.skills.logic;

import game.entities.Hero;

import java.util.Random;

public class Condition {
    public ConditionTrigger trigger;

    public String effectName;
    public boolean hasCheck;

    public boolean healthComparison;
    public boolean manaComparison;
    public double percentage;
    public boolean lessThan;

    public boolean chance;
    public int successChance;

    public boolean isMet(Hero hero, Hero target) {

        if (this.effectName != null) {
            int amnt = 0;
            switch (trigger) {
                case CASTER -> amnt = hero.hasPermanentEffect(this.effectName);
                case TARGET -> amnt = target.hasPermanentEffect(this.effectName);
                case ANY -> amnt = hero.arena.amountEffects(this.effectName);
                case ANY_ALLY -> amnt = hero.team.amountEffects(this.effectName);
                case ANY_ENEMY -> amnt = hero.enemyTeam.amountEffects(this.effectName);
                case ARENA -> amnt = hero.arena.globalEffect != null && hero.arena.globalEffect.getName().equals(this.effectName) ? 1:0;
            }
            return hasCheck ? amnt > 0 : amnt < 1;
        }
        if (this.healthComparison) {
            if (trigger == ConditionTrigger.CASTER) {
                return resourceCheck(hero.getCurrentLifePercentage());
            }
            if (trigger == ConditionTrigger.TARGET) {
                return resourceCheck(target.getCurrentLifePercentage());
            }
        }
        if (this.manaComparison) {
            if (trigger == ConditionTrigger.CASTER) {
                return resourceCheck(hero.getCurrentManaPercentage());
            }
            if (trigger == ConditionTrigger.TARGET) {
                return resourceCheck(target.getCurrentManaPercentage());
            }
        }

        if (chance) {
            Random rand = new Random();
            int next = rand.nextInt(1,101);
            return next > successChance;

        }



        return false;
    }
    private boolean resourceCheck(int resourcePercentage) {
        return lessThan ? resourcePercentage < percentage
                : resourcePercentage > percentage;
    }
}
