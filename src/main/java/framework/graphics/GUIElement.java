package framework.graphics;

import framework.Logger;
import framework.Property;
import framework.graphics.text.*;

import javax.sound.sampled.Line;
import java.util.*;

public class GUIElement {
    public int id;
    public String name;
    protected int width, height;
    protected Color background = Color.VOID;
    protected int[] pixels;
    protected int x, y; //location in parent
    protected TextEditor editor = new TextEditor(TextEditor.baseConf);
    protected List<GUIElement> children = new ArrayList<>();

    protected boolean simpleBorder;
    protected Color borderColor = Color.WHITE;
    protected int borderThickness;

    protected boolean active;

    public GUIElement() {
        this.pixels= new int[0];
    }
    public GUIElement(String name) {
        this.pixels= new int[0];
        this.name = name;
    }

    public GUIElement(int width, int height, Color background, int[] pixels, int x, int y) {
        this.width = width;
        this.height = height;
        this.background = background;
        this.pixels = pixels;
        this.x = x;
        this.y = y;
    }
    public GUIElement(int width, int height, int x, int y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.pixels = new int[width*height];
    }

    public GUIElement(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new int[width*height];
    }

    public static int[] setBackGround(int[] pixels, Color color) {
        for (int i = 0; i < pixels.length; i++) {
            if (pixels[i] == Color.VOID.VALUE) {
                pixels[i] = color.VALUE;
            }
        }
        return pixels;
    }

    public void update(int frame) {}
    public int[] render() {
        renderChildren();
        return this.pixels;
    }

    protected void updateChildren(int frame) {
        for (GUIElement child : children) {
            if (child != null) {
                child.update(frame);
            }
        }
    }
    protected void renderChildren() {
        this.children.removeIf(Objects::isNull);
        for (GUIElement child : children) {
            if (child != null) {
                int[] childP = child.render();
                fillWithGraphicsSize(child.getX(), child.getY(), child.getWidth(), child.getHeight(), childP, false);
            }
        }
    }
    protected void deactivateAllChildren() {
        for (GUIElement child : children) {
            if (child != null) {
                child.setActive(false);
            }
        }
    }
    protected int[] getTextBlock(String text, int maxWidth, Color background, Color font) {
        List<Symbol> symbols = editor.getSymbols(text, font, background.VALUE);
        List<List<Symbol>> rows = splitRows(symbols, maxWidth);
        int[] result = new int[maxWidth * rows.size()*8];
        int rowIndex = 0;
        for (List<Symbol> row : rows) {
            int textWidth = editor.getTextWidth(row);
            int[] rowPixels = editor.getLineFromSymbols(row, textWidth, background.VALUE);
            int yFrom = rowIndex*8;
            for (int x = 0; x < textWidth; x++) {
                int ySource = 0;
                for (int y = yFrom; y < yFrom + 8; y++) {
                    result[x + y * maxWidth] = rowPixels[x + ySource * textWidth];
                    ySource++;
                }
            }
            rowIndex++;
        }
        return result;
    }

    private List<List<Symbol>> splitRows(List<Symbol> fullList, int maxWidth) {
        int width = editor.getTextWidth(fullList);
        boolean hasLineBreaks = fullList.stream().anyMatch(s -> s instanceof LineBreak);
        if (width > maxWidth || hasLineBreaks) {
            return splitAt(maxWidth, fullList);
        } else {
            return List.of(fullList);
        }
    }

    private List<List<Symbol>> splitAt(int maxWidth, List<Symbol> symbols) {
        List<List<Symbol>> result = new ArrayList<>();
        List<List<Symbol>> splitByWhiteSpace = splitByWhiteSpace(symbols);
        int loopCheck = 0;
        while(!splitByWhiteSpace.isEmpty() && loopCheck < 10) {
            result.add(getNextRow(splitByWhiteSpace, maxWidth));
            loopCheck++;
        }
        return result;
    }

    private List<List<Symbol>> splitByWhiteSpace(List<Symbol> symbols) {
        List<List<Symbol>> result = new ArrayList<>();
        List<Symbol> currentWord = new ArrayList<>();

        for (Symbol symbol : symbols) {
            if (symbol.code == null || !symbol.code.equals(" ")) {
                currentWord.add(symbol);
            } else {
                result.add(currentWord);
                currentWord = new ArrayList<>();
            }
        }
        result.add(currentWord);
        return result;
    }

    private List<Symbol> getNextRow(List<List<Symbol>> words, int maxWidth) {
        List<Symbol> newLine = new ArrayList<>();
        if (words.isEmpty()) {
            return newLine;
        }
        int index = 0;
        while (editor.getTextWidth(newLine) + editor.getTextWidth(words.get(index)) + 5 < maxWidth) {
            if (words.get(index) instanceof LineBreak) {
                words.remove(index);
                return newLine;
            }
            newLine.addAll(words.get(index));
            words.remove(index);
            newLine.add(editor.getSymbol(" "));
            if (index == words.size()) {
                return newLine;
            }
        }
        return newLine;
    }
    private boolean noRemainingWords(List<List<Symbol>> words) {
        for (List<Symbol> word : words) {
            if (!word.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private static int getCharWidth() {
        return new TextEditor(TextEditor.baseConf).charWidth;
    }
    protected int[] getTextLine(String text, int textWidth, int textHeight, Color fontColor) {
        return editor.getTextLineNew(text, textWidth, textHeight,1, TextAlignment.CENTER, Color.VOID, fontColor);
    }
    protected int[] getTextLine(String text, int textWidth, int textHeight, TextAlignment alignment, Color background, Color fontColor) {
        return editor.getTextLineNew(text, textWidth, textHeight, 1,alignment, background, fontColor);
    }
    protected int[] getSmallNumTextLine(String text, int textWidth, int textHeight, TextAlignment alignment, Color background, Color fontColor) {
        return editor.getSmallNumTextLine(text, textWidth, textHeight, alignment, background, fontColor);
    }

    protected void writeLine(String text, int xFrom, int xUntil, int yFrom, int yUntil) {
        writeLine(text, xFrom, xUntil, yFrom, yUntil, 0, TextAlignment.CENTER, Color.BLACK, Color.WHITE);
    }

    protected void writeLine(String text, int xFrom, int xUntil, int yFrom, int yUntil, int fontSize, TextAlignment alignment, Color backGround, Color font) {

        int[] result = editor.getTextLine(text, (xUntil - xFrom + 1), (yUntil - yFrom + 1), fontSize, alignment, backGround, font);
        int index = 0;
        for (int y = yFrom; y <= yUntil; y++) {
            for (int x = xFrom; x <= xUntil; x++) {
                if (result[index] != -12450784) {
                    pixels[x + y * this.width] = result[index];
                } else if (!backGround.equals(Color.VOID)) {
                    pixels[x + y * this.width] = backGround.VALUE;
                }
                index++;
            }
        }
    }

    protected void horizontalLine(int xfrom, int xuntil, int height) {
        for (int i = xfrom; i <= xuntil; i++) {
            pixels[i + height * this.width] = -1;
        }
    }
    public static void verticalLine(int x, int yf, int yu, int w, int[] sprite, Color color) {
        for (int i = yf; i <= yu; i++) {
            sprite[x + i * w] = color.VALUE;
        }
    }
    public static void addBorder(int w, int h, int[] sprite, Color color) {
        for (int i = 0; i < h; i++) {
            sprite[i * w] = color.VALUE;
            sprite[(w-1) + i * w] = color.VALUE;
        }
        for (int j = 0; j < w; j++) {
            sprite[j] = color.VALUE;
            sprite[j + (h-1) * w] = color.VALUE;
        }
    }
    protected void fill(int[] graphics) {
        if (this.pixels.length != graphics.length) {
            System.out.println("ERROR: Not the same length");
            return;
        }
        for (int i = 0; i < this.width*this.height; i++) {
            int color =  graphics[i];
            if (color != Color.VOID.VALUE) {
                this.pixels[i] = color;
            }
        }
    }
    public static void staticFillSize(int xf, int yf, int w, int h, int targetW, int[] target, int[] fill) {

        int yu = yf + h;
        int xu = xf + w;
        int index = 0;
        for (int y = yf; y < yu; y++) {
            for (int x = xf; x < xu; x++) {
                target[x + y * targetW] = fill[index];
                index++;
            }
        }
    }
    protected void fillWithGraphicsSize(int xf, int yf, int w, int h, int[] graphics, Color borderColor) {
        fillWithGraphicsSize(xf, yf, w, h, graphics, borderColor != null, borderColor, Color.VOID);
    }

    protected void fillWithGraphicsSize(int xf, int yf, int w, int h, int[] graphics, boolean bordered, Color borderColor) {
        fillWithGraphicsSize(xf, yf, w, h, graphics, bordered, borderColor, Color.VOID);
    }

    protected void fillWithGraphicsSize(int xf, int yf, int w, int h, int[] graphics, boolean bordered) {
        fillWithGraphicsSize(xf, yf, w, h, graphics, bordered, Color.WHITE, Color.VOID);
    }

    protected void fillWithGraphicsSize(int xf, int yf, int w, int h, int[] graphics, boolean bordered, Color borderColor, Color backgroundColor) {
        int xu = xf + w - 1;
        int yu = yf + h - 1;
        fillWithGraphics(xf, xu, yf, yu, graphics, bordered, borderColor, backgroundColor);
    }

    protected void fillWithGraphics(int xfrom, int xuntil, int yfrom, int yuntil, int[] graphics, boolean bordered, Color borderColor, Color backgroundColor) {

        if (graphics == null) {
            return;
        }
        int index = 0;
        for (int i = yfrom; i <= yuntil; i++) {
            for (int j = xfrom; j <= xuntil; j++) {

                if (index >= graphics.length) {
                    continue;
                }
                if (j + i * this.width >= pixels.length) {
                    continue;
                }
                if (graphics[index] != Color.VOID.VALUE) {
                    int color = graphics[index];
                    int arrayindex = j + i * this.width;
                    if (arrayindex < pixels.length && arrayindex >= 0)
                        pixels[arrayindex] = color;
                } else if (!backgroundColor.equals(Color.VOID)) {
                    pixels[j + i * this.width] = backgroundColor.VALUE;
                }
                index++;
            }
        }
        if (bordered) {
            try {
                for (int i = xfrom; i <= xuntil; i++) {
                    pixels[i + (yfrom) * this.width] = borderColor.VALUE;
                    pixels[i + (yuntil) * this.width] = borderColor.VALUE;
                }
                for (int i = yfrom; i <= yuntil; i++) {
                    pixels[(xfrom) + i * this.width] = borderColor.VALUE;
                    pixels[(xuntil) + i * this.width] = borderColor.VALUE;
                }
            } catch (IndexOutOfBoundsException e) {
                Logger.logLn("[ERROR]: Writing border failed");
            }

        }
    }

    public void background(Color color) {
        Arrays.fill(this.pixels, color.VALUE);
    }
    protected int[] getBar(int width, int height, int startWidth, double percentage, Color color, Color color2) {
        if (percentage > 1) {
            percentage = 1;
        }

        int[] resultPixels = new int[width * height];
        int filledSize = (int) (width * percentage);
        if (filledSize + startWidth > width) {
            Logger.logLn("Bar start and fill width too much for space");
            return resultPixels;
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                resultPixels[x + y * width] = color2.VALUE;
            }
        }
        for (int x = startWidth; x < startWidth+filledSize; x++) {
            for (int y = 0; y < height; y++) {
                resultPixels[x + y * width] = color.VALUE;
            }
        }
        return resultPixels;
    }
    protected void writeBar(int xFrom, int xUntil, int yFrom, int yUntil, double percentage, Color color, Color color2) {

        int filledSize = (int) ((xUntil - xFrom) * percentage);

        for (int x = xFrom; x < (xUntil); x++) {
            for (int y = yFrom; y < yUntil; y++) {
                pixels[x + y * width] = color2.VALUE;
            }
        }

        for (int x = xFrom; x < (xFrom + filledSize); x++) {
            for (int y = yFrom; y < yUntil; y++) {
                pixels[x + y * width] = color.VALUE;
            }
        }

        for (int i = xFrom; i < xUntil + 1; i++) {
            pixels[i + yFrom * this.width] = -1;
            pixels[i + yUntil * this.width] = -1;
        }
        for (int i = yFrom; i < yUntil + 1; i++) {
            pixels[(xFrom) + i * this.width] = -1;
            pixels[(xUntil) + i * this.width] = -1;
        }
    }
    protected void clear() {
        this.pixels = new int[this.pixels.length];
    }

    public int[] getPixels() {
        return this.pixels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isSimpleBorder() {
        return simpleBorder;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public int getBorderThickness() {
        return borderThickness;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new int[width * height];
    }


    public void setBackground(Color background) {
        this.background = background;
    }

    public void setPixels(int[] pixels) {
        this.pixels = pixels;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setEditor(TextEditor editor) {
        this.editor = editor;
    }

    public void setChildren(List<GUIElement> children) {
        this.children = children;
    }

    public void setBorder(Color borderColor, int borderThickness) {
        this.borderColor = borderColor;
        this.borderThickness = borderThickness;
        this.simpleBorder = true;
    }
    public void removeBorder() {
        this.simpleBorder = false;
    }
}
