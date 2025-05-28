package utils;

import game.entities.Hero;
import game.skills.Skill;

public class Action {
    public int rating;
    public Skill skill;
    public Hero caster;
    public Hero[] targets;
    public int[] targetPositions;

    public Action() {

    }
    public Action(int rating, Skill skill, Hero caster, Hero[] targets, int[] targetPositions) {
        this.rating = rating;
        this.skill = skill;
        this.caster = caster;
        this.targets = targets;
        this.targetPositions = targetPositions;
    }
}
