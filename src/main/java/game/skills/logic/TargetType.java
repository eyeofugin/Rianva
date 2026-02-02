package game.skills.logic;

public enum TargetType {
    SINGLE("Single"),
    SINGLE_OTHER("Other"),
    ALL("All"),
    ALL_OTHER_ALLY("Other Allies"),
    ALL_TARGETS("All Targets"),
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
