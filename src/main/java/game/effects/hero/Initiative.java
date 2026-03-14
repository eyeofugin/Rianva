package game.effects.hero;

import framework.connector.ConnectionPayload;
import game.effects.Effect;

public class Initiative extends Effect {
  public void endOfTurn(ConnectionPayload pl) {
    this.hero.arena.extraTurn(this.hero);
    this.used = true;
  }
}
