package framework.graphics.text;

import framework.Property;
import game.entities.Role;
import game.skills.Stat;
import game.skills.changeeffects.effects.*;
import game.skills.changeeffects.effects.other.Exhausted;
import game.skills.changeeffects.effects.other.Steadfast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TextEditor {

    public static int BUTTON_PADDING = 2;
    public static int BASE_CHAR_DISTANCE = 1;

    public static String ACTION_KEY = "ACT";
    public static String TURN_KEY = "TRN";
    public static String TURN_CD_KEY = "TCD";

    public static String OVERHEAT_INACTIVE_KEY = "OHI";

    public int charWidth,charHeight,smallNumHeight,smallNumWidth;
    public boolean hasLowerCase=true;
    public static TextEditorConfig conf5x8 = new TextEditorConfig(5,8, 3, 5);
    private final HashMap<String, int[]> smallNumeric;
    private final HashMap<String,int[]> symbols;
    private final HashMap<String, Symbol> symbolMap;
    private final HashMap<String,String> iconCodes = new HashMap<>();
    private static final HashMap<String,String> staticIconCodes = new HashMap<>();

    public static TextEditorConfig baseConf = TextEditor.conf5x8;

    public TextEditor(TextEditorConfig conf) {
        this.charWidth = conf.getCharWidth();
        this.charHeight = conf.getCharHeight();
        this.smallNumHeight = conf.getSmallNumHeight();
        this.smallNumWidth = conf.getSmallNumWidth();
        this.symbols = fillSymbols5x8();
        this.symbolMap = fillSymbolMap();
        this.smallNumeric = fillSmallNumeric();
        fillIconMap();
    }
    private void fillIconMap() {
        iconCodes.put("CARBON","CBN");
        iconCodes.put("FOOD","FOD");
        iconCodes.put("KYBER","KBR");
        iconCodes.put("BESKAR","BSK");
        iconCodes.put("GAS","GAS");
        iconCodes.put("COAXIUM","CXM");
        iconCodes.put("DURASTEEL","DSL");
        iconCodes.put("DURAPLAST","DPL");
        iconCodes.put("HYPERMATTER","HYM");
        iconCodes.put("BACTA","BCT");
        iconCodes.put("ENERGY","NRG");
        iconCodes.put("CREDITS","CRD");
    }
    public String getIconCode(String key) {
        return iconCodes.get(key);
    }
    public int[] getIcon(String key) {
        return symbols.get(key);
    }
    public static String getStaticIconCode(String key, boolean withBrackets) {
        String result = "";
        result = staticIconCodes.get(key);
        if (withBrackets) {
            return "["+result+"]";
        } else {
            return result;
        }
    }

    private HashMap<String, int[]> fillSmallNumeric() {
        HashMap<String,int[]> symbols = new HashMap<String,int[]>();
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
        symbols.put(Stat.CURRENT_MANA.getIconKey(), Symbol.mana);
        symbols.put(Stat.MANA.getIconKey(), Symbol.mana);
        symbols.put(Stat.MANA_REGAIN.getIconKey(), Symbol.manaregain);
        symbols.put(TURN_KEY, Symbol.turn);
        symbols.put(TURN_CD_KEY, Symbol.turnCD);
        symbols.put(Stat.CURRENT_FAITH.getIconKey(), Symbol.faith);
        symbols.put(Stat.FAITH.getIconKey(), Symbol.faith);
        symbols.put(Stat.CURRENT_HALO.getIconKey(), Symbol.halo);
        symbols.put(Stat.HALO.getIconKey(), Symbol.halo);
        symbols.put(Stat.LIFE.getIconKey(), Symbol.life);
        symbols.put(Stat.CURRENT_LIFE.getIconKey(), Symbol.life);
        symbols.put(Stat.LIFE_REGAIN.getIconKey(), Symbol.liferegain);
        symbols.put(Stat.MAGIC.getIconKey(), Symbol.magic);
        symbols.put(Stat.POWER.getIconKey(), Symbol.force);
        symbols.put(Stat.STAMINA.getIconKey(), Symbol.stamina);
        symbols.put(Stat.ENDURANCE.getIconKey(), Symbol.endurance);
        symbols.put(Stat.SPEED.getIconKey(), Symbol.speed);
        symbols.put(Stat.SHIELD.getIconKey(), Symbol.shield);
        symbols.put(Stat.CRIT_CHANCE.getIconKey(), Symbol.critchance);
        symbols.put(Stat.EVASION.getIconKey(), Symbol.evasion);
        symbols.put(Stat.LETHALITY.getIconKey(), Symbol.lethality);
        symbols.put(Stat.ACCURACY.getIconKey(), Symbol.accuracy);

        symbols.put(Burning.ICON_STRING, Symbol.burning);
        symbols.put(DoubleShot.ICON_STRING, Symbol.doubleshot);
        symbols.put(Exalted.ICON_STRING, Symbol.exalted);
        symbols.put(Frost.ICON_STRING, Symbol.frost);
        symbols.put(Exhausted.ICON_STRING, Symbol.exhausted);
        symbols.put(Immunity.ICON_STRING, Symbol.immunity);
        symbols.put(LifeSteal.ICON_STRING, Symbol.lifesteal);
        symbols.put(Scoped.ICON_STRING, Symbol.scoped);
        symbols.put(Steadfast.ICON_STRING, Symbol.steadfast);
        symbols.put(Threatening.ICON_STRING, Symbol.threatening);
        symbols.put(Bleeding.ICON_STRING, Symbol.bleeding);
        symbols.put(Dazed.ICON_STRING, Symbol.dazed);
        symbols.put(Disenchanted.ICON_STRING, Symbol.disenchanted);
        symbols.put(Injured.ICON_STRING, Symbol.injured);
        symbols.put(Taunted.ICON_STRING, Symbol.taunted);
        symbols.put(Protected.ICON_STRING, Symbol._protected);
        symbols.put("FTT", Symbol.friendTarget);
        symbols.put("ETT", Symbol.enemyTarget);
        symbols.put("OTT", Symbol.otherTarget);
        symbols.put("ETA", Symbol.enemyTargetAll);
        symbols.put("FTA", Symbol.friendTargetAll);
        symbols.put("OTA", Symbol.otherTargetAll);
        symbols.put("EMT", Symbol.emptyTarget);
        symbols.put("BRL", new LineBreak());
        symbols.put("???", Symbol.missingx8);
        symbols.put(Role.TANK.iconKey, Symbol.tank);
        symbols.put(Role.FIGHTER.iconKey, Symbol.fighter);
        symbols.put(Role.DPS.iconKey, Symbol.dps);
        symbols.put(Role.SUPPORT.iconKey, Symbol.support);

        return symbols;
    }
    private HashMap<String,int[]> fillSymbols5x8() {
        HashMap<String,int[]> symbols = new HashMap<String,int[]>();

        symbols.put("1", Symbol.one5x8.pixels);
        symbols.put("2", Symbol.two5x8.pixels);
        symbols.put("3", Symbol.three5x8.pixels);
        symbols.put("4", Symbol.four5x8.pixels);
        symbols.put("5", Symbol.five5x8.pixels);
        symbols.put("6", Symbol.six5x8.pixels);
        symbols.put("7", Symbol.seven5x8.pixels);
        symbols.put("8", Symbol.eight5x8.pixels);
        symbols.put("9", Symbol.nine5x8.pixels);
        symbols.put("0", Symbol.zero5x8.pixels);
        symbols.put("a", Symbol.a5x8.pixels);
        symbols.put("b", Symbol.b5x8.pixels);
        symbols.put("c", Symbol.c5x8.pixels);
        symbols.put("d", Symbol.d5x8.pixels);
        symbols.put("e", Symbol.e5x8.pixels);
        symbols.put("f", Symbol.f5x8.pixels);
        symbols.put("g", Symbol.g5x8.pixels);
        symbols.put("h", Symbol.h5x8.pixels);
        symbols.put("i", Symbol.i5x8.pixels);
        symbols.put("j", Symbol.j5x8.pixels);
        symbols.put("k", Symbol.k5x8.pixels);
        symbols.put("l", Symbol.l5x8.pixels);
        symbols.put("m", Symbol.m5x8.pixels);
        symbols.put("n", Symbol.n5x8.pixels);
        symbols.put("o", Symbol.o5x8.pixels);
        symbols.put("p", Symbol.p5x8.pixels);
        symbols.put("q", Symbol.q5x8.pixels);
        symbols.put("r", Symbol.r5x8.pixels);
        symbols.put("s", Symbol.s5x8.pixels);
        symbols.put("t", Symbol.t5x8.pixels);
        symbols.put("u", Symbol.u5x8.pixels);
        symbols.put("v", Symbol.v5x8.pixels);
        symbols.put("w", Symbol.w5x8.pixels);
        symbols.put("x", Symbol.x5x8.pixels);
        symbols.put("y", Symbol.y5x8.pixels);
        symbols.put("z", Symbol.z5x8.pixels);
        symbols.put("A", Symbol.A5x8.pixels);
        symbols.put("B", Symbol.B5x8.pixels);
        symbols.put("C", Symbol.C5x8.pixels);
        symbols.put("D", Symbol.D5x8.pixels);
        symbols.put("E", Symbol.E5x8.pixels);
        symbols.put("F", Symbol.F5x8.pixels);
        symbols.put("G", Symbol.G5x8.pixels);
        symbols.put("H", Symbol.H5x8.pixels);
        symbols.put("I", Symbol.I5x8.pixels);
        symbols.put("J", Symbol.J5x8.pixels);
        symbols.put("K", Symbol.K5x8.pixels);
        symbols.put("L", Symbol.L5x8.pixels);
        symbols.put("M", Symbol.M5x8.pixels);
        symbols.put("N", Symbol.N5x8.pixels);
        symbols.put("O", Symbol.O5x8.pixels);
        symbols.put("P", Symbol.P5x8.pixels);
        symbols.put("Q", Symbol.Q5x8.pixels);
        symbols.put("R", Symbol.R5x8.pixels);
        symbols.put("S", Symbol.S5x8.pixels);
        symbols.put("T", Symbol.T5x8.pixels);
        symbols.put("U", Symbol.U5x8.pixels);
        symbols.put("V", Symbol.V5x8.pixels);
        symbols.put("W", Symbol.W5x8.pixels);
        symbols.put("X", Symbol.X5x8.pixels);
        symbols.put("Y", Symbol.Y5x8.pixels);
        symbols.put("Z", Symbol.Z5x8.pixels);
        symbols.put(".", Symbol.point5x8.pixels);
        symbols.put(",", Symbol.comma5x8.pixels);
        symbols.put("/", Symbol.slash5x8.pixels);
        symbols.put("(", Symbol.bracketopen5x8.pixels);
        symbols.put(")", Symbol.bracketclose5x8.pixels);
        symbols.put("+", Symbol.plus5x8.pixels);
        symbols.put(":", Symbol.colon5x8.pixels);
        symbols.put(">", Symbol.moreThan5x8.pixels);
        symbols.put("<", Symbol.lessThan5x8.pixels);
        symbols.put("-", Symbol.minus5x8.pixels);
        symbols.put("=", Symbol.equals5x8.pixels);
        symbols.put("%", Symbol.percentage5x8.pixels);
        symbols.put(Stat.MANA.getIconKey(), Symbol.mana.pixels);
        symbols.put(TURN_KEY, Symbol.turn.pixels);
        symbols.put(TURN_CD_KEY, Symbol.turnCD.pixels);
        symbols.put(Stat.FAITH.getIconKey(), Symbol.faith.pixels);
        symbols.put(Stat.LIFE.getIconKey(), Symbol.life.pixels);
        symbols.put(Stat.MAGIC.getIconKey(), Symbol.magic.pixels);
        symbols.put(Stat.POWER.getIconKey(), Symbol.finesse.pixels);
        symbols.put(Stat.STAMINA.getIconKey(), Symbol.stamina.pixels);
        symbols.put(Stat.ENDURANCE.getIconKey(), Symbol.endurance.pixels);
        symbols.put(Stat.SPEED.getIconKey(), Symbol.speed.pixels);
        symbols.put(Stat.SHIELD.getIconKey(), Symbol.shield.pixels);
        symbols.put(Stat.CRIT_CHANCE.getIconKey(), Symbol.critchance.pixels);
        symbols.put(Stat.EVASION.getIconKey(), Symbol.evasion.pixels);
        symbols.put(Stat.LETHALITY.getIconKey(), Symbol.lethality.pixels);
        return symbols;
    }

    public int[] getTextLine(String text, int targetWidth, int targetHeight) {

        return getTextLine(text,targetWidth,targetHeight,
                0,TextAlignment.CENTER,
                Color.BLACK, Color.WHITE);
    }
    public int[] getTextLine(String text, int targetWidth, int targetHeight,int fontsize,TextAlignment alignment) {

        return getTextLine(text,targetWidth,targetHeight,
                fontsize,alignment,
                Color.BLACK, Color.WHITE);
    }
    public int[] getTextLine(String text, int targetWidth, int targetHeight,Color color) {

        return getTextLine(text,targetWidth,targetHeight,
                0,TextAlignment.CENTER,
                Color.BLACK, Color.WHITE);
    }
    public int[] getSmallNumTextLine(String text, int targetWidth, int targetHeight, TextAlignment alignment,
                                     Color backGroundColor, Color fontColor) {
        if(text == null) {
            return new int[targetWidth*targetHeight];
        }
        int backgroundColor = backGroundColor.VALUE;
        int fontcolor = fontColor.VALUE;

        int[] result = new int[targetWidth * targetHeight];
        int textLength = getTextWidth(text);
        int resultWidth = Math.max(1,textLength * this.smallNumWidth + textLength - 1);
        int[] word = new int[this.smallNumHeight * resultWidth];

        char[] textArray = new char[text.length()];

        int widthOverhead = targetWidth - resultWidth;
        int leftOverhead = (widthOverhead / 2);
        int rightOverhead = (widthOverhead / 2) + (widthOverhead % 2);

        if(alignment==TextAlignment.LEFT) {
            leftOverhead = 0;
            rightOverhead = widthOverhead;
        }
        if(alignment==TextAlignment.RIGHT) {
            rightOverhead = 0;
            leftOverhead  = widthOverhead;
        }
        Arrays.fill(result, backgroundColor);

        int lastWrittenWidth = 0;
        text.getChars(0, text.length(), textArray, 0);

        int symbolNr = 1;
        for (int charindex = 0; charindex < textArray.length; charindex++) {
            char symbol = textArray[charindex];

            int[] symbolarray = smallNumeric.get((symbol+""));
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
                lastWrittenWidth+=1;
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

        for(int i : result) {
            if(i == 0 || i == -16777216 || i == -12450784) {
                result[resultIndex] = backgroundColor;
            }else if(i == -1 || i == -1710619) {
                result[resultIndex] = fontcolor;
            }else {
            }
            resultIndex++;
        }
        return result;
    }

    public int[] getTextLineNew(String text, int targetWidth, int targetHeight,int fontSize, TextAlignment alignment,
                                 Color backGround, Color font) {

        int bgValue = backGround.VALUE;

        int[] result = new int[targetWidth*targetHeight];
        Arrays.fill(result, bgValue);

        if (text == null) {
            return result;
        }

        List<Symbol> textSymbols =getSymbols(text, font, bgValue);

        int textWidth = getTextWidth(textSymbols);

        int[] rawText = getLineFromSymbols(textSymbols,textWidth, bgValue);

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
        int[] rawText = new int[this.charHeight*textWidth];
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
                charIndex+=4;
            } else {
                Symbol symbol;
                if (character == '[') {
                    symbol = getSpecialCharacter(charIndex, textArray);
                    charIndex+=4;
                } else {
                    Symbol _sym = this.symbolMap.get(String.valueOf(character));
                    if (_sym == null) {
                        System.out.println(character);
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
        textWidth += symbols.size()-1;
        return Math.max(textWidth, 0);
    }

    public int[] getTextLine(String text, int targetWidth, int targetHeight,
                             int fontSize, TextAlignment alignment,
                             Color backGroundColor, Color fontColor) {

        if(text == null) {
            return new int[targetWidth*targetHeight];
        }
        int backgroundColor = backGroundColor.VALUE;
        int fontColorValue = fontColor.VALUE;

        int[] result = new int[targetWidth * targetHeight];
        int textLength = getTextWidth(text);
        int resultWidth = Math.max(1,textLength * this.charWidth + textLength - 1);
        int[] word = new int[this.charHeight * resultWidth];

        int multiplier;
        int xmultiplier = targetWidth / resultWidth;
        int ymultiplier = targetHeight / this.charHeight;
        multiplier = Math.min(xmultiplier, ymultiplier);

        if(multiplier > fontSize && fontSize > 0) {
            multiplier = fontSize;
        }

        char[] textArray = new char[text.length()];

        int heightOverhead = targetHeight - (int)(this.charHeight * multiplier);
        int topOverhead = (heightOverhead / 2) + (heightOverhead % 2);
        int bottomOverhead = heightOverhead / 2;

        int widthOverhead = targetWidth - (int)(resultWidth * multiplier);
        int leftOverhead = (widthOverhead / 2);
        int rightOverhead = (widthOverhead / 2) + (widthOverhead % 2);
        if(text.contains("Level")) {
        }

        if(alignment==TextAlignment.LEFT) {
            leftOverhead = 1;
            rightOverhead = widthOverhead -1;
        }
        if(alignment==TextAlignment.RIGHT) {
            rightOverhead = 1;
            leftOverhead  = widthOverhead - 1;
        }
        if(alignment==TextAlignment.SOFT_LEFT) {
            leftOverhead = 5;
            rightOverhead = widthOverhead-5;
        }
        if(alignment==TextAlignment.SOFT_RIGHT) {
            rightOverhead = 5;
            leftOverhead = widthOverhead - 5;
        }
//
//        for (int row = 0; row < topOverhead; row++) {
//            for (int column = 0; column < targetWidth; column++) {
//                result[column + row * targetWidth] = backgroundColor;
//            }
//        }
//        for (int row = (targetHeight - bottomOverhead) - 1; row < targetHeight; row++) {
//            for (int column = 0; column < targetWidth; column++) {
//                result[column + row * targetWidth] = backgroundColor;
//            }
//        }
//        for (int row = 0; row < targetHeight; row++) {
//            for (int column = 0; column < leftOverhead; column++) {
//                result[column + row * targetWidth] = backgroundColor;
//            }
//        }
//        for (int row = 0; row < targetHeight; row++) {
//            for (int column = (targetWidth - rightOverhead) - 1; column < targetWidth; column++) {
//                result[column + row * targetWidth] = backgroundColor;
//            }
//        }

        int lastWrittenWidth = 0;
        text.getChars(0, text.length(), textArray, 0);

        int symbolNr = 1;
        for (int charindex = 0; charindex < textArray.length; charindex++) {
            char symbol = textArray[charindex];

            if (symbol == '{') {
                fontColorValue = getColor(charindex, textArray, fontColor).VALUE;
                charindex+=4;

            } else {
                if(symbol == '[') {
                    int[] symbolArray = getSpecialSymbol(charindex,textArray);
                    if(symbolArray==null) {
                        symbolArray = new int[this.charWidth*this.charHeight];
                    }
                    int index = 0;
                    for (int y = 0; y < this.charHeight; y++) {
                        for (int x = lastWrittenWidth; x < lastWrittenWidth + this.charWidth; x++) {
                            word[x + y * resultWidth] = symbolArray[index];
                            index++;
                        }
                    }
                    charindex+=4;
                } else if (!(Character.getNumericValue(symbol) == -1)) {
                    String s ="";
                    if(this.hasLowerCase) {
                        s = String.valueOf(symbol);
                    }else {
                        s = String.valueOf(symbol).toLowerCase();
                    }
                    int[] symbolarray = symbols.get(s);

                    int index = 0;

                    for (int y = 0; y < this.charHeight; y++) {
                        for (int x = lastWrittenWidth; x < lastWrittenWidth + this.charWidth; x++) {
                            int val = symbolarray[index];
                            word[x + y * resultWidth] = val == -1 || val == -1710619 ? fontColorValue : backgroundColor;
                            index++;
                        }
                    }

                } else if (Character.isDigit(symbol)) {

                    int[] symbolarray = symbols.get((symbol+""));
                    int index = 0;

                    for (int y = 0; y < this.charHeight; y++) {
                        for (int x = lastWrittenWidth; x < lastWrittenWidth + this.charWidth; x++) {
                            int val = symbolarray[index];
                            word[x + y * resultWidth] = val == -1 || val == -1710619 ? fontColorValue : backgroundColor;
                            index++;
                        }
                    }

                } else if (symbol == ' ') {
                    for (int y = 0; y < this.charHeight; y++) {
                        for (int x = lastWrittenWidth; x < lastWrittenWidth + this.charWidth; x++) {
                            word[x + y * resultWidth] = backgroundColor;
                        }
                    }
                }else if (symbol == '.') {
                    for (int y = 0; y < this.charHeight; y++) {
                        for (int x = lastWrittenWidth; x < lastWrittenWidth + this.charWidth; x++) {
                            word[x + y * resultWidth] = backgroundColor;
                        }
                    }
                    word[(lastWrittenWidth+2)+6*resultWidth] = fontColorValue;
                    word[(lastWrittenWidth+3)+6*resultWidth] = fontColorValue;
                    word[(lastWrittenWidth+2)+5*resultWidth] = fontColorValue;
                    word[(lastWrittenWidth+3)+5*resultWidth] = fontColorValue;
                }else {
                    String symbolS = String.valueOf(symbol);
                    int[] symbolarray = symbols.get(symbolS);
                    if(symbolarray==null || symbolarray.length==0)
                        symbolarray=symbols.get("0");
                    int index = 0;

                    for (int y = 0; y < this.charHeight; y++) {
                        for (int x = lastWrittenWidth; x < lastWrittenWidth + this.charWidth; x++) {
                            int val = symbolarray[index];
                            word[x + y * resultWidth] = val == -1 || val == -1710619 ? fontColorValue : backgroundColor;
                            index++;
                        }
                    }

                }

                lastWrittenWidth += this.charWidth;
                if (symbolNr != textLength) {
                    for (int i = 0; i < this.charHeight; i++) {
                        word[lastWrittenWidth + i * resultWidth] = backgroundColor;
                    }
                    lastWrittenWidth+=BASE_CHAR_DISTANCE;
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
        return text.length() - (int)iconCount*4 - (int)colorCount*5;
    }
    private int[] getSpecialSymbol(int index, char[] textArray) {
        StringBuilder specialSymbolCode = new StringBuilder();
        for(int i = index+1; i < index+4; i++) {
            specialSymbolCode.append(textArray[i]);
        }
        return symbols.get(specialSymbolCode.toString());
    }
    private Symbol getSpecialCharacter(int index, char[] textArray) {
        StringBuilder specialSymbolCode = new StringBuilder();
        for(int i = index+1; i < index+4; i++) {
            specialSymbolCode.append(textArray[i]);
        }
        Symbol original = symbolMap.get(specialSymbolCode.toString());
        if (original == null) {
            System.out.println("special character not found: "+ specialSymbolCode);
            return null;
        }
        return original.copy();
    }
    private Color getColor(int index, char[] textArray, Color fontColor) {
        StringBuilder codeBuilder = new StringBuilder();
        for(int i = index+1; i < index+4; i++) {
            codeBuilder.append(textArray[i]);
        }
        String code = codeBuilder.toString();
        if ( code.equals("000")) {
            return fontColor;
        }
        return Color.fromCode(code);
    }

    public int[] getButtonLine(String text, int targetWidth, int targetHeight,
                               Color backGroundColor, Color fontColor, int buttonLine) {

        int backgroundColor = backGroundColor.VALUE;
        int fontcolor = fontColor.VALUE;
        int[] button = new int[targetWidth*targetHeight];


        int labelWidth = targetWidth-(BUTTON_PADDING+buttonLine);
        int labelHeight = targetHeight-(BUTTON_PADDING+buttonLine);

        int[] label = getTextLine(text, labelWidth, labelHeight, 1, TextAlignment.CENTER);

        for(int i = 0; i < button.length; i++) {
            button[i] = backgroundColor;
        }
        for(int x = 0; x < targetWidth; x++) {
            for(int y = 0; y < buttonLine; y++ ) {
                button[x+y*targetWidth] = -1;
            }
        }
        for(int x = 0; x < targetWidth; x++) {
            for(int y = targetHeight-buttonLine; y < targetHeight; y++ ) {
                button[x+y*targetWidth] = -1;
            }
        }
        for(int x = 0; x < buttonLine; x++) {
            for(int y = 0; y < targetHeight; y++ ) {
                button[x+y*targetWidth] = -1;
            }
        }
        for(int x = targetWidth-buttonLine; x < targetWidth; x++) {
            for(int y = 0; y < targetHeight; y++ ) {
                button[x+y*targetWidth] = -1;
            }
        }
        int labelpadding = buttonLine+BUTTON_PADDING-2;
        int index = 0;
        for(int y = labelpadding; y < labelHeight+labelpadding; y++) {
            for(int x = labelpadding; x < labelWidth+labelpadding; x++) {
                button[x+y*targetWidth] = label[index];
                index++;
            }
        }



        int buttonIndex = 0;

        for(int i : button) {
            if(i == 0) {
                button[buttonIndex] = backgroundColor;
            }
            if(i == -1) {
                button[buttonIndex] = fontcolor;
            }
            buttonIndex++;
        }

        return button;
    }
    public Symbol getSymbol(String symbolKey) {
        return this.symbolMap.get(symbolKey).copy();
    }
    protected int[] getTextBlock(String text, int maxWidth, int fontSize, TextAlignment alignment, Color background, Color font) {
        String[] rows = splitRows(text, maxWidth, fontSize);
        int[] result = new int[0];
        for (String s : rows) {
            int[] rowPixels = getTextLine(s, maxWidth, 10 * fontSize, fontSize, alignment, background, font);
            int[] newPixels = new int[result.length + rowPixels.length];
            System.arraycopy(result, 0, newPixels, 0, result.length);
            System.arraycopy(rowPixels, 0, newPixels, result.length, rowPixels.length);
            result = newPixels;
        }
        return result;
    }

    private static String[] splitRows(String text, int width, int fontSize) {
        int stringWidth = getStringWidth(text, fontSize);
        boolean hasLineBreaks = text.contains("[br][br]");
        if (stringWidth > width || hasLineBreaks) {
            return splitAt(width, text, fontSize);
        } else {
            return new String[]{text};
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
        while (hasEntries(splitWhole) && loopcheck<10) {
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
            this.charWidth=charWidth;
            this.charHeight=charHeight;
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