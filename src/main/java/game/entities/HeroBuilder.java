package game.entities;

import framework.Logger;
import game.entities.classes.HeroClassDTO;
import game.entities.races.HeroRaceDTO;
import game.entities.roles.HeroRoleDTO;
import game.libraries.EquipmentLibrary;
import game.libraries.HeroBackgroundLibrary;
import game.objects.Equipment;
import game.objects.EquipmentDTO;
import game.skills.Skill;

import java.util.*;

public class HeroBuilder {

    private static List<String> races = List.of("Human", "Orc", "Goblin", "Elf", "Troll", "Spirit", "Undead", "Demon", "Dragonborn", "Dwarf");
    private static List<String> classes = List.of("Barbarian", "Hunter", "Strider", "Evoker", "Vengeance", "Fauna", "Flora", "Medic", "Radiant", "Rogue", "Trickster");
    private static List<String> roles = List.of("Hunger", "Inspiration", "Resolve", "Tempo");
    private static List<String> weapons = List.of("MundaneSword", "MundaneDagger", "MundaneBow", "MundaneHammer", "MundaneRing");

    private static Map<String, List<String>> classExclusions = new HashMap<>();
    private static Map<String, List<String>> rolePrerequisites = new HashMap<>();
    private static Map<String, List<String>> weaponPrerequisites = new HashMap<>();
    private static Map<String, List<String>> weaponExclusions = new HashMap<>();


    public static void init() {
        classExclusions.put("Demon", List.of("Radiant"));
        classExclusions.put("Orc", List.of("Strider", "Rogue", "Trickster"));
        classExclusions.put("Troll", List.of("Hunter", "Strider", "Evoker", "Flora", "Medic", "Radiant", "Rogue", "Trickster"));
        classExclusions.put("Goblin", List.of("Barbarian", "Vengeance"));
        classExclusions.put("Elf", List.of("Barbarian", "Vengeance"));

        rolePrerequisites.put("Hunger", List.of("Barbarian", "Hunter", "Strider", "Vengeance", "Fauna", "Rogue", "Trickster"));
        rolePrerequisites.put("Inspiration", List.of("Evoker", "Fauna", "Radiant", "Flora", "Trickster"));
        rolePrerequisites.put("Resolve", List.of("Barbarian", "Hunter", "Strider", "Vengeance", "Fauna", "Flora", "Radiant"));
        rolePrerequisites.put("Tempo", List.of("Hunter", "Strider", "Evoker", "Fauna", "Flora", "Medic", "Radiant", "Rogue", "Trickster"));

        weaponPrerequisites.put("MundaneSword",List.of("Barbarian", "Hunter", "Strider", "Vengeance", "Fauna"));
        weaponPrerequisites.put("MundaneBow",List.of("Hunter", "Strider", "Medic", "Rogue", "Trickster"));
        weaponPrerequisites.put("MundaneDagger",List.of("Vengeance", "Medic", "Rogue", "Fauna", "Trickster"));
        weaponPrerequisites.put("MundaneHammer",List.of("Barbarian", "Vengeance", "Fauna", "Radiant"));
        weaponPrerequisites.put("MundaneRing",List.of("Evoker", "Fauna", "Flora", "Radiant", "Trickster"));

        weaponExclusions.put("MundaneDagger", List.of("Resolve"));
        weaponExclusions.put("MundaneBow", List.of("Resolve"));
        weaponExclusions.put("MundaneHammer", List.of(""));
        weaponExclusions.put("MundaneRing", List.of(""));
        weaponExclusions.put("MundaneSword", List.of(""));

    }

    public static void buildRandom() {
        String raceName = "";
        String weaponName = "";
        Random random = new Random();
        raceName = races.get(random.nextInt(races.size()));
        List<String> raceExclusions = classExclusions.get(raceName);
        List<String> classChoices;
        if (raceExclusions != null) {
            classChoices = classes.stream().filter(s-> !raceExclusions.contains(s)).toList();
        } else {
            classChoices = classes;
        }
        final String className = classChoices.get(random.nextInt(classChoices.size()));
        List<String> roleChoices = rolePrerequisites.keySet().stream().filter(s->rolePrerequisites.get(s).contains(className)).toList();
        final String roleName = roleChoices.get(random.nextInt(roleChoices.size()));

        List<String> weaponChoices = weaponPrerequisites.keySet().stream()
                .filter(s-> weaponPrerequisites.get(s).contains(className) && !weaponExclusions.get(s).contains(roleName)).toList();
        weaponName = weaponChoices.get(random.nextInt(weaponChoices.size()));

        Logger.logLn(raceName + "|" + className + "|" + roleName + "|" + weaponName);

        HeroRaceDTO raceDTO = HeroBackgroundLibrary.getHeroRace(raceName);
        HeroClassDTO classDTO = HeroBackgroundLibrary.getHeroClass(className);
        HeroRoleDTO roleDTO = HeroBackgroundLibrary.getHeroRole(roleName);
        EquipmentDTO weaponDto = EquipmentLibrary.getEquipment(weaponName);


        List<String> skillNames = new ArrayList<>();
        skillNames.addAll(classDTO.learnableSkills);
        skillNames.addAll(raceDTO.learnableSkills);
        skillNames.addAll(roleDTO.learnableSkills);
        skillNames.addAll(weaponDto.learnableSkills);
        for (String skillName : skillNames) {
            Logger.logLn(skillName);
        }

        Equipment equipment = new Equipment();
        equipment.set(weaponDto);

        Hero hero = new Hero(raceDTO, classDTO, roleDTO);
        hero.equip(equipment);
//        hero.buildSkillFromPresets();
    }

}
