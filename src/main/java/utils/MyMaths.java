package utils;

import framework.states.Arena;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class MyMaths {
//    public static int damageRdmValue = 2;
    public static int dmgRdmPercentage = 15;
    public static int dmgEqualizer = 25;
    public static int getMirrorPos(int pos) {
        return Arena.lastEnemyPos - pos;
    }

    public static int getLevelStat(int baseStat, int level) {
        double multiplier = 0.12 * (level*level);
        int addition = (int) Math.round(multiplier*baseStat);
        return baseStat + addition;
    }

    public static int getPcProbability(int turn, int tier) {
        int m = 1;
        int t = 1;
        int apprTier = 3 - tier;
        return 2;
    }

    public static void simulateDmg() {
//        int att = 0;
//        int def = 0;
//        int armor = 0;
//        int lethal = 0;

        System.out.println("Early");
        System.out.println("att/def\t3\t6\t9\t12\t15\t18\t21");
        for (int att = 5; att <= 20; att+=3) {
            System.out.print(att + "\t\t");
            for (int def = 3; def <= 21; def+=3) {
                System.out.print(getDamage(att,def, 0, false)+"\t");
            }
            System.out.println();
        }

        System.out.println("Late");
        System.out.println("att/def\t10\t15\t20\t25\t30\t35\t40");
        for (int att = 10; att <= 40; att+=5) {
            System.out.print(att + "\t\t");
            for (int def = 10; def <= 40; def+=5) {
                System.out.print(getDamage(att,def, 0, false)+"\t");
            }
            System.out.println();
        }
//        for (int armor = 0; armor <= 40; armor+=5) {
//            System.out.println("Armor: " + armor);
//            System.out.println("att/def\t10\t15\t20\t25\t30\t35\t40");
//            for (int att = 10; att <= 50; att+=5) {
//                System.out.print(att + "\t\t");
//                for (int def = 10; def <= 40; def+=5) {
//                    System.out.print(getDamage(att,def,armor,0, false)+"\t");
//                }
//                System.out.println();
//            }
//        }

    }
    public static int getDamage(int att, int def, int lethal) {
        return getDamage(att, def, lethal, true);
    }
    public static int getDamage(int att, int def, int lethal, boolean rdmize) {
        int defense = def - (def*lethal/100);
        if (rdmize) {
            att = rdmize(att);
            defense = rdmize(defense);
        }
        return (int) (att * (dmgEqualizer / (dmgEqualizer + (double) defense)));
    }

    static private int rdmize(int a) {
        int rdmValue = a * dmgRdmPercentage / 100;
        return a + ThreadLocalRandom.current().nextInt(-1*rdmValue, rdmValue+1);

    }

    static public int[] getIntArrayWithExclusiveRandValues(int from, int until, int size) {
        if (size <= (until-from) + 1) {
            int[] result = new int[size];
            Random rand = new Random();
            int valuesFound = 0;
            while (valuesFound < size) {
                int tryInt = rand.nextInt(from, until+1);
                if (IntStream.of(result).noneMatch(x->x==tryInt)) {
                    result[valuesFound++] = tryInt;
                }
            }
            return result;
        } else {
            return new int[0];
        }
    }
    static public boolean success(int chance) {
        Random rand = new Random();
        float f = rand.nextFloat();
        if(f <= (chance/100.0)) {
            return true;
        }else {
            return false;
        }
    }

    static public int getFromToIncl(int from, int to, List<Integer> exclude) {
        Random rand = new Random();
        boolean found = false;
        if (to - from + 1 <= exclude.size()) {
            return 0;
        }
        int randomInt = 0;
        int counter = 0;
        while (!found && counter < 100) {
            randomInt = rand.nextInt(from, to + 1);
            if (!exclude.contains(randomInt)) {
                found = true;
            }
            counter++;
        }
        return randomInt;
    }
}
