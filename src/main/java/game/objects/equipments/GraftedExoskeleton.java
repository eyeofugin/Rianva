package game.objects.equipments;

import framework.graphics.text.TextEditor;
import game.objects.Equipment;
import game.objects.equipments.skills.S_GraftedExoskeleton;

import java.util.List;

//TODO: DT Changes
public class GraftedExoskeleton extends Equipment {

    public GraftedExoskeleton() {
        super("graftedexoskeleton", "Grafted Exoskeleton");
        this.statBonus = this.loadBaseStatBonus();
        this.adaptiveStats = List.of();
        this.skill = new S_GraftedExoskeleton(this);
    }

    @Override
    public String getDescription() {
        return "Active: Remove all debuffs. ["+TextEditor.TURN_KEY+"]4.";
    }
}
