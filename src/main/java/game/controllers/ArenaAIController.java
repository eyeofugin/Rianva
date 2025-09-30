package game.controllers;

import framework.Logger;
import framework.states.Arena;
import game.entities.Hero;
import game.entities.HeroTeam;
import game.skills.AiSkillTag;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import utils.Action;

import java.util.*;

public class ArenaAIController {

    public Arena arena;
    public AIMemory memory;
    private final List<Action> turnOptions = new ArrayList<>();
    private HeroTeam aiTeam;

    public ArenaAIController(Arena arena) {
        this.arena = arena;
        this.memory = new AIMemory();
    }

    public void setTeam(HeroTeam team) {
        this.aiTeam = team;
    }

    public void chooseActions() {
        evaluateSkills();
        Hero bot = this.aiTeam.getHeroesAsList().stream().filter(h->!h.isMoved()).findFirst().orElse(null);
        if (bot != null) {
            bot.setMoved(true);
            Action action = getBestActionFor(bot);
            if (action != null) {
                this.arena.actionQueue.addAction(getBestActionFor(bot));
            }
        }
//        this.aiTeam.getHeroesAsList().forEach(hero -> this.arena.actionQueue.addAction(getBestActionFor(hero)));
    }

    private Action getBestActionFor(Hero hero) {
        this.turnOptions.sort((o1, o2) -> Integer.compare(o2.rating, o1.rating));
        List<Action> options = this.turnOptions.stream().filter(a->a.caster.equals(hero)).toList();
        if (options.isEmpty()) {
            return null;
        }
        return options.get(0);
    }

    private void evaluateSkills() {
        Logger.aiLogln("Eval For team.");
        this.aiTeam.getHeroesAsList().forEach(hero -> {
           hero.getSkills().stream()
                   .filter(Objects::nonNull)
                   .forEach(skill -> {
                       if (skill.getTargetType().equals(TargetType.ARENA)) {
                           this.turnOptions.add(new Action(2, skill, hero, new Hero[0], new int[0]));
                       } else {
                           getPossibleTargetGroups(skill).forEach(targetPositions -> {
                               if (!hero.canPerform(skill, targetPositions)) {
                                   Logger.aiLogln("Cant perform " + skill.getName());
                                   return;
                               }
                               Hero[] targets = this.arena.getEntitiesAt(targetPositions);
                               Action action = new Action(getRating(skill, targets), skill, hero, targets, targetPositions);
                               Logger.aiLogln(action.caster.getName() + " " + action.skill.getName() + " " + action.rating + " " + action.targetPositions);
                               this.turnOptions.add(action);
                           });
                       }
            });
        });
    }


//--------------------RATINGS-----------------------------

    private int getRating(Skill s, Hero[] targets) {
        int rating = 0; //Rating borders ~+-20(==400%dmg/heal)
        rating += getDamageRating(s, targets);
        rating += getHealRating(s,targets);
        rating += getCustomAIRating(s, targets);
        rating += getShieldRating(s, targets);
        rating += getFaithGainRating(s);
        return rating;
    }
    private int getDamageRating(Skill cast, Hero[] targets) {
        if (cast.getDamageMode() == null) {
            return 0;
        }
        int weightedPercentages = 0;
        int lethality = 0; // this.arena.activeHero.getStat(Stat.LETHALITY, cast);
        for (Hero e : targets) {
            int estimatedDamage = cast.getDmgWithMulti(e);
//            Logger.aiLog(" target:"+e.getName());

            int dmgPercentage = e.simulateDamageInPercentages(this.arena.activeHero, estimatedDamage, cast.getDamageMode(), lethality, cast);
            if (dmgPercentage >= e.getCurrentLifePercentage()) {
//                Logger.aiLog(" low life bonus!");
                dmgPercentage *= 5;
            }
            int HeroWeightedPercentage = e.isTeam2()?-1*dmgPercentage:dmgPercentage;
//            Logger.aiLog(" weighted dmg percentage:"+HeroWeightedPercentage);
            weightedPercentages += HeroWeightedPercentage;
        }
        int damageRating = weightedPercentages / 10;
//        Logger.aiLog(" sum weighted dmg percentage:"+weightedPercentages);
//        Logger.aiLog(" dmgRating:"+damageRating);
        return damageRating;
    }
    private int getHealRating(Skill cast, Hero[] targets) {
        int weightedPercentages = 0;
        for (Hero e : targets) {
            int estimatedDamage = cast.getHealWithMulti(e);
//            Logger.aiLog(" target:"+e.getName());
            int healPercentage = e.simulateHealInPercentages(this.arena.activeHero, estimatedDamage, cast);
            if (e.getCurrentLifePercentage() < 30) {
//                Logger.aiLog(" low life bonus!");
                healPercentage *= 3;
            }

            int HeroWeightedPercentage = e.isTeam2()?healPercentage:-1*healPercentage;
//            Logger.aiLog(" weighted heal percentage:"+HeroWeightedPercentage);
            weightedPercentages += HeroWeightedPercentage;
        }
        int healRating = weightedPercentages / 10;
//        Logger.aiLog(" sum weighted heal percentage:"+weightedPercentages);
//        Logger.aiLog(" healRating:"+healRating);
        return healRating;
    }

    private int getCustomAIRating(Skill cast, Hero[] targets) {
        int result = 0;
        for (Hero target: targets) {
            result+=cast.getAIRating(target);
        }
//        Logger.aiLog(" customrating:"+result);
        return result;
    }

    private int getShieldRating(Skill cast, Hero[] targets) {
        int result = 0;
        if (cast.getShield(null) == 0) {
            return 0;
        }
        for (Hero e : targets) {
            if (e.isTeam2()) {
                result += getShieldRating(e);
            } else {
                result -= getShieldRating(e);
            }
        }
        return result;
    }

    private int getShieldRating(Hero target) {
        if (target.getCurrentLifePercentage() < 25) {
            return 3;
        }
        if (target.getCurrentLifePercentage() < 50) {
            return 2;
        }
        return 1;
    }

    private int getFaithGainRating(Skill s) {
        if (s.aiTags.contains(AiSkillTag.FAITH_GAIN)) {
            double missingFaith = 1.0 - s.hero.getResourcePercentage(Stat.CURRENT_FAITH);
            return (int)(missingFaith / 0.3);
        }
        return 0;
    }

//---------------------------------Targeting---------------------------

    private List<int[]> getPossibleTargetGroups(Skill s) {
        List<int[]> result = new ArrayList<>();
        int[] targetMatrix = s.setupTargetMatrix();
        switch (s.getTargetType()) {
            case SINGLE -> setSingleTargetGroups(s, targetMatrix, result, true);
            case SINGLE_OTHER -> setSingleTargetGroups(s, targetMatrix, result, false);
            case SELF -> result.add(new int[]{s.hero.getPosition()});
            case ALL -> setAllTargetGroups(result);
            case ALL_TARGETS -> result.add(s.possibleTargetPositions);
        }
        return result;
    }
    private void setSingleTargetGroups(Skill s, int[] targetMatrix, List<int[]> result, boolean includeSelf) {
        boolean highValues = Arrays.stream(targetMatrix).anyMatch(i -> i > 3);
        Arrays.stream(targetMatrix)
                .filter(i-> (includeSelf || s.hero.getPosition() != i) && (!highValues || i < 4))
                .forEach(i-> {
            result.add(new int[]{i});
        });
    }
    private void setAllTargetGroups(List<int[]> results) {
        results.add(this.arena.getAllLivingPositions());
    }

}
