package game.skills;

import game.entities.HeroDTO;
import game.skills.logic.Condition;
import game.skills.logic.Effect;
import game.skills.logic.EffectDTO;
import utils.FileWalker;

import java.util.Map;

public class EffectLibrary {
    private static Map<String, String> effectJson;

    public static void init() {
        effectJson = FileWalker.loadJsonMap("effects.json");
    }

    public static Effect getEffect(String name, int stacks, int turns, Condition condition) {
        if (effectJson != null && effectJson.containsKey(name)) {
            EffectDTO dto = FileWalker.mapJson(EffectDTO.class,effectJson.get(name));
            Effect effect = new Effect(dto);
            effect.turns = turns;
            effect.stacks = stacks;
            effect.condition = condition;
            return effect;
        }
        return null;
    }
}
