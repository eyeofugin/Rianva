package game.libraries;

import game.entities.Hero;
import game.entities.HeroDTO;
import utils.FileWalker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeroLibrary {

  private static Map<String, String> heroesJson = new HashMap<>();

  public static void init() {
    heroesJson = FileWalker.loadJsonMap("data/heroes.json");
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
    for (String name : heroesJson.keySet()) {
      heroList.add(getHero(name));
    }
    return heroList;
  }
}
