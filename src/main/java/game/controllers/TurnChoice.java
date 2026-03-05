package game.controllers;

import game.entities.Hero;
import game.skills.Skill;

public class TurnChoice {
  public Skill s;
  public Hero[] targets;
  public int rating;

  public TurnChoice(Skill s, Hero[] targets, int rating) {
    this.s = s;
    this.targets = targets;
    this.rating = rating;
  }
}
