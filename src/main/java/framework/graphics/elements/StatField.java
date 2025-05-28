package framework.graphics.elements;

import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import framework.graphics.text.TextAlignment;
import game.entities.Hero;
import game.skills.Stat;

public class StatField extends GUIElement {
    private final Hero hero;
    private Stat[] leftStatArray = new Stat[]{Stat.MAGIC, Stat.POWER, Stat.SPEED, Stat.EVASION, Stat.ACCURACY, Stat.CRIT_CHANCE, Stat.LETHALITY};
    private Stat[] rightStatArray = new Stat[]{Stat.STAMINA, Stat.ENDURANCE};

    public StatField(Hero hero) {
        this.hero = hero;
        this.setSize(98, 100);
    }

    public StatField(Hero hero, Stat[] lArray, Stat[] rArray) {
        this.hero = hero;
        this.setSize(98, 100);
        this.leftStatArray = lArray;
        this.rightStatArray = rArray;
    }

    @Override
    public int[] render() {
        background(Color.VOID);
        renderSide(leftStatArray, 3);
        renderSide(rightStatArray, this.width / 2);
        return pixels;
    }

    private void renderSide(Stat[] stats, int x) {
        int yf = 3;
        for (Stat stat : stats) {
            int totalValue = hero.getStat(stat);
            int statChange = hero.getStatChange(stat);
            int baseValue = totalValue - statChange;
            if (baseValue != 100 && (statChange != 0 || baseValue != 0)) {
                String baseStatString = stat.getReference() + ":" + baseValue;
                String statChangeString = "";
                if (statChange != 0) {
                    statChangeString = statChange > 0 ? "{006}+" : "{012}";
                    statChangeString += statChange;
                    statChangeString += "{000}";
                }
                baseStatString += statChangeString;

                int[] baseStatStringPixels = getTextLine(baseStatString, this.width / 2 - 5, 8, TextAlignment.LEFT, Color.VOID, Color.WHITE);
                fillWithGraphicsSize(x,yf,this.width / 2 - 5, 8, baseStatStringPixels, false);

                yf += 9;
            }

        }
    }
}
