package utils;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.rule.RuleTargetReference;

import java.util.ArrayList;
import java.util.List;

public class TargetResolver {
    public static List<Hero> getTargets(Hero reference, Skill skill, RuleTargetReference ruleTargetReference) {
        switch (ruleTargetReference) {
            case SELF -> {
                return List.of(reference);
            }
            case TARGET -> {
                return skill.getTargets();
            }
            case ALL_ALLY -> {
                List<Hero> targetList = new ArrayList<>(reference.getAllies());
                targetList.add(reference);
                return targetList;
            }
            case ALL_OTHER_ALLY -> {
                return reference.getAllies();
            }
            case ALL_ENEMY -> {
                return reference.getEnemies();
            }
            case ALL -> {
                return reference.arena.getAllLivingEntities();
            }
        }
        return new ArrayList<>();
    }
}
