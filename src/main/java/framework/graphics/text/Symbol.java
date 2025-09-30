package framework.graphics.text;

import framework.resources.SpriteLibrary;
import framework.resources.SpriteSheet;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;

public class Symbol {
    public final int WIDTH,HEIGHT;
    public int[] pixels;
    public Integer primaryColor;
    public Integer secondaryColor;
    public String code;

    public static Symbol zero5x8 = 		new Symbol(50,	0,4,	0,7,	SpriteSheet.fonts5x8);
    public static Symbol one5x8 = 		new Symbol(50,	5,9,	0,7,	SpriteSheet.fonts5x8);
    public static Symbol two5x8 = 		new Symbol(50,	10,14,	0,7,	SpriteSheet.fonts5x8);
    public static Symbol three5x8=		new Symbol(50,	15,19,	0,7,	SpriteSheet.fonts5x8);
    public static Symbol four5x8=		new Symbol(50,    20,24,	0,7,	SpriteSheet.fonts5x8);
    public static Symbol five5x8=		new Symbol(50,	25,29,	0,7,	SpriteSheet.fonts5x8);
    public static Symbol six5x8 = 		new Symbol(50,	30,34,	0,7,	SpriteSheet.fonts5x8);
    public static Symbol seven5x8 = 	new Symbol(50, 	35,39,	0,7,	SpriteSheet.fonts5x8);
    public static Symbol eight5x8 = 	new Symbol(50,  40,44,  0,7,	SpriteSheet.fonts5x8);
    public static Symbol nine5x8 = 		new Symbol(50,  45,49,  0,7,    SpriteSheet.fonts5x8);
    public static Symbol A5x8 = 		new Symbol(50,	0,4,	8,15,	SpriteSheet.fonts5x8);
    public static Symbol B5x8 = 		new Symbol(50,	5,9,	8,15,	SpriteSheet.fonts5x8);
    public static Symbol C5x8 = 		new Symbol(50,	10,14,	8,15,	SpriteSheet.fonts5x8);
    public static Symbol D5x8=			new Symbol(50,	15,19,	8,15,	SpriteSheet.fonts5x8);
    public static Symbol E5x8=			new Symbol(50,  20,24,	8,15,	SpriteSheet.fonts5x8);
    public static Symbol F5x8=			new Symbol(50,	25,29,	8,15,	SpriteSheet.fonts5x8);
    public static Symbol G5x8 = 		new Symbol(50,	30,34,	8,15,	SpriteSheet.fonts5x8);
    public static Symbol H5x8 = 		new Symbol(50, 	35,39,	8,15,	SpriteSheet.fonts5x8);
    public static Symbol I5x8 = 		new Symbol(50,  40,44,  8,15,	SpriteSheet.fonts5x8);
    public static Symbol J5x8 = 		new Symbol(50,  45,49,  8,15,   SpriteSheet.fonts5x8);
    public static Symbol K5x8 = 		new Symbol(50,	0,4,	16,23,	SpriteSheet.fonts5x8);
    public static Symbol L5x8 = 		new Symbol(50,	5,9,	16,23,	SpriteSheet.fonts5x8);
    public static Symbol M5x8 = 		new Symbol(50,	10,14,	16,23,	SpriteSheet.fonts5x8);
    public static Symbol N5x8=			new Symbol(50,	15,19,	16,23,	SpriteSheet.fonts5x8);
    public static Symbol O5x8=			new Symbol(50,  20,24,	16,23,	SpriteSheet.fonts5x8);
    public static Symbol P5x8=			new Symbol(50,	25,29,	16,23,	SpriteSheet.fonts5x8);
    public static Symbol Q5x8 = 		new Symbol(50,	30,34,	16,23,	SpriteSheet.fonts5x8);
    public static Symbol R5x8 = 		new Symbol(50, 	35,39,	16,23,	SpriteSheet.fonts5x8);
    public static Symbol S5x8 = 		new Symbol(50,  40,44,  16,23,	SpriteSheet.fonts5x8);
    public static Symbol T5x8 = 		new Symbol(50,  45,49,  16,23,  SpriteSheet.fonts5x8);
    public static Symbol U5x8 = 		new Symbol(50,	0,4,	24,31,	SpriteSheet.fonts5x8);
    public static Symbol V5x8 = 		new Symbol(50,	5,9,	24,31,	SpriteSheet.fonts5x8);
    public static Symbol W5x8 = 		new Symbol(50,	10,14,	24,31,	SpriteSheet.fonts5x8);
    public static Symbol X5x8=			new Symbol(50,	15,19,	24,31,	SpriteSheet.fonts5x8);
    public static Symbol Y5x8=			new Symbol(50,  20,24,	24,31,	SpriteSheet.fonts5x8);
    public static Symbol Z5x8=			new Symbol(50,	25,29,	24,31,	SpriteSheet.fonts5x8);

    public static Symbol a5x8 = 		new Symbol(50,	0,4,	32,39,	SpriteSheet.fonts5x8);
    public static Symbol b5x8 = 		new Symbol(50,	5,9,	32,39,	SpriteSheet.fonts5x8);
    public static Symbol c5x8 = 		new Symbol(50,	10,14,	32,39,	SpriteSheet.fonts5x8);
    public static Symbol d5x8=			new Symbol(50,	15,19,	32,39,	SpriteSheet.fonts5x8);
    public static Symbol e5x8=			new Symbol(50,  20,24,	32,39,	SpriteSheet.fonts5x8);
    public static Symbol f5x8=			new Symbol(50,	25,29,	32,39,	SpriteSheet.fonts5x8);
    public static Symbol g5x8 = 		new Symbol(50,	30,34,	32,39,	SpriteSheet.fonts5x8);
    public static Symbol h5x8 = 		new Symbol(50, 	35,39,	32,39,	SpriteSheet.fonts5x8);
    public static Symbol i5x8 = 		new Symbol(50,  40,44,  32,39,	SpriteSheet.fonts5x8);
    public static Symbol j5x8 = 		new Symbol(50,  45,49,  32,39,  SpriteSheet.fonts5x8);
    public static Symbol k5x8 = 		new Symbol(50,	0,4,	40,47,	SpriteSheet.fonts5x8);
    public static Symbol l5x8 = 		new Symbol(50,	5,9,	40,47,	SpriteSheet.fonts5x8);
    public static Symbol m5x8 = 		new Symbol(50,	10,14,	40,47,	SpriteSheet.fonts5x8);
    public static Symbol n5x8=			new Symbol(50,	15,19,	40,47,	SpriteSheet.fonts5x8);
    public static Symbol o5x8=			new Symbol(50,  20,24,	40,47,	SpriteSheet.fonts5x8);
    public static Symbol p5x8=			new Symbol(50,	25,29,	40,47,	SpriteSheet.fonts5x8);
    public static Symbol q5x8 = 		new Symbol(50,	30,34,	40,47,	SpriteSheet.fonts5x8);
    public static Symbol r5x8 = 		new Symbol(50, 	35,39,	40,47,	SpriteSheet.fonts5x8);
    public static Symbol s5x8 = 		new Symbol(50,  40,44,  40,47,	SpriteSheet.fonts5x8);
    public static Symbol t5x8 = 		new Symbol(50,  45,49,  40,47,  SpriteSheet.fonts5x8);
    public static Symbol u5x8 = 		new Symbol(50,	0,4,	48,55,	SpriteSheet.fonts5x8);
    public static Symbol v5x8 = 		new Symbol(50,	5,9,	48,55,	SpriteSheet.fonts5x8);
    public static Symbol w5x8 = 		new Symbol(50,	10,14,	48,55,	SpriteSheet.fonts5x8);
    public static Symbol x5x8=			new Symbol(50,	15,19,	48,55,	SpriteSheet.fonts5x8);
    public static Symbol y5x8=			new Symbol(50,  20,24,	48,55,	SpriteSheet.fonts5x8);
    public static Symbol z5x8=			new Symbol(50,	25,29,	48,55,	SpriteSheet.fonts5x8);

    public static Symbol Ax8=			new Symbol("A", 64,	0,3,	0,7,	SpriteSheet.fonts8x);
    public static Symbol Bx8=			new Symbol("B", 64,	4,7,	0,7,	SpriteSheet.fonts8x);
    public static Symbol Cx8=			new Symbol("C", 64,	8,11,	0,7,	SpriteSheet.fonts8x);
    public static Symbol Dx8=			new Symbol("D", 64,	12,15,	0,7,	SpriteSheet.fonts8x);
    public static Symbol Ex8=			new Symbol("E", 64,	16,19,	0,7,	SpriteSheet.fonts8x);
    public static Symbol Fx8=			new Symbol("F", 64,	20,23,	0,7,	SpriteSheet.fonts8x);
    public static Symbol Gx8=			new Symbol("G", 64,	24,27,	0,7,	SpriteSheet.fonts8x);
    public static Symbol Hx8=			new Symbol("H", 64,	28,31,	0,7,	SpriteSheet.fonts8x);
    public static Symbol Ix8=			new Symbol("I", 64,	32,34,	0,7,	SpriteSheet.fonts8x);
    public static Symbol Jx8=			new Symbol("J", 64,	35,37,	0,7,	SpriteSheet.fonts8x);
    public static Symbol Kx8=			new Symbol("K", 64,	38,41,	0,7,	SpriteSheet.fonts8x);
    public static Symbol Lx8=			new Symbol("L", 64,	42,45,	0,7,	SpriteSheet.fonts8x);
    public static Symbol Mx8=			new Symbol("M", 64,	46,50,	0,7,	SpriteSheet.fonts8x);
    public static Symbol Nx8=			new Symbol("N", 64,	51,54,	0,7,	SpriteSheet.fonts8x);
    public static Symbol Ox8=			new Symbol("O", 64,	55,58,	0,7,	SpriteSheet.fonts8x);
    public static Symbol Px8=			new Symbol("P", 64,	59,62,	0,7,	SpriteSheet.fonts8x);
    public static Symbol Qx8=			new Symbol("Q", 64,	0,3,	8,15,	SpriteSheet.fonts8x);
    public static Symbol Rx8=			new Symbol("R", 64,	4,7,	8,15,	SpriteSheet.fonts8x);
    public static Symbol Sx8=			new Symbol("S", 64,	8,11,	8,15,	SpriteSheet.fonts8x);
    public static Symbol Tx8=			new Symbol("T", 64,	12,16,	8,15,	SpriteSheet.fonts8x);
    public static Symbol Ux8=			new Symbol("U", 64,	17,20,	8,15,	SpriteSheet.fonts8x);
    public static Symbol Vx8=			new Symbol("V", 64,	21,25,	8,15,	SpriteSheet.fonts8x);
    public static Symbol Wx8=			new Symbol("W", 64,	26,30,	8,15,	SpriteSheet.fonts8x);
    public static Symbol Xx8=			new Symbol("X", 64,	31,35,	8,15,	SpriteSheet.fonts8x);
    public static Symbol Yx8=			new Symbol("Y", 64,	36,40,	8,15,	SpriteSheet.fonts8x);
    public static Symbol Zx8=			new Symbol("Z", 64,	41,44,	8,15,	SpriteSheet.fonts8x);
    public static Symbol ax8=			new Symbol("a", 64,	45,48,	8,15,	SpriteSheet.fonts8x);
    public static Symbol bx8=			new Symbol("b", 64,	49,52,	8,15,	SpriteSheet.fonts8x);
    public static Symbol cx8=			new Symbol("c", 64,	53,56,	8,15,	SpriteSheet.fonts8x);
    public static Symbol dx8=			new Symbol("d", 64,	57,60,	8,15,	SpriteSheet.fonts8x);
    public static Symbol ex8=			new Symbol("e", 64,	0,3,	16,23,	SpriteSheet.fonts8x);
    public static Symbol fx8=			new Symbol("f", 64,	4,6,	16,23,	SpriteSheet.fonts8x);
    public static Symbol gx8=			new Symbol("g", 64,	7,10,	16,23,	SpriteSheet.fonts8x);
    public static Symbol hx8=			new Symbol("h", 64,	11,14,	16,23,	SpriteSheet.fonts8x);
    public static Symbol ix8=			new Symbol("i", 64,	15,15,	16,23,	SpriteSheet.fonts8x);
    public static Symbol jx8=			new Symbol("j", 64,	16,17,	16,23,	SpriteSheet.fonts8x);
    public static Symbol kx8=			new Symbol("k", 64,	18,21,	16,23,	SpriteSheet.fonts8x);
    public static Symbol lx8=			new Symbol("l", 64,	22,23,	16,23,	SpriteSheet.fonts8x);
    public static Symbol mx8=			new Symbol("m", 64,	24,28,	16,23,	SpriteSheet.fonts8x);
    public static Symbol nx8=			new Symbol("n", 64,	29,32,	16,23,	SpriteSheet.fonts8x);
    public static Symbol ox8=			new Symbol("o", 64,	33,36,	16,23,	SpriteSheet.fonts8x);
    public static Symbol px8=			new Symbol("p", 64,	37,40,	16,23,	SpriteSheet.fonts8x);
    public static Symbol qx8=			new Symbol("q", 64,	41,44,	16,23,	SpriteSheet.fonts8x);
    public static Symbol rx8=			new Symbol("r", 64,	45,47,	16,23,	SpriteSheet.fonts8x);
    public static Symbol sx8=			new Symbol("s", 64,	48,51,	16,23,	SpriteSheet.fonts8x);
    public static Symbol tx8=			new Symbol("t", 64,	52,54,	16,23,	SpriteSheet.fonts8x);
    public static Symbol ux8=			new Symbol("u", 64,	55,58,	16,23,	SpriteSheet.fonts8x);
    public static Symbol vx8=			new Symbol("v", 64,	59,63,	16,23,	SpriteSheet.fonts8x);
    public static Symbol wx8=			new Symbol("w", 64,	0,4,	24,31,	SpriteSheet.fonts8x);
    public static Symbol xx8=			new Symbol("x", 64,	5,7,	24,31,	SpriteSheet.fonts8x);
    public static Symbol yx8=			new Symbol("y", 64,	8,11,	24,31,	SpriteSheet.fonts8x);
    public static Symbol zx8=			new Symbol("z", 64,	12,15,	24,31,	SpriteSheet.fonts8x);

    public static Symbol zerox8=		new Symbol("0", 64,	16,19,	24,31,  SpriteSheet.fonts8x);
    public static Symbol onex8=			new Symbol("1", 64,	20,22,	24,31,	SpriteSheet.fonts8x);
    public static Symbol twox8=			new Symbol("2", 64,	23,26,	24,31,	SpriteSheet.fonts8x);
    public static Symbol threex8=		new Symbol("3", 64,	27,30,	24,31,	SpriteSheet.fonts8x);
    public static Symbol fourx8=		new Symbol("4", 64,	31,34,	24,31,	SpriteSheet.fonts8x);
    public static Symbol fivex8=		new Symbol("5", 64,	35,38,	24,31,	SpriteSheet.fonts8x);
    public static Symbol sixx8=			new Symbol("6", 64,	39,42,	24,31,	SpriteSheet.fonts8x);
    public static Symbol sevenx8=		new Symbol("7", 64,	43,46,	24,31,	SpriteSheet.fonts8x);
    public static Symbol eightx8=		new Symbol("8", 64,	47,50,	24,31,	SpriteSheet.fonts8x);
    public static Symbol ninex8=		new Symbol("9", 64,	51,54,	24,31,	SpriteSheet.fonts8x);

    public static Symbol exclamationMarkx8 = new Symbol("!", "fonts/8x/exclamationMark.png", 2, 8);
    public static Symbol pointx8 =		new Symbol(".","fonts/8x/point.png",2,8);
    public static Symbol starx8 =		new Symbol("*","fonts/8x/star.png",3,8);
    public static Symbol slashx8 = 	new Symbol("/","fonts/8x/slash.png",3,8);
    public static Symbol colonx8 =     new Symbol(":","fonts/8x/colon.png",2,8);
    public static Symbol semicolonx8 =     new Symbol(";","fonts/8x/semicolon.png",2,8);
    public static Symbol moreThanx8 =  new Symbol(">","fonts/8x/more.png",3,8);
    public static Symbol lessThanx8 =  new Symbol("<","fonts/8x/less.png",3,8);
    public static Symbol bracketopenx8 = new Symbol("(","fonts/8x/bracketOpen.png",2,8);
    public static Symbol bracketclosex8 =new Symbol(")","fonts/8x/bracketClose.png",2,8);
    public static Symbol minusx8 =     new Symbol("-","fonts/8x/minus.png",3,8);
    public static Symbol plusx8 =      new Symbol("+","fonts/8x/plus.png",3,8);
    public static Symbol equalsx8 =    new Symbol("=","fonts/8x/equals.png",3,8);
    public static Symbol percentagex8= new Symbol("%","fonts/8x/percentage.png",3,8);
    public static Symbol commax8 =     new Symbol(",","fonts/8x/comma.png",2,8);
    public static Symbol spacex8 =     new Symbol(" ","fonts/8x/space.png",3,8);
    public static Symbol apostrophex8 =     new Symbol("'","fonts/8x/apostrophe.png",2,8);
    public static Symbol infinitex8 = new Symbol("~", "fonts/infinite.png", 8, 8);

    public static Symbol point5x8 =		initPoint5x8();
    public static Symbol slash5x8 = 	initSlash5x8();
    public static Symbol colon5x8 =     initColon5x8();
    public static Symbol moreThan5x8 =  initMoreThan5x8();
    public static Symbol lessThan5x8 =  initLessThan5x8();
    public static Symbol bracketopen5x8 = initBracketOpen5x8();
    public static Symbol bracketclose5x8 =initBracketClose5x8();
    public static Symbol minus5x8 =     initMinus5x8();
    public static Symbol plus5x8 =      initPlus5x8();
    public static Symbol equals5x8 =    initEquals5x8();
    public static Symbol percentage5x8= initPercentage5x8();
    public static Symbol comma5x8 =     initComma5x8();

    public static Symbol faith =    new Symbol("fonts/faith.png",5,8);
    public static Symbol faithmax = new Symbol("fonts/faithmax.png",5,8);
    public static Symbol halo =    new Symbol("fonts/halo.png",5,8);
    public static Symbol halomax =    new Symbol("fonts/halomax.png",5,8);
    public static Symbol mana = 	new Symbol("fonts/mana.png",5,8);
    public static Symbol manamax = 	new Symbol("fonts/manamax.png",5,8);
    public static Symbol manaregain = 	new Symbol("fonts/manaregain.png",5,8);
    public static Symbol life = new Symbol("fonts/life.png",5,8);
    public static Symbol lifemax = new Symbol("fonts/lifemax.png",5,8);
    public static Symbol liferegain = new Symbol("fonts/liferegain.png",5,8);
    public static Symbol turn = new Symbol("fonts/turn.png", 5,8);
    public static Symbol turnCD = new Symbol("fonts/turnCD.png", 5,8);
    public static Symbol shield = new Symbol("fonts/shield.png", 5,8);

    public static Symbol burning = new Symbol("icons/effect/burning.png", 8,8);
    public static Symbol doubleshot = new Symbol("icons/effect/doubleshot.png", 8,8);
    public static Symbol exalted = new Symbol("icons/effect/exalted.png", 8,8);
    public static Symbol frost = new Symbol("icons/effect/frost.png", 8,8);
    public static Symbol exhausted = new Symbol("icons/effect/exhausted.png", 8,8);
    public static Symbol immunity = new Symbol("icons/effect/immunity.png", 8,8);
    public static Symbol lifesteal = new Symbol("icons/effect/lifesteal.png", 8,8);
    public static Symbol scoped = new Symbol("icons/effect/scoped.png", 8,8);
    public static Symbol steadfast = new Symbol("icons/effect/steadfast.png", 8,8);
    public static Symbol threatening = new Symbol("icons/effect/threatening.png", 8,8);

    public static Symbol bleeding = new Symbol("icons/effect/bleeding.png", 8,8);
    public static Symbol dazed = new Symbol("icons/effect/dazed.png", 8,8);
    public static Symbol disenchanted = new Symbol("icons/effect/disenchanted.png", 8,8);
    public static Symbol injured = new Symbol("icons/effect/injured.png", 8,8);
    public static Symbol taunted = new Symbol("icons/effect/taunted.png", 8,8);
    public static Symbol _protected = new Symbol("icons/effect/invincible.png", 8,8);
    public static Symbol missingx8 = new Symbol("icons/effect/missing.png", 8,8);

    public static Symbol tank = new Symbol("icons/gui/tank.png", 8,8);
    public static Symbol fighter = new Symbol("icons/gui/fighter.png", 8,8);
    public static Symbol dps = new Symbol("icons/gui/dps.png", 8,8);
    public static Symbol support = new Symbol("icons/gui/support.png", 8,8);

    public static Symbol magic = new Symbol("fonts/magic.png", 8,8);
    public static Symbol finesse = new Symbol("fonts/finesse.png", 8,8);
    public static Symbol force = new Symbol("fonts/force.png", 8,8);
    public static Symbol endurance = new Symbol("fonts/endurance.png", 8,8);
    public static Symbol stamina = new Symbol("fonts/stamina.png", 8,8);
    public static Symbol speed = new Symbol("fonts/speed.png", 8,8);
    public static Symbol critchance = new Symbol("fonts/critchance.png", 8,8);
    public static Symbol evasion = new Symbol("fonts/evasion.png", 8,8);
    public static Symbol lethality = new Symbol("fonts/lethality.png", 8,8);
    public static Symbol accuracy = new Symbol("fonts/accuracy.png", 8,8);

    public static Symbol enemyTarget = new Symbol("fonts/enemytarget.png", 8,8);
    public static Symbol friendTarget = new Symbol("fonts/friendtarget.png", 8,8);
    public static Symbol otherTarget = new Symbol("fonts/othertarget.png", 8,8);
    public static Symbol enemyTargetAll = new Symbol("fonts/enemytargetall.png", 8,8);
    public static Symbol friendTargetAll = new Symbol("fonts/friendtargetall.png", 8,8);
    public static Symbol otherTargetAll = new Symbol("fonts/othertargetall.png", 8,8);
    public static Symbol emptyTarget = new Symbol("fonts/emptytarget.png", 8,8);

    public static Symbol smallNum1 = new Symbol(30,	0,2,	0,4,	SpriteSheet.smallNum);
    public static Symbol smallNum2 = new Symbol(30,	3,5,	0,4,	SpriteSheet.smallNum);
    public static Symbol smallNum3 = new Symbol(30,	6,8,	0,4,	SpriteSheet.smallNum);
    public static Symbol smallNum4 = new Symbol(30,	9,11,	0,4,	SpriteSheet.smallNum);
    public static Symbol smallNum5 = new Symbol(30,	12,14,	0,4,	SpriteSheet.smallNum);
    public static Symbol smallNum6 = new Symbol(30,	15,17,	0,4,	SpriteSheet.smallNum);
    public static Symbol smallNum7 = new Symbol(30,	18,20,	0,4,	SpriteSheet.smallNum);
    public static Symbol smallNum8 = new Symbol(30,	21,23,	0,4,	SpriteSheet.smallNum);
    public static Symbol smallNum9 = new Symbol(30,	24,26,	0,4,	SpriteSheet.smallNum);
    public static Symbol smallNum0 = new Symbol(30,	27,29,	0,4,	SpriteSheet.smallNum);
    public static Symbol smallNumSlash = new Symbol("fonts/smallNumSlash.png", 3, 5);
    public static Symbol smallNumBracketOpen = new Symbol("fonts/smallNumBracketOpen.png", 3, 5);
    public static Symbol smallNumBracketClose = new Symbol("fonts/smallNumBracketClose.png", 3, 5);
    public static Symbol smallNumPlus = new Symbol("fonts/smallNumPlus.png", 3, 5);

    public Symbol(int w, int h) {
        this.WIDTH = w;
        this.HEIGHT = h;
    }
    public Symbol(int sheetBaseWidth, int xfrom,int xuntil,int yfrom,int yuntil, SpriteSheet sheet) {
        this.WIDTH = (xuntil-xfrom)+1;
        this.HEIGHT = (yuntil-yfrom)+1;
        pixels = new int[this.WIDTH*this.HEIGHT];
        cutOutSymbol(sheetBaseWidth,sheet.getSprite(sheet.WIDTH, sheet.HEIGHT),xfrom,xuntil,yfrom,yuntil);
    }
    public Symbol(String code, int sheetBaseWidth, int xfrom,int xuntil,int yfrom,int yuntil, SpriteSheet sheet) {
        this.WIDTH = (xuntil-xfrom)+1;
        this.HEIGHT = (yuntil-yfrom)+1;
        pixels = new int[this.WIDTH*this.HEIGHT];
        cutOutSymbol(sheetBaseWidth,sheet.getSprite(sheet.WIDTH, sheet.HEIGHT),xfrom,xuntil,yfrom,yuntil);
        this.code = code;
    }
    public Symbol(int[] pixels, int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.pixels = pixels;
    }
    public Symbol(String path, int width, int height) {
        this.WIDTH=width;
        this.HEIGHT=height;
        pixels=new int[this.WIDTH*this.HEIGHT];
        getSymbol(path);
    }
    public Symbol(String code, String path, int width, int height) {
        this.WIDTH=width;
        this.HEIGHT=height;
        pixels=new int[this.WIDTH*this.HEIGHT];
        getSymbol(path);
        this.code = code;
    }
    public Symbol copy() {
        Symbol copy = new Symbol(this.WIDTH, this.HEIGHT);
        copy.pixels = this.pixels;
        copy.primaryColor = this.primaryColor;
        copy.secondaryColor = this.secondaryColor;
        copy.code = this.code;
        return copy;
    }
    private void getSymbol(String path) {
        try {
            URI uri = SpriteLibrary.class.getClassLoader().getResource(path).toURI();
            BufferedImage image = ImageIO.read(new File(uri));
            int width = image.getWidth();
            int height = image.getHeight();
            image.getRGB(0, 0, width, height, pixels, 0, width);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void cutOutSymbol(int sheetBaseWidth, int[] sheet, int xfrom,int xuntil,int yfrom,int yuntil) {
        int index = 0;
        for(int i = yfrom; i <= yuntil;i++) {
            for(int j = xfrom; j <= xuntil;j++) {
                int color = sheet[j + i * sheetBaseWidth];
                pixels[index] = color;
                index++;
            }
        }
    }

    private static Symbol initPoint5x8() {
        int[] pixels = new int[]{-12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -1,-12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784};
        return new Symbol(pixels, 5,8);
    }
    private static Symbol initSlash5x8() {
        int[] pixels = new int[]{	-12450784, -12450784, -12450784, -12450784,-1,
                -12450784, -12450784, -12450784, -12450784,-1,
                -12450784, -12450784, -12450784,-1, -12450784,
                -12450784, -12450784,-1, -12450784, -12450784,
                -12450784,-1, -12450784, -12450784, -12450784,
                -1, -12450784, -12450784, -12450784, -12450784,
                -1, -12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784};
        return new Symbol(pixels,5,8);
    }
    private static Symbol initBracketOpen5x8() {
        int[] pixels = new int[]{	-12450784, -12450784, -12450784, -12450784,-1,
                -12450784, -12450784, -12450784,-1, -12450784,
                -12450784, -12450784,-1, -12450784, -12450784,
                -12450784, -12450784,-1, -12450784, -12450784,
                -12450784, -12450784,-1, -12450784, -12450784,
                -12450784, -12450784, -12450784,-1, -12450784,
                -12450784, -12450784, -12450784, -12450784,-1,
                -12450784, -12450784, -12450784, -12450784, -12450784};
        return new Symbol(pixels,5,8);
    }
    private static Symbol initBracketClose5x8() {
        int[] pixels = new int[]{  -1, -12450784, -12450784, -12450784, -12450784,
                -12450784,-1, -12450784, -12450784, -12450784,
                -12450784, -12450784,-1, -12450784, -12450784,
                -12450784, -12450784,-1, -12450784, -12450784,
                -12450784, -12450784,-1, -12450784, -12450784,
                -12450784,-1, -12450784, -12450784, -12450784,
                -1, -12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784};
        return new Symbol(pixels,5,8);
    }
    private static Symbol initMinus5x8() {
        int[] pixels = new int[]{
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784,
               -1,-1,-1,-1,-1,
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784};
        return new Symbol(pixels,5,8);
    }
    private static Symbol initPlus5x8() {
        int[] pixels = new int[]{
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784,-1, -12450784, -12450784,
                -12450784, -12450784,-1, -12450784, -12450784,
                -1,-1,-1,-1,-1,
                -12450784, -12450784,-1, -12450784, -12450784,
                -12450784, -12450784,-1, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784};
        return new Symbol(pixels,5,8);
    }
    private static Symbol initEquals5x8() {
        int[] pixels = new int[]{
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -1,-1,-1,-1,-1,
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -1,-1,-1,-1,-1,
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784};
        return new Symbol(pixels,5,8);
    }
    private static Symbol initColon5x8() {
        int[] pixels = new int[]{
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784,-1, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784,-1, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784};
        return new Symbol(pixels,5,8);
    }
    private static Symbol initMoreThan5x8() {
        int[] pixels = new int[]{
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784,-1, -12450784, -12450784, -12450784,
                -12450784, -12450784,-1, -12450784, -12450784,
                -12450784, -12450784, -12450784,-1, -12450784,
                -12450784, -12450784,-1, -12450784, -12450784,
                -12450784,-1, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784};
        return new Symbol(pixels,8,7);
    }
    private static Symbol initLessThan5x8() {
        int[] pixels = new int[]{
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784,-1, -12450784,
                -12450784, -12450784,-1, -12450784, -12450784,
                -12450784,-1, -12450784, -12450784, -12450784,
                -12450784, -12450784,-1, -12450784, -12450784,
                -12450784, -12450784, -12450784,-1, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784};
        return new Symbol(pixels,8,7);
    }
    private static Symbol initPercentage5x8() {
        int[] pixels = new int[]{
                -12450784, -12450784, -12450784, -12450784, -1,
                -12450784, -1       , -12450784, -12450784, -1,
                -12450784, -12450784, -12450784, -1,        -12450784,
                -12450784, -12450784, -1       , -12450784, -12450784,
                -12450784, -1       , -12450784, -12450784, -12450784,
                -1       , -12450784, -12450784, -1       , -12450784,
                -1       , -12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784};
        return new Symbol(pixels,8,7);
    }
    private static Symbol initComma5x8() {
        int[] pixels = new int[]{
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784, -12450784, -12450784, -12450784, -12450784,
                -12450784, -1, -12450784, -12450784, -12450784,
                -1, -12450784, -12450784, -12450784, -12450784};
        return new Symbol(pixels, 5, 8);
    }

    @Override
    public String toString() {
        return code;
    }
}
