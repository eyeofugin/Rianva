package game.entities;

//import game.entities.goons.axedude.AxeDude;
//import game.entities.goons.bowdude.BowDude;
//import game.entities.goons.sworddude.SwordDude;
//import game.entities.heroes.angelguy.H_AngelGuy;
//import game.entities.heroes.battleaxe.H_BattleAxe;
//import game.entities.heroes.burner.H_Burner;
//import game.entities.heroes.cryobrawler.H_CryoBrawler;
//import game.entities.heroes.darkmage.H_DarkMage;
//import game.entities.heroes.dev.dummy.DUMMY;
//import game.entities.heroes.divinemage.H_DivineMage;
//import game.entities.heroes.dragonbreather.H_DragonBreather;
//import game.entities.heroes.dualpistol.H_DualPistol;
//import game.entities.heroes.duelist.H_Duelist;
//import game.entities.heroes.eldritchguy.H_EldritchGuy;
//import game.entities.heroes.firedancer.H_FireDancer;
//import game.entities.heroes.longsword.H_Longsword;
//import game.entities.heroes.paladin.H_Paladin;
//import game.entities.heroes.phoenixguy.H_Phoenixguy;
//import game.entities.heroes.rifle.H_Rifle;
//import game.entities.heroes.sniper.H_Sniper;
//import game.entities.heroes.thehealer.H_TheHealer;
//import game.entities.heroes.thewizard.H_TheWizard;
import game.objects.Equipment;
import game.objects.equipments.*;
import game.skills.Skill;
import game.skills.genericskills.S_Skip;
import utils.FileWalker;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class DraftBuilder {

    private static final Map<Integer, List<Integer>> dungeonEncounterLevelDistribution = initMap();
    public static List<Class<? extends Hero>> all = List.of(
//            H_AngelGuy.class,
//            H_BattleAxe.class,
//            H_Burner.class,
//            H_CryoBrawler.class,
//            H_DarkMage.class,
//            H_DivineMage.class,
//            H_DragonBreather.class,
//            H_DualPistol.class,
//            H_Duelist.class,
//            H_EldritchGuy.class,
//            H_FireDancer.class,
//            H_Longsword.class,
//            H_Paladin.class,
//            H_Phoenixguy.class,
//            H_Rifle.class,
//            H_Sniper.class,
//            H_TheHealer.class,
//            H_TheWizard.class
    );
    public static List<Class<? extends Hero>> pos0List = List.of(
//            H_Burner.class,
//            H_DarkMage.class,
//            H_DivineMage.class,
//            H_Sniper.class,
//            H_TheHealer.class
    );
    public static List<Class<? extends Hero>> pos1List = List.of(
//            H_TheWizard.class,
//            H_Rifle.class,
//            H_DualPistol.class
    );
    public static List<Class<? extends Hero>> pos2List = List.of(
//            H_AngelGuy.class,
//            H_Duelist.class,
//            H_FireDancer.class,
//            H_Phoenixguy.class,
//            H_Longsword.class
    );
    public static List<Class<? extends Hero>> pos3List = List.of(
//            H_BattleAxe.class,
//            H_CryoBrawler.class,
//            H_DragonBreather.class,
//            H_EldritchGuy.class,
//            H_Paladin.class
    );

    private static Map<Integer, List<Integer>> initMap() {
        Map<Integer, List<Integer>> map = new HashMap<>();
        List<Integer> level1 = List.of(70, 30,  0,  0,  0);
        List<Integer> level2 = List.of(20, 50, 30,  0,  0);
        List<Integer> level3 = List.of( 0, 20, 50, 30,  0);
        List<Integer> level4 = List.of( 0,  0, 20, 50, 30);
        List<Integer> level5 = List.of( 0,  0,  0, 20, 80);
        map.put(1, level1);
        map.put(2, level2);
        map.put(3, level3);
        map.put(4, level4);
        map.put(5, level5);
        return map;
    }

    public static List<Class<? extends Hero>> getPos0List() {
        return new ArrayList<>(pos0List);
    }
    public static List<Class<? extends Hero>> getPos1List() {
        return new ArrayList<>(pos1List);
    }
    public static List<Class<? extends Hero>> getPos2List() {
        return new ArrayList<>(pos2List);
    }
    public static List<Class<? extends Hero>> getPos3List() {
        return new ArrayList<>(pos3List);
    }
    public static List<Class<? extends Hero>> getAllList() {
        return new ArrayList<>();
    }
    public static List<Class<? extends Equipment>> getAllItems() {
        return new ArrayList<>(List.of(
//                ArcaneCloak.class,
//                Arkenwand.class,
//                BastardSword.class,
//                BlueOrb.class,
////                ButchersCleaver.class,
//                CrownOfLife.class,
//                DefensiveAura.class,
//                FlamingChestplate.class,
//                FlamingSword.class,
//                GraftedExoskeleton.class,
//                JewelOfLife.class,
//                PocketDarkness.class,
//                RedOrb.class,
//                ScepterOfTheGods.class,
//                ShiningArmor.class,
//                SnipersTunika.class,
////                WingedBoots.class,
//                WinterOrb.class
        ));
    }
    public static Hero[] getTestTeam(int index) {
        Hero[] heroes = new Hero[4];
        heroes[0] = getPos0Hero(index);
        heroes[1] = getPos1Hero(index);
        heroes[2] = getPos2Hero(index);
        heroes[3] = getPos3Hero(index);
        return heroes;
    }
    public static Hero getPos0Hero(int index) {
        if (index > pos0List.size()-1) {
            return null;//return new H_TheHealer();
        }
        return getFromClass(pos0List.get(index));
    }
    public static Hero getPos1Hero(int index) {
        if (index > pos1List.size()-1) {
            return null;//return new H_DualPistol();
        }
        return getFromClass(pos1List.get(index));
    }
    public static Hero getPos2Hero(int index) {
        if (index > pos2List.size()-1) {
            return null;//return new H_Duelist();
        }
        return getFromClass(pos2List.get(index));
    }
    public static Hero getPos3Hero(int index) {
        if (index > pos3List.size()-1) {
            return null;//return new H_CryoBrawler();
        }
        return getFromClass(pos3List.get(index));
    }

    public static Hero getRdmPos0Hero(List<Hero> exclusions) {
        return getHeroFromClassList(getPos0List(), exclusions);
    }
    public static Hero getRdmPos1Hero(List<Hero> exclusions) {
        return getHeroFromClassList(getPos1List(), exclusions);
    }
    public static Hero getRdmPos2Hero(List<Hero> exclusions) {
        return getHeroFromClassList(getPos2List(), exclusions);
    }
    public static Hero getRdmPos3Hero(List<Hero> exclusions) {
        return getHeroFromClassList(getPos3List(), exclusions);
    }
    public static Hero getRdmFlexHero(List<Hero> exclusions) {
        return getHeroFromClassList(getAllList(), exclusions);
    }
    public static Hero getFromClass(Class<? extends Hero> heroClass) {
        try {
            Hero hero = heroClass.getDeclaredConstructor(new Class[0]).newInstance();
            hero.resetResources();
            return hero;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Skill getFromClass(Class<? extends Skill> skillClass, Hero hero) {
        try {
            return skillClass.getDeclaredConstructor(new Class[]{Hero.class}).newInstance(hero);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Hero getHeroFromClassList(List<Class<? extends Hero>> availClasses, List<Hero> exclusions) {

        exclusions.stream().map(Hero::getClass).forEach(c -> availClasses.removeIf(hc -> hc == c));
        if (availClasses.isEmpty()) {
            return null;
        }
        Class<? extends Hero> heroClass = availClasses.get(new Random().nextInt(availClasses.size()));
        return getFromClass(heroClass);
    }
    public static HeroTeam getRandomTeam(int fillUpDirection, int teamNumber) {

        List<Hero> exclusions = new ArrayList<>();
        Hero[] heroes = new Hero[4];
        heroes[0] = getRdmPos0Hero(exclusions);
        exclusions.add(heroes[0]);
        heroes[1] = getRdmPos1Hero(exclusions);
        exclusions.add(heroes[1]);
        heroes[2] = getRdmPos2Hero(exclusions);
        exclusions.add(heroes[2]);
        heroes[3] = getRdmPos3Hero(exclusions);
        return new HeroTeam(fillUpDirection, heroes, teamNumber);
    }

    public static List<Hero> getDungeonDraft() {
        List<Hero> draftOptions = new ArrayList<>();
        List<Hero> drafted = new ArrayList<>();
        draftOptions.add(getRdmPos0Hero(drafted));drafted.add(draftOptions.get(draftOptions.size()-1));
        draftOptions.add(getRdmPos1Hero(drafted));drafted.add(draftOptions.get(draftOptions.size()-1));
        draftOptions.add(getRdmPos2Hero(drafted));drafted.add(draftOptions.get(draftOptions.size()-1));
        draftOptions.add(getRdmPos3Hero(drafted));drafted.add(draftOptions.get(draftOptions.size()-1));
        draftOptions.add(getRdmFlexHero(drafted));drafted.add(draftOptions.get(draftOptions.size()-1));
        draftOptions.add(getRdmFlexHero(drafted));drafted.add(draftOptions.get(draftOptions.size()-1));
        draftOptions.add(getRdmFlexHero(drafted));drafted.add(draftOptions.get(draftOptions.size()-1));
        draftOptions.add(getRdmFlexHero(drafted));drafted.add(draftOptions.get(draftOptions.size()-1));
        return draftOptions;
    }

    public static List<Hero> getAllHeroes() {
        List<Hero> draftOptions = new ArrayList<>();
        for (Class<? extends Hero> clazz : getAllList()) {
            draftOptions.add(getFromClass(clazz));
        }
        return draftOptions;
    }

    public static List<Hero> getDraftFromPresets() {
        List<Hero> draftOptions = new ArrayList<>();
        List<String> drafted = new ArrayList<>();
        Map<String, List<DraftEntityDTO>> container = FileWalker.getDraftSet("presets/standard.json");
        if (container == null) { return new ArrayList<>(); }
        draftOptions.add(getRandFromPreset(container, "3", drafted));
        draftOptions.add(getRandFromPreset(container, "2", drafted));
        draftOptions.add(getRandFromPreset(container, "1", drafted));
        draftOptions.add(getRandFromPreset(container, "0", drafted));
        List<DraftEntityDTO> remaining = container.values().stream().flatMap(List::stream).toList();
        for (int i = 0; i < 4; i++) {
            draftOptions.add(getRandFromList(remaining, drafted));
        }
        return draftOptions;
    }

    private static Hero getRandFromList(List<DraftEntityDTO> list, List<String> drafted) {
        Random rand = new Random();
        List<DraftEntityDTO> filtered = list.stream().filter(e->!drafted.contains(e.id)).toList();
        DraftEntityDTO entity = filtered.get(rand.nextInt(filtered.size()));
        drafted.add(entity.id);
        return draftEntityToHero(entity);
    }
    private static Hero getRandFromPreset(Map<String, List<DraftEntityDTO>> container, String role,  List<String> drafted) {
        return getRandFromList(new ArrayList<>(container.get(role)), drafted);
    }
    public static Hero buildHeroFromPreset(String preset, String role, int nr) {
        Map<String, List<DraftEntityDTO>> container = FileWalker.getDraftSet(preset);
        if (container == null || container.get(role) == null) { return null; }
        DraftEntityDTO entity = container.get(role).get(nr);
        return draftEntityToHero(entity);
    }

    private static Hero draftEntityToHero(DraftEntityDTO entity) {
        if (entity == null) { return null; }
        Hero hero = getHero(entity.name);
        if (hero == null) { return null; }
        Skill primary = getSkill(entity.primary, hero);
        Skill tactical1 = getSkill(entity.tactical[0], hero);
        Skill tactical2 = getSkill(entity.tactical[1], hero);
        Skill ultimate = getSkill(entity.ultimate, hero);
        hero.getSkills().addAll(List.of(primary, tactical1, tactical2, ultimate, new S_Skip(hero)));
        hero.equipments.add(getEquipment(entity.equipments[0]));
        hero.equipments.add(getEquipment(entity.equipments[1]));
        hero.equipments.add(getEquipment(entity.equipments[2]));
        return hero;
    }

    private static Skill getSkill(String name, Hero hero) {
        try {
            Class<?> skillClass = Class.forName(name);
            Object obj = skillClass.getDeclaredConstructor(new Class[]{Hero.class}).newInstance(hero);
            return (Skill) obj;
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            return null;
        }
    }
    private static Equipment getEquipment(String name) {
        try {
            Class<?> skillClass = Class.forName(name);
            Object obj = skillClass.getDeclaredConstructor().newInstance();
            if (obj == null) {
                return new SimpleDagger();
            }
            return (Equipment) obj;
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            return null;
        }
    }

    private static Hero getHero(String name) {
        try {
            Class<?> skillClass = Class.forName(name);
            Object obj = skillClass.getDeclaredConstructor().newInstance();
            return (Hero) obj;
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            return null;
        }
    }

    public static Hero[] getDummyTeam() {
//        return new Hero[]{new DUMMY(1), new DUMMY(2), new DUMMY(3)};
        return null;
    }
    public static Hero[] getDungeonEncounterTeam(int level) {
        Hero[] heroes = new Hero[3];
//        heroes[0] = new BowDude();
//        heroes[1] = new SwordDude();
//        heroes[2] = new AxeDude();
        return heroes;
    }
}
