package game.skills;

import framework.Property;
import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import game.entities.Hero;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.effects.Bleeding;
import game.skills.changeeffects.effects.Dazed;
import game.skills.changeeffects.effects.Disenchanted;
import game.skills.changeeffects.effects.Injured;
import game.skills.changeeffects.effects.Taunted;

import java.util.List;
import java.util.Random;

public abstract class Effect {

    public enum ChangeEffectType {
        BUFF,
        DEBUFF,
        STAT_CHANGE,
        OTHER;
    }

    public static String ICON_STRING = "%%%";
    public String iconString;
    public int turns = -1;
    public int stacks = 1;
    public Hero origin;
    public Hero hero;

    public String name;
    public String description;
    public boolean stackable;
    public ChangeEffectType type;
    public Stat stat;
    public int statChange;
    public double statChangePercentage;

    public abstract Effect getNew();
    public void addSubscriptions() {

    }
    public void turnLogic() {}

    public void addToHero(){
        if (statChange != 0) {
            hero.addToStat(stat, statChange);
        } else if (statChangePercentage > 0) {
            hero.addToStat(stat, (int)(hero.getStat(stat) * statChangePercentage));
        }
        addSubscriptions();
    }
    public void addStack(int stacks){
        this.stacks+=stacks;
    }
    public void removeEffect() {
        if (statChange != 0) {
            hero.addToStat(stat, (-1) * statChange);
        }
        for (int i = 0; i < stacks; i++) {
            removeStack();
        }

    }
    public void removeStack() {
        this.stacks--;
    }

    public void turn() {
        if (turns == -1) {
            turnLogic();
            return;
        }
        if(turns>0) {
            this.turns--;
            turnLogic();
        }
    }
    public int getDamageChanges(Hero caster, Hero target, Skill damagingSkill, int result, boolean simulated) {
        return result;
    }

    public void addStacksToSprite(int[] effectSprite) {
        if (stackable) {
            int x = Property.EFFECT_ICON_SIZE - 2;
            for (int i = 0; i < this.stacks; i++) {
                GUIElement.verticalLine(x, Property.EFFECT_ICON_SIZE - 3,Property.EFFECT_ICON_SIZE - 1, Property.EFFECT_ICON_SIZE, effectSprite, Color.RED);
                x-=2;
            }
        }
    }

    public String getDetailInfo() {
        if (stackable) {
            return "Stacks:" + this.stacks + " ";
        }
        if (turns > 0) {
            return "Turns:" + this.turns + " ";
        }
        return "";

    }

    public String getIconString() {
        return "[" + this.iconString + "]";
    }
    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }

    @Override
    public String toString() {
        return "Effect{" +
                "turns=" + turns +
                ", stacks=" + stacks +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }

    public static Effect getRdmDebuff() {
        List<Effect> effectList = List.of(new Burning(1), new Injured(1), new Bleeding(1), new Dazed(1), new Disenchanted(1), new Taunted(1));
        Random r = new Random();
        return effectList.get(r.nextInt(effectList.size()));
    }
}