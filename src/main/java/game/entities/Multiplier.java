package game.entities;

import game.skills.logic.Stat;

public class Multiplier {
  public Stat prof;
  public double percentage;

  public Multiplier() {}

  public Multiplier(Stat prof, double percentage) {
    this.prof = prof;
    this.percentage = percentage;
  }

  public Multiplier copy() {
    return new Multiplier(prof, percentage);
  }
}
