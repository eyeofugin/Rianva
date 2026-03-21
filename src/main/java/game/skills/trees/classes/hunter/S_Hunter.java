package game.skills.trees.classes.hunter;

import framework.connector.ConnectionPayload;
import game.effects.EffectLibrary;
import game.effects.hero.Advantage;
import game.effects.hero.Marked;
import game.entities.Hero;
import game.skills.Skill;
import utils.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class S_Hunter extends Skill {
    int marks = 0;
    public void onMark(ConnectionPayload pl) {
        this.marks++;
    }

    public void startOfRound(ConnectionPayload pl) {
        Hero enemy = CollectionUtils.getRandom(this.hero.getEnemies());
        if (enemy != null) {
            enemy.addEffect(EffectLibrary.getEffect(Marked.class.getName(), 0,0,null), this.hero);
        }
    }

    public void startOfTurn(ConnectionPayload pl) {
        if (marks > 4) {
            this.hero.addEffect(EffectLibrary.getEffect(Advantage.class.getName(), 0,0,null), this.hero);
        }
    }

    public void castChange(ConnectionPayload pl) {
        List<Integer> enemyPositions = this.hero.getEnemies().stream().filter(e->e.hasPermanentEffect(Marked.class))
                .map(Hero::getSkillPos).toList();
        if (CollectionUtils.isNotEmpty(enemyPositions)) {
            List<Integer> targets = new java.util.ArrayList<>(Arrays.stream(this.possibleTargetPositions)
                    .boxed()
                    .toList());
            targets.addAll(enemyPositions);
            this.possibleTargetPositions = targets.stream()
                    .distinct()
                    .sorted()
                    .mapToInt(Integer::intValue)
                    .toArray();
        }
    }
}
