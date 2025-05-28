package framework;

public class Logger {
    private static final boolean DEBUG = false;
    private static final boolean AIDEBUG = true;

    public static void logLn(String s) {
        if (DEBUG) {
            System.out.println(s);
        }
    }

    public static void log(String s) {
        if (DEBUG) {
            System.out.print(s);
        }
    }

    public static void aiLog(String s) {
        if (AIDEBUG) {
            System.out.print("DEBUG" + s);
        }
    }

    public static void aiLogln(String s) {
        if (AIDEBUG) {
            System.out.println("AIDEBUG" + s);
        }
    }

    public static void printIcon(int[] icon) {
        for ( int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                System.out.print(icon[j+i*16] == -1? "X": ".");
            }
            System.out.println();
        }
    }
}
