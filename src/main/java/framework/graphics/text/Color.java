package framework.graphics.text;

public enum Color {
  BLACK(0, "000"),
  WHITE(-1, "001"),
  DARKGREY(7829367, "002"),
  GREEN(9104670, "003"),
  RED(-65536, "004"),
  VOID(-12450784, "005"),
  MEDIUMGREEN(1283100, "006"),
  DARKYELLOW(12558336, "007"),
  LIGHTYELLOW(15204096, "008"),
  DARKGREEN(18184, "009"),
  BROWN(10839079, "010"),
  BLUE(9215, "011"),
  DARKRED(6816256, "012"),
  MAGIC_BLUE(5072308, "013"),
  FORCE_RED(15219515, "014"),
  FINESSE_ORANGE(16476957, "015"),
  STAMINA_RED(15219515, "016"),
  ENDURANCE_BLUE(5072308, "017"),
  SPEED_YELLOW(16514950, "018"),
  DEBUFF_RED(4653065, "019"),
  BUFF_GREEN(83712, "020"),
  STATUS_YELLOW(4669952, "021");

  public final int VALUE;
  public final String CODE;

  Color(int value, String code) {
    this.VALUE = value;
    CODE = code;
  }

  public static Color fromCode(String code) {
    for (Color color : Color.values()) {
      if (color.CODE.equals(code)) {
        return color;
      }
    }
    return VOID;
  }

  public String getCodeString() {
    return "{" + this.CODE + "}";
  }
}
