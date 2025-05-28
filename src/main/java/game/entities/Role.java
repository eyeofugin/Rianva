package game.entities;

public enum Role {
    TANK("Tank", 3, "TNK"),
    FIGHTER("Fighter", 2, "FGH"),
    DPS("Dps", 1, "DPS"),
    SUPPORT("Supporter", 0, "SUP"),
    NONE("None", 4, "???");

    public String translation;
    public int id;
    public String iconKey;
    Role(String translation, int id, String iconKey) {
        this.translation = translation;
        this.id = id;
        this.iconKey = iconKey;
    }

}
