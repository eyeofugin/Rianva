package framework.graphics.text;

import framework.Logger;
import framework.Property;
import game.entities.Role;
import game.skills.logic.Stat;

import java.util.*;

public class TextEditor {

  public static int BUTTON_PADDING = 2;
  public static int BASE_CHAR_DISTANCE = 1;

  public static String ACTION_KEY = "ACT";
  public static String TURN_KEY = "TRN";
  public static String TURN_CD_KEY = "TCD";

  public int charWidth, charHeight, smallNumHeight, smallNumWidth;
  public boolean hasLowerCase = true;
  public static TextEditorConfig conf5x8 = new TextEditorConfig(5, 8, 3, 5);
  private final HashMap<String, int[]> smallNumeric;
  private final HashMap<String, Symbol> symbolMap;
  private final HashMap<String, String> iconCodes = new HashMap<>();
  private static final HashMap<String, String> staticIconCodes = new HashMap<>();

  public static TextEditorConfig baseConf = TextEditor.conf5x8;

  public TextEditor(TextEditorConfig conf) {
    this.charWidth = conf.getCharWidth();
    this.charHeight = conf.getCharHeight();
    this.smallNumHeight = conf.getSmallNumHeight();
    this.smallNumWidth = conf.getSmallNumWidth();
    this.symbolMap = fillSymbolMap();
    this.smallNumeric = fillSmallNumeric();
  }

  public String getIconCode(String key) {
    return iconCodes.get(key);
  }

  public static String getStaticIconCode(String key, boolean withBrackets) {
    String result = "";
    result = staticIconCodes.get(key);
    if (withBrackets) {
      return "[" + result + "]";
    } else {
      return result;
    }
  }

  private HashMap<String, int[]> fillSmallNumeric() {
    HashMap<String, int[]> symbols = new HashMap<String, int[]>();
    symbols.put("1", Symbol.smallNum1.pixels);
    symbols.put("2", Symbol.smallNum2.pixels);
    symbols.put("3", Symbol.smallNum3.pixels);
    symbols.put("4", Symbol.smallNum4.pixels);
    symbols.put("5", Symbol.smallNum5.pixels);
    symbols.put("6", Symbol.smallNum6.pixels);
    symbols.put("7", Symbol.smallNum7.pixels);
    symbols.put("8", Symbol.smallNum8.pixels);
    symbols.put("9", Symbol.smallNum9.pixels);
    symbols.put("0", Symbol.smallNum0.pixels);
    symbols.put("/", Symbol.smallNumSlash.pixels);
    symbols.put("(", Symbol.smallNumBracketOpen.pixels);
    symbols.put(")", Symbol.smallNumBracketClose.pixels);
    symbols.put("+", Symbol.smallNumPlus.pixels);

    return symbols;
  }

  private HashMap<String, Symbol> fillSymbolMap() {
    HashMap<String, Symbol> symbols = new HashMap<>();

    symbols.put("1", Symbol.onex8);
    symbols.put("2", Symbol.twox8);
    symbols.put("3", Symbol.threex8);
    symbols.put("4", Symbol.fourx8);
    symbols.put("5", Symbol.fivex8);
    symbols.put("6", Symbol.sixx8);
    symbols.put("7", Symbol.sevenx8);
    symbols.put("8", Symbol.eightx8);
    symbols.put("9", Symbol.ninex8);
    symbols.put("0", Symbol.zerox8);
    symbols.put("a", Symbol.ax8);
    symbols.put("b", Symbol.bx8);
    symbols.put("c", Symbol.cx8);
    symbols.put("d", Symbol.dx8);
    symbols.put("e", Symbol.ex8);
    symbols.put("f", Symbol.fx8);
    symbols.put("g", Symbol.gx8);
    symbols.put("h", Symbol.hx8);
    symbols.put("i", Symbol.ix8);
    symbols.put("j", Symbol.jx8);
    symbols.put("k", Symbol.kx8);
    symbols.put("l", Symbol.lx8);
    symbols.put("m", Symbol.mx8);
    symbols.put("n", Symbol.nx8);
    symbols.put("o", Symbol.ox8);
    symbols.put("p", Symbol.px8);
    symbols.put("q", Symbol.qx8);
    symbols.put("r", Symbol.rx8);
    symbols.put("s", Symbol.sx8);
    symbols.put("t", Symbol.tx8);
    symbols.put("u", Symbol.ux8);
    symbols.put("v", Symbol.vx8);
    symbols.put("w", Symbol.wx8);
    symbols.put("x", Symbol.xx8);
    symbols.put("y", Symbol.yx8);
    symbols.put("z", Symbol.zx8);
    symbols.put("A", Symbol.Ax8);
    symbols.put("B", Symbol.Bx8);
    symbols.put("C", Symbol.Cx8);
    symbols.put("D", Symbol.Dx8);
    symbols.put("E", Symbol.Ex8);
    symbols.put("F", Symbol.Fx8);
    symbols.put("G", Symbol.Gx8);
    symbols.put("H", Symbol.Hx8);
    symbols.put("I", Symbol.Ix8);
    symbols.put("J", Symbol.Jx8);
    symbols.put("K", Symbol.Kx8);
    symbols.put("L", Symbol.Lx8);
    symbols.put("M", Symbol.Mx8);
    symbols.put("N", Symbol.Nx8);
    symbols.put("O", Symbol.Ox8);
    symbols.put("P", Symbol.Px8);
    symbols.put("Q", Symbol.Qx8);
    symbols.put("R", Symbol.Rx8);
    symbols.put("S", Symbol.Sx8);
    symbols.put("T", Symbol.Tx8);
    symbols.put("U", Symbol.Ux8);
    symbols.put("V", Symbol.Vx8);
    symbols.put("W", Symbol.Wx8);
    symbols.put("X", Symbol.Xx8);
    symbols.put("Y", Symbol.Yx8);
    symbols.put("Z", Symbol.Zx8);
    symbols.put(".", Symbol.pointx8);
    symbols.put("*", Symbol.starx8);
    symbols.put(",", Symbol.commax8);
    symbols.put("'", Symbol.apostrophex8);
    symbols.put("/", Symbol.slashx8);
    symbols.put("(", Symbol.bracketopenx8);
    symbols.put(")", Symbol.bracketclosex8);
    symbols.put("+", Symbol.plusx8);
    symbols.put(":", Symbol.colonx8);
    symbols.put(";", Symbol.semicolonx8);
    symbols.put(">", Symbol.moreThanx8);
    symbols.put("<", Symbol.lessThanx8);
    symbols.put("-", Symbol.minusx8);
    symbols.put("=", Symbol.equalsx8);
    symbols.put("%", Symbol.percentagex8);
    symbols.put(" ", Symbol.spacex8);
    symbols.put("~", Symbol.infinitex8);
    symbols.put("!", Symbol.exclamationMarkx8);
    symbols.put("?", Symbol.questionMarkx8);
    symbols.put(Stat.CURRENT_ENERGY.getIconKey(), Symbol.mana);
    symbols.put(Stat.ENERGY.getIconKey(), Symbol.mana);
    symbols.put(Stat.ENERGY_REGAIN.getIconKey(), Symbol.manaregain);
    symbols.put(TURN_KEY, Symbol.turn);
    symbols.put(TURN_CD_KEY, Symbol.turnCD);
    symbols.put(Stat.VITALITY.getIconKey(), Symbol.life);
    symbols.put(Stat.CURRENT_LIFE.getIconKey(), Symbol.life);
    symbols.put(Stat.LIFE_REGAIN.getIconKey(), Symbol.liferegain);
    symbols.put(Stat.MIND.getIconKey(), Symbol.magic);
    symbols.put(Stat.BODY.getIconKey(), Symbol.force);
    symbols.put(Stat.DEXTERITY.getIconKey(), Symbol.speed);
    symbols.put(Stat.SHIELD.getIconKey(), Symbol.shield);
    symbols.put(Stat.CRIT_CHANCE.getIconKey(), Symbol.critchance);
    symbols.put(Stat.DODGE.getIconKey(), Symbol.evasion);
    symbols.put(Stat.LETHALITY.getIconKey(), Symbol.lethality);
    symbols.put(Stat.ARMOR.getIconKey(), Symbol.normalRes);
    symbols.put(Stat.HEAT_RESIST.getIconKey(), Symbol.heatRes);
    symbols.put(Stat.COLD_RESIST.getIconKey(), Symbol.coldRes);
    symbols.put(Stat.LIGHT_RESIST.getIconKey(), Symbol.lightRes);
    symbols.put(Stat.DARK_RESIST.getIconKey(), Symbol.darkRes);
    symbols.put(Stat.MENTAL_RESIST.getIconKey(), Symbol.mindRes);
    symbols.put(Stat.TOX_RESIST.getIconKey(), Symbol.toxRes);
    symbols.put(Stat.SHOCK_RESIST.getIconKey(), Symbol.shockRes);
    symbols.put("FTT", Symbol.friendTarget);
    symbols.put("ETT", Symbol.enemyTarget);
    symbols.put("OTT", Symbol.otherTarget);
    symbols.put("ETA", Symbol.enemyTargetAll);
    symbols.put("FTA", Symbol.friendTargetAll);
    symbols.put("OTA", Symbol.otherTargetAll);
    symbols.put("EMT", Symbol.emptyTarget);
    symbols.put("CHN", Symbol.chancex8);
    symbols.put("MRK", Symbol.markx8);
    symbols.put("BNS", Symbol.bonusx8);
    symbols.put("BRL", new LineBreak());
    symbols.put("DMG", Symbol.damagex8);
    symbols.put("OHT", Symbol.onhitx8);
    symbols.put("???", Symbol.missingx8);

    return symbols;
  }

  public int[] getTextLine(String text, int targetWidth, int targetHeight) {

    return getTextLine(
        text, targetWidth, targetHeight, 0, TextAlignment.CENTER, Color.BLACK, Color.WHITE);
  }

  public int[] getTextLine(
      String text, int targetWidth, int targetHeight, int fontsize, TextAlignment alignment) {

    return getTextLine(
        text, targetWidth, targetHeight, fontsize, alignment, Color.BLACK, Color.WHITE);
  }

  public int[] getTextLine(String text, int targetWidth, int targetHeight, Color color) {

    return getTextLine(
        text, targetWidth, targetHeight, 0, TextAlignment.CENTER, Color.BLACK, Color.WHITE);
  }

  public int[] getSmallNumTextLine(
      String text,
      int targetWidth,
      int targetHeight,
      TextAlignment alignment,
      Color backGroundColor,
      Color fontColor) {
    if (text == null) {
      return new int[targetWidth * targetHeight];
    }
    Logger.logLn("Print:"+ text);
    int backgroundColor = backGroundColor.VALUE;
    int fontcolor = fontColor.VALUE;

    int[] result = new int[targetWidth * targetHeight];
    int textLength = getTextWidth(text);
    int resultWidth = Math.max(1, textLength * this.smallNumWidth + textLength - 1);
    if (resultWidth > targetWidth) {
      Arrays.fill(result, Color.MEDIUMGREEN.VALUE);
      return result;
    }
    int[] word = new int[this.smallNumHeight * resultWidth];

    char[] textArray = new char[text.length()];

    int widthOverhead = targetWidth - resultWidth;
    int leftOverhead = (widthOverhead / 2);
    int rightOverhead = (widthOverhead / 2) + (widthOverhead % 2);

    if (alignment == TextAlignment.LEFT) {
      leftOverhead = 0;
      rightOverhead = widthOverhead;
    }
    if (alignment == TextAlignment.RIGHT) {
      rightOverhead = 0;
      leftOverhead = widthOverhead;
    }
    Arrays.fill(result, backgroundColor);

    int lastWrittenWidth = 0;
    text.getChars(0, text.length(), textArray, 0);

    int symbolNr = 1;
    for (int charindex = 0; charindex < textArray.length; charindex++) {
      char symbol = textArray[charindex];

      int[] symbolarray = smallNumeric.get((symbol + ""));
      int index = 0;

      for (int y = 0; y < this.smallNumHeight; y++) {
        for (int x = lastWrittenWidth; x < lastWrittenWidth + this.smallNumWidth; x++) {
          word[x + y * resultWidth] = symbolarray[index];
          index++;
        }
      }

      lastWrittenWidth += this.smallNumWidth;
      if (symbolNr != text.length()) {
        for (int i = 0; i < this.smallNumHeight; i++) {
          word[lastWrittenWidth + i * resultWidth] = backgroundColor;
        }
        lastWrittenWidth += 1;
      }
      symbolNr++;
    }
    for (int y = 0; y < targetHeight; y++) {
      for (int x = leftOverhead; x < (targetWidth - rightOverhead); x++) {
        int newx = x - leftOverhead;

        result[x + y * targetWidth] = word[newx + y * resultWidth];
      }
    }
    int resultIndex = 0;

    for (int i : result) {
      if (i == 0 || i == -16777216 || i == -12450784) {
        result[resultIndex] = backgroundColor;
      } else if (i == -1 || i == -1710619) {
        result[resultIndex] = fontcolor;
      } else {
      }
      resultIndex++;
    }
    return result;
  }

  public int[] getTextLineNew(
      String text,
      int targetWidth,
      int targetHeight,
      int fontSize,
      TextAlignment alignment,
      Color backGround,
      Color font) {

    int bgValue = backGround.VALUE;

    int[] result = new int[targetWidth * targetHeight];
    Arrays.fill(result, bgValue);

    if (text == null) {
      return result;
    }

    List<Symbol> textSymbols = getSymbols(text, font, bgValue);

    int textWidth = getTextWidth(textSymbols);

    int[] rawText = getLineFromSymbols(textSymbols, textWidth, bgValue);

    int horizontalPadding = targetWidth - textWidth;
    int xStart = 0;
    if (horizontalPadding > 0 && !alignment.equals(TextAlignment.LEFT)) {
      if (alignment.equals(TextAlignment.CENTER)) {
        xStart = horizontalPadding / 2;
      } else if (alignment.equals(TextAlignment.RIGHT)) {
        xStart = horizontalPadding;
      }
    }
    int xEnd = xStart + textWidth;
    int rawTextX = 0;
    for (int x = xStart; x < xEnd && x < targetWidth; x++) {
      for (int y = 0; y < this.charHeight; y++) {
        result[x + y * targetWidth] = rawText[rawTextX + y * textWidth];
      }
      rawTextX++;
    }
    return result;
  }

  public int[] getLineFromSymbols(List<Symbol> textSymbols, int textWidth, int bgValue) {
    int[] rawText = new int[this.charHeight * textWidth];
    int writePointer = 0;
    int symbolCounter = 1;
    for (Symbol symbol : textSymbols) {
      int index = 0;
      for (int y = 0; y < this.charHeight; y++) {
        for (int x = writePointer; x < writePointer + symbol.WIDTH; x++) {
          int colorVal = symbol.pixels[index++];
          if (symbol.primaryColor != null) {
            colorVal = colorVal == Color.WHITE.VALUE ? symbol.primaryColor : colorVal;
          }
          if (symbol.secondaryColor != null) {
            colorVal = colorVal == Color.BLACK.VALUE ? symbol.secondaryColor : colorVal;
          }
          rawText[x + y * textWidth] = colorVal;
        }
      }
      writePointer += symbol.WIDTH;

      if (symbolCounter != textSymbols.size()) {
        for (int i = 0; i < this.charHeight; i++) {
          rawText[writePointer + i * textWidth] = bgValue;
        }
        writePointer++;
        symbolCounter++;
      }
    }

    return rawText;
  }

  public List<Symbol> getSymbols(String text, Color font, int bgValue) {

    List<Symbol> textSymbols = new ArrayList<>();
    char[] textArray = new char[text.length()];
    text.getChars(0, text.length(), textArray, 0);
    int fontValue = font.VALUE;

    for (int charIndex = 0; charIndex < textArray.length; charIndex++) {
      char character = textArray[charIndex];
      if (character == '{') {
        fontValue = getColor(charIndex, textArray, font).VALUE;
        charIndex += 4;
      } else {
        Symbol symbol;
        if (character == '[') {
          symbol = getSpecialCharacter(charIndex, textArray);
          charIndex += 4;
        } else {
          Symbol _sym = this.symbolMap.get(String.valueOf(character));
          if (_sym == null) {
            System.out.println(character);
            character = '*';
          }
          symbol = this.symbolMap.get(String.valueOf(character)).copy();
        }
        if (symbol != null) {
          symbol.primaryColor = fontValue;
          symbol.secondaryColor = bgValue;
          textSymbols.add(symbol);
        } else {
          textSymbols.add(this.symbolMap.get(" ").copy());
        }
      }
    }
    return textSymbols;
  }

  public int getTextWidth(List<Symbol> symbols) {
    int textWidth = 0;
    for (Symbol symbol : symbols) {
      textWidth += symbol.WIDTH;
    }
    textWidth += symbols.size() - 1;
    return Math.max(textWidth, 0);
  }

  public int[] getTextLine(
      String text,
      int targetWidth,
      int targetHeight,
      int fontSize,
      TextAlignment alignment,
      Color backGroundColor,
      Color fontColor) {

    if (text == null) {
      return new int[targetWidth * targetHeight];
    }
    int backgroundColor = backGroundColor.VALUE;
    int fontColorValue = fontColor.VALUE;

    int[] result = new int[targetWidth * targetHeight];
    int textLength = getTextWidth(text);
    int resultWidth = Math.max(1, textLength * this.charWidth + textLength - 1);
    int[] word = new int[this.charHeight * resultWidth];

    int multiplier;
    int xmultiplier = targetWidth / resultWidth;
    int ymultiplier = targetHeight / this.charHeight;
    multiplier = Math.min(xmultiplier, ymultiplier);

    if (multiplier > fontSize && fontSize > 0) {
      multiplier = fontSize;
    }

    char[] textArray = new char[text.length()];

    int heightOverhead = targetHeight - (int) (this.charHeight * multiplier);
    int topOverhead = (heightOverhead / 2) + (heightOverhead % 2);
    int bottomOverhead = heightOverhead / 2;

    int widthOverhead = targetWidth - (int) (resultWidth * multiplier);
    int leftOverhead = (widthOverhead / 2);
    int rightOverhead = (widthOverhead / 2) + (widthOverhead % 2);
    if (text.contains("Level")) {}

    if (alignment == TextAlignment.LEFT) {
      leftOverhead = 1;
      rightOverhead = widthOverhead - 1;
    }
    if (alignment == TextAlignment.RIGHT) {
      rightOverhead = 1;
      leftOverhead = widthOverhead - 1;
    }
    if (alignment == TextAlignment.SOFT_LEFT) {
      leftOverhead = 5;
      rightOverhead = widthOverhead - 5;
    }
    if (alignment == TextAlignment.SOFT_RIGHT) {
      rightOverhead = 5;
      leftOverhead = widthOverhead - 5;
    }

    int lastWrittenWidth = 0;
    text.getChars(0, text.length(), textArray, 0);

    int symbolNr = 1;
    for (int charindex = 0; charindex < textArray.length; charindex++) {
      char symbol = textArray[charindex];

      if (symbol == '{') {
        fontColorValue = getColor(charindex, textArray, fontColor).VALUE;
        charindex += 4;

      } else {
        if (symbol == '[') {
          int[] symbolArray = getSpecialSymbol(charindex, textArray).pixels;
          int index = 0;
          for (int y = 0; y < this.charHeight; y++) {
            for (int x = lastWrittenWidth; x < lastWrittenWidth + this.charWidth; x++) {
              word[x + y * resultWidth] = symbolArray[index];
              index++;
            }
          }
          charindex += 4;
        } else if (!(Character.getNumericValue(symbol) == -1)) {
          String s = "";
          if (this.hasLowerCase) {
            s = String.valueOf(symbol);
          } else {
            s = String.valueOf(symbol).toLowerCase();
          }
          int[] symbolarray = getSymbol(s).pixels;

          int index = 0;

          for (int y = 0; y < this.charHeight; y++) {
            for (int x = lastWrittenWidth; x < lastWrittenWidth + this.charWidth; x++) {
              int val = symbolarray[index];
              word[x + y * resultWidth] =
                  val == -1 || val == -1710619 ? fontColorValue : backgroundColor;
              index++;
            }
          }

        } else if (Character.isDigit(symbol)) {

          int[] symbolarray = getSymbol(String.valueOf(symbol)).pixels;
          int index = 0;

          for (int y = 0; y < this.charHeight; y++) {
            for (int x = lastWrittenWidth; x < lastWrittenWidth + this.charWidth; x++) {
              int val = symbolarray[index];
              word[x + y * resultWidth] =
                  val == -1 || val == -1710619 ? fontColorValue : backgroundColor;
              index++;
            }
          }

        } else if (symbol == ' ') {
          for (int y = 0; y < this.charHeight; y++) {
            for (int x = lastWrittenWidth; x < lastWrittenWidth + this.charWidth; x++) {
              word[x + y * resultWidth] = backgroundColor;
            }
          }
        } else if (symbol == '.') {
          for (int y = 0; y < this.charHeight; y++) {
            for (int x = lastWrittenWidth; x < lastWrittenWidth + this.charWidth; x++) {
              word[x + y * resultWidth] = backgroundColor;
            }
          }
          word[(lastWrittenWidth + 2) + 6 * resultWidth] = fontColorValue;
          word[(lastWrittenWidth + 3) + 6 * resultWidth] = fontColorValue;
          word[(lastWrittenWidth + 2) + 5 * resultWidth] = fontColorValue;
          word[(lastWrittenWidth + 3) + 5 * resultWidth] = fontColorValue;
        } else {
          String symbolS = String.valueOf(symbol);
          int[] symbolarray = getSymbol(symbolS).pixels;
          int index = 0;

          for (int y = 0; y < this.charHeight; y++) {
            for (int x = lastWrittenWidth; x < lastWrittenWidth + this.charWidth; x++) {
              int val = symbolarray[index];
              word[x + y * resultWidth] =
                  val == -1 || val == -1710619 ? fontColorValue : backgroundColor;
              index++;
            }
          }
        }

        lastWrittenWidth += this.charWidth;
        if (symbolNr != textLength) {
          for (int i = 0; i < this.charHeight; i++) {
            word[lastWrittenWidth + i * resultWidth] = backgroundColor;
          }
          lastWrittenWidth += BASE_CHAR_DISTANCE;
        }
        symbolNr++;
      }
    }

    for (int y = topOverhead; y < (targetHeight - bottomOverhead); y++) {
      for (int x = leftOverhead; x < (targetWidth - rightOverhead); x++) {
        int newx = (int) ((x - leftOverhead) / multiplier);
        int newy = (int) ((y - topOverhead) / multiplier);
        result[x + y * targetWidth] = word[newx + newy * resultWidth];
      }
    }
    //        int resultIndex = 0;
    //
    //        for(int i : result) {
    //            if(i == 0 || i == -16777216 || i == -12450784) {
    //                result[resultIndex] = backgroundColor;
    //            }else if(i == -1 || i == -1710619) {
    //                result[resultIndex] = fontcolor;
    //            }else {
    //                //Logger.log(i);
    //            }
    //            resultIndex++;
    //        }
    return result;
  }

  private int getTextWidth(String text) {
    long iconCount = text.chars().filter(ch -> ch == '[').count();
    long colorCount = text.chars().filter(ch -> ch == '{').count();
    return text.length() - (int) iconCount * 4 - (int) colorCount * 5;
  }

  private Symbol getSpecialSymbol(int index, char[] textArray) {
    StringBuilder specialSymbolCode = new StringBuilder();
    for (int i = index + 1; i < index + 4; i++) {
      specialSymbolCode.append(textArray[i]);
    }
    Symbol symbol = symbolMap.get(specialSymbolCode.toString());
    if (symbol == null) {
      return Symbol.missingx8.copy();
    }
    return symbol.copy();
  }

  private Symbol getSpecialCharacter(int index, char[] textArray) {
    StringBuilder specialSymbolCode = new StringBuilder();
    for (int i = index + 1; i < index + 4; i++) {
      specialSymbolCode.append(textArray[i]);
    }
    Symbol original = symbolMap.get(specialSymbolCode.toString());
    if (original == null) {
      System.out.println("special character not found: " + specialSymbolCode);
      return null;
    }
    return original.copy();
  }

  private Color getColor(int index, char[] textArray, Color fontColor) {
    StringBuilder codeBuilder = new StringBuilder();
    for (int i = index + 1; i < index + 4; i++) {
      codeBuilder.append(textArray[i]);
    }
    String code = codeBuilder.toString();
    if (code.equals("000")) {
      return fontColor;
    }
    return Color.fromCode(code);
  }

  public int[] getButtonLine(
      String text,
      int targetWidth,
      int targetHeight,
      Color backGroundColor,
      Color fontColor,
      int buttonLine) {

    int backgroundColor = backGroundColor.VALUE;
    int fontcolor = fontColor.VALUE;
    int[] button = new int[targetWidth * targetHeight];

    int labelWidth = targetWidth - (BUTTON_PADDING + buttonLine);
    int labelHeight = targetHeight - (BUTTON_PADDING + buttonLine);

    int[] label = getTextLine(text, labelWidth, labelHeight, 1, TextAlignment.CENTER);

    for (int i = 0; i < button.length; i++) {
      button[i] = backgroundColor;
    }
    for (int x = 0; x < targetWidth; x++) {
      for (int y = 0; y < buttonLine; y++) {
        button[x + y * targetWidth] = -1;
      }
    }
    for (int x = 0; x < targetWidth; x++) {
      for (int y = targetHeight - buttonLine; y < targetHeight; y++) {
        button[x + y * targetWidth] = -1;
      }
    }
    for (int x = 0; x < buttonLine; x++) {
      for (int y = 0; y < targetHeight; y++) {
        button[x + y * targetWidth] = -1;
      }
    }
    for (int x = targetWidth - buttonLine; x < targetWidth; x++) {
      for (int y = 0; y < targetHeight; y++) {
        button[x + y * targetWidth] = -1;
      }
    }
    int labelpadding = buttonLine + BUTTON_PADDING - 2;
    int index = 0;
    for (int y = labelpadding; y < labelHeight + labelpadding; y++) {
      for (int x = labelpadding; x < labelWidth + labelpadding; x++) {
        button[x + y * targetWidth] = label[index];
        index++;
      }
    }

    int buttonIndex = 0;

    for (int i : button) {
      if (i == 0) {
        button[buttonIndex] = backgroundColor;
      }
      if (i == -1) {
        button[buttonIndex] = fontcolor;
      }
      buttonIndex++;
    }

    return button;
  }

  public Symbol getSymbol(String symbolKey) {
    Symbol symbol = this.symbolMap.get(symbolKey);
    if (symbol == null) {
      Symbol.missingx8.copy();
    }
    return symbol.copy();
  }

  protected int[] getTextBlock(
      String text,
      int maxWidth,
      int fontSize,
      TextAlignment alignment,
      Color background,
      Color font) {
    String[] rows = splitRows(text, maxWidth, fontSize);
    int[] result = new int[0];
    for (String s : rows) {
      int[] rowPixels =
          getTextLine(s, maxWidth, 10 * fontSize, fontSize, alignment, background, font);
      int[] newPixels = new int[result.length + rowPixels.length];
      System.arraycopy(result, 0, newPixels, 0, result.length);
      System.arraycopy(rowPixels, 0, newPixels, result.length, rowPixels.length);
      result = newPixels;
    }
    return result;
  }

  private static String[] splitRows(String text, int width, int fontSize) {
    int stringWidth = getStringWidth(text, fontSize);
    boolean hasLineBreaks = text.contains("[br]");
    if (stringWidth > width || hasLineBreaks) {
      return splitAt(width, text, fontSize);
    } else {
      return new String[] {text};
    }
  }

  private static int getStringWidth(String s, int fontSize) {
    int chars = s.length();
    if (s.contains("[")) {
      int charcounter = 0;
      char[] stringChars = new char[s.length()];
      s.getChars(0, s.length() - 1, stringChars, 0);
      boolean inBrackets = false;
      for (char c : stringChars) {
        if (c == '[') {
          inBrackets = true;
          charcounter++;
        } else if (c == ']') {
          inBrackets = false;
        } else if (inBrackets) {
          charcounter++;
        }
      }
      chars -= charcounter;
    }
    return chars * ((Property.BASE_CHAR_DISTANCE + getCharWidth()) * fontSize);
  }

  private static String[] splitAt(int split, String s, int fontSize) {
    List<String> result = new ArrayList<>();
    String[] splitWhole = s.split(" ");
    int loopcheck = 0;
    while (hasEntries(splitWhole) && loopcheck < 10) {
      result.add(getNextRow(splitWhole, split, fontSize));
      loopcheck++;
    }
    return getArray(result);
  }

  private static boolean hasEntries(String[] split) {
    for (String s : split) {
      if (!s.equals(" ")) {
        return true;
      }
    }
    return false;
  }

  private static String getNextRow(String[] splitted, int split, int fontSize) {
    String newS = "";
    int index = getFirstFilled(splitted);
    while (getStringWidth(newS, fontSize) + getStringWidth(splitted[index], fontSize) + 5 < split) {
      if (splitted[index].equals("[br]")) {
        splitted[index] = " ";
        return newS + " ";
      }
      newS += splitted[index] + " ";
      splitted[index] = " ";
      index++;
      if (index == splitted.length) {
        return newS;
      }
    }
    return newS;
  }

  private static int getFirstFilled(String[] split) {
    for (int i = 0; i < split.length; i++) {
      if (!split[i].equals(" ")) {
        return i;
      }
    }
    return 0;
  }

  private static String[] getArray(List<String> list) {
    String[] result = new String[list.size()];
    for (int i = 0; i < list.size(); i++) {
      result[i] = list.get(i);
    }
    return result;
  }

  private static int getCharWidth() {
    return new TextEditor(TextEditor.baseConf).charWidth;
  }

  public static class TextEditorConfig {

    private int charWidth;
    private int charHeight;
    private int smallNumWidth;
    private int smallNumHeight;

    public TextEditorConfig(int charWidth, int charHeight, int smallNumWidth, int smallNumHeight) {
      this.charWidth = charWidth;
      this.charHeight = charHeight;
      this.smallNumWidth = smallNumWidth;
      this.smallNumHeight = smallNumHeight;
    }

    public int getCharWidth() {
      return this.charWidth;
    }

    public int getCharHeight() {
      return this.charHeight;
    }

    public int getSmallNumWidth() {
      return smallNumWidth;
    }

    public int getSmallNumHeight() {
      return smallNumHeight;
    }
  }
}
