package game.libraries;

import java.util.*;
import java.util.stream.Collectors;

import game.objects.Equipment;
import game.objects.EquipmentDTO;
import game.skills.Skill;
import utils.FileWalker;

public class EquipmentLibrary {
  private static Map<String, EquipmentDTO> equipments = new HashMap<>();

  public static void init() {
    Map<String, String> equipmentJson = FileWalker.loadJsonMap("data/objects/equipments.json");
    equipments = loadDtoMap(equipmentJson, EquipmentDTO.class);
  }

  public static Equipment getEquipmentByName(String equipmentName) {
    EquipmentDTO dto = getEquipment(equipmentName);
    if (dto != null && dto.className != null) {
      try {
        Class<?> eqClass = Class.forName(dto.className);
        Equipment equipment = (Equipment) eqClass.getDeclaredConstructor().newInstance();
        equipment.set(dto);
        return equipment;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  public static List<EquipmentDTO> getAllEquipments() {
    return new ArrayList<>(equipments.values());
  }

  public static EquipmentDTO getEquipment(String name) {
    if (equipments.containsKey(name)) {
      return equipments.get(name);
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
