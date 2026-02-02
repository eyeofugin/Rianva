package game.entities;

import utils.FileWalker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HeroLibrary {

    private static Map<String, String> heroesJson;

    public static void init() {
        heroesJson = FileWalker.loadJsonMap("heroes.json");
    }

    public static Hero getHero(String name) {
        if (heroesJson != null && heroesJson.containsKey(name)) {
            HeroDTO dto = FileWalker.mapJson(HeroDTO.class, heroesJson.get(name));
            return new Hero(dto);
        }
        return null;
    }

    public static List<Hero> getAll() {
        List<Hero> heroList = new ArrayList<>();
        for (String name: heroesJson.keySet()) {
            heroList.add(getHero(name));
        }
        return heroList;
    }
}
