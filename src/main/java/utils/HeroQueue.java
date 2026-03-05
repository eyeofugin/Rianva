package utils;

import game.entities.Hero;
import game.skills.logic.Stat;

import java.util.ArrayList;
import java.util.List;

public class HeroQueue {
  public List<Hero> upThisTurn = new ArrayList<>();
  public List<Hero> doneThisTurn = new ArrayList<>();

  public boolean hasHeroUp() {
    return !this.upThisTurn.isEmpty();
  }

  public Hero peek() {
    return this.upThisTurn.getFirst();
  }

  public void addAll(List<Hero> heroes) {
    this.upThisTurn.addAll(heroes);
  }

  public void removeAll(List<Hero> heroes) {
    this.upThisTurn.removeAll(heroes);
    this.doneThisTurn.removeAll(heroes);
  }

  public void sendToBack(Hero hero) {
    if (this.upThisTurn.contains(hero)) {
      this.upThisTurn.remove(hero);
      this.upThisTurn.add(hero);
    }
  }

  public void addToBack(Hero hero) {
    this.upThisTurn.add(hero);
  }

  public void didTurn(Hero activeHero) {
    this.upThisTurn.remove(activeHero);
    if (!this.doneThisTurn.contains(activeHero)) {
      this.doneThisTurn.add(activeHero);
    }
  }

  public void restartTurnQueue() {
    doneThisTurn.removeIf(hero -> !hero.isAlive());
    this.upThisTurn.addAll(
        doneThisTurn.stream()
            .sorted(
                (Hero e1, Hero e2) ->
                    Integer.compare(e2.getStat(Stat.DEXTERITY), e1.getStat(Stat.DEXTERITY)))
            .toList());
    this.doneThisTurn = new ArrayList<>();
  }
}
