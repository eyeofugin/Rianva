package game.libraries;

import game.entities.classes.HeroClassDTO;
import game.entities.races.HeroRaceDTO;
import game.entities.roles.HeroRoleDTO;
import utils.FileWalker;

import java.util.*;
import java.util.stream.Collectors;

public class HeroBackgroundLibrary {
  private static Map<String, HeroClassDTO> heroClasses = new HashMap<>();
  private static Map<String, HeroRoleDTO> heroRoles = new HashMap<>();
  private static Map<String, HeroRaceDTO> heroRaces = new HashMap<>();

  public static void init() {
    Map<String, String> heroClassJson = FileWalker.loadJsonMap("data/backgrounds/classes.json");
    Map<String, String> heroRaceJson = FileWalker.loadJsonMap("data/backgrounds/races.json");
    Map<String, String> heroRoleJson = FileWalker.loadJsonMap("data/backgrounds/roles.json");
    heroClasses = loadDtoMap(heroClassJson, HeroClassDTO.class);
    heroRoles = loadDtoMap(heroRoleJson, HeroRoleDTO.class);
    heroRaces = loadDtoMap(heroRaceJson, HeroRaceDTO.class);
  }

  public static List<HeroClassDTO> getAllClasses() {
    return new ArrayList<>(heroClasses.values());
  }

  public static List<HeroRaceDTO> getAllRaces() {
    return new ArrayList<>(heroRaces.values());
  }

  public static List<HeroRoleDTO> getAllRoles() {
    return new ArrayList<>(heroRoles.values());
  }

  public static HeroRaceDTO getHeroRace(String name) {
    if (heroRaces.containsKey(name)) {
      return heroRaces.get(name);
    }
    return null;
  }

  public static HeroClassDTO getHeroClass(String name) {
    if (heroClasses.containsKey(name)) {
      return heroClasses.get(name);
    }
    return null;
  }

  public static HeroRoleDTO getHeroRole(String name) {
    if (heroRoles.containsKey(name)) {
      return heroRoles.get(name);
    }
    return null;
  }

  private static <T> Map<String, T> loadDtoMap(Map<String, String> json, Class<T> clazz) {
    return json.entrySet().stream()
        .collect(
            Collectors.toMap(
                Map.Entry::getKey,
                e -> {
                  T dto = createDTO(e.getValue(), clazz);
                  return Objects.requireNonNullElseGet(dto, null);
                }));
  }

  public static <T> T createDTO(String value, Class<T> clazz) {
    return FileWalker.mapJson(clazz, value);
  }
}
