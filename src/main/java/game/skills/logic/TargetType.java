package game.skills.logic;

public enum TargetType {
    SINGLE("Single"),
    SINGLE_OTHER("Other"),
    ALL("All"),
    ALL_ENEMY("Enemies"),
    ALL_ALLY("Allies"),
    ALL_OTHER_ALLY("Other Allies"),
    ARENA("Arena"),
    SELF("Self");

    private String translation;

    TargetType(String value) {
        this.translation = value;
    }
    public String getTranslation() {
        return translation;
    }
}
