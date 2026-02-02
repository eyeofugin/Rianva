package game.skills.logic;


import framework.graphics.text.Color;

import java.util.List;
import java.util.Random;

public enum Stat {

    MAGIC("Magic","MAG", "{013}"),
    ATTACK("Attack", "ATK", "{014}"),
    STAMINA("Stamina", "STA", "{016}"),
    ENDURANCE("Endurance", "END", "{017}"),
    SPEED("Speed", "SPE", "{018}"),

    CRIT_CHANCE("Crit Chance", "CRI", "{001}"),
    LETHALITY("Lethality", "LET", "{001}"),

    ACCURACY("Accuracy", "ACC", "{001}"),
    EVASION("Evasion", "EVA", "{001}"),

    LIFE("Max", "LIF", "{009}"),
    LIFE_REGAIN("Regain", "LRE", "{009}"),
    CURRENT_LIFE("Life", "CLI", "{009}"),

    MANA("Max", "MAN", "{011}"),
    MANA_REGAIN("Regain", "MRE", "{011}"),
    CURRENT_MANA("Mana", "CMA", "{011}"),

    SHIELD("Shield", "SHI", "{008}"),

    NORMAL_RESIST("Normal Resist", "SHI", "{008}"),
    HEAT_RESIST("Heat Resist", "SHI", "{008}"),
    COLD_RESIST("Cold Resist", "SHI", "{008}"),
    LIGHT_RESIST("Light Resist", "SHI", "{008}"),
    DARK_RESIST("Dark Resist", "SHI", "{008}"),
    SHOCK_RESIST("Shock Resist", "SHI", "{008}"),
    MIND_RESIST("Mind Resist", "SHI", "{008}"),
    TOX_RESIST("Tox Resist", "SHI", "{008}");


//    MAX_ACTION("Max", "ACT", "{001}"),
//    CURRENT_ACTION("Action", "CAC", "{001}");


    private final String translationString;
    private final String iconKey;
    private final String colorKey;

    public static List<Stat> nonResourceStats = List.of(Stat.MAGIC,
            Stat.ATTACK, Stat.STAMINA, Stat.ENDURANCE, Stat.EVASION, Stat.CRIT_CHANCE,
            Stat.ACCURACY, Stat.LETHALITY, Stat.SPEED, Stat.HEAT_RESIST, Stat.COLD_RESIST, Stat.LIGHT_RESIST,
            Stat.DARK_RESIST, Stat.SHOCK_RESIST, Stat.MIND_RESIST, Stat.TOX_RESIST);
    public static List<Stat> maxStats = List.of(LIFE, MANA);

    Stat(String translationString, String iconKey, String colorKey) {
        this.translationString = translationString;
        this.iconKey = iconKey;
        this.colorKey = colorKey;
    }

    public String getTranslationString() {
        return translationString;
    }
    public String getIconKey() {
        return iconKey;
    }
    public String getIconString() {
        return "["+iconKey+"]";
    }
    public String getFullStringReference() {
        return colorKey + this.name() + getIconString() + Color.WHITE.getCodeString();
    }
    public String getColorKey() {
        return colorKey;
    }

    public static Stat getRdmStat() {

        Stat[] stdStats = new Stat[]{MAGIC, ATTACK, STAMINA, ENDURANCE, ACCURACY, EVASION, SPEED};
        Random random = new Random();
        int rndInt = random.nextInt(7);
        return stdStats[rndInt];
    }

    public String getReference() {
        if (maxStats.contains(this)) {
            return this.getColorKey() + "Max" + Color.WHITE.getCodeString() + this.getIconString();
        }
        return this.getIconString();
    }
}
