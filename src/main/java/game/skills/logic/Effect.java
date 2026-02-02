package game.skills.logic;

import framework.Property;
import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.changeeffects.effects.other.Burning;
import game.skills.changeeffects.effects.status.Bleeding;
import game.skills.changeeffects.effects.status.Dazed;
import game.skills.changeeffects.effects.other.Disenchanted;
import game.skills.changeeffects.effects.status.Injured;
import game.skills.changeeffects.effects.status.Taunted;

import java.util.List;
import java.util.Random;

public class Effect {

    public enum ChangeEffectType {
        BUFF,
        DEBUFF,
        STATUS,
        OTHER;
    }

    public static String ICON_STRING = "%%%";
    public String iconString;
    public int turns = -1;
    public int stacks = 1;
    public Hero origin;
    public Hero hero;

    public Condition condition;

    public String negates;
    public String name;
    public String description;
    public boolean stackable;
    public ChangeEffectType type;
    public Stat stat;
    public double statChangePercentage;

    public Effect(EffectDTO dto) {
        this.name = dto.name;
        this.iconString = dto.iconString;
        this.description = dto.description;
        this.stackable = dto.stackable;
        this.type = dto.type;
        this.stat = dto.stat;
        this.statChangePercentage = dto.statChangePercentage;
        this.negates = dto.negates;
    }

    public Effect() {
        
    }
    public Effect getNew(){
        return null;
    };
    public void addSubscriptions() {

    }
    public void turnLogic() {}

    public void addToHero(){
         if (statChangePercentage != 0) {
            hero.addToStat(stat, (int)(hero.getStat(stat) * statChangePercentage));
        }
        addSubscriptions();
    }
    public void addStack(int stacks){
        this.stacks+=stacks;
    }
    public void removeEffect() {
        if (statChangePercentage != 0) {
            hero.addToStat(stat, (int)(hero.getStat(stat) / statChangePercentage));
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