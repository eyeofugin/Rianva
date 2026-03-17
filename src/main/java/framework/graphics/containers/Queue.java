package framework.graphics.containers;

import framework.Property;
import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import framework.resources.SpriteLibrary;
import game.entities.Hero;
import utils.HeroQueue;

import java.util.Iterator;
import java.util.List;

public class Queue extends GUIElement {

  private final HeroQueue queue = new HeroQueue();
  private static final int SPACE_WIDTH = 3;
  private static final int ARROW_WIDTH = 12;

  public Queue() {
    super(Property.QUEUE_WIDTH, Property.QUEUE_HEIGHT);
    this.x = Property.QUEUE_X;
    this.y = Property.QUEUE_Y;
  }

  public int[] render() {
    background(Color.VOID);
    int portraits = this.queue.doneThisTurn.size() + this.queue.upThisTurn.size();
    int doneSpaces = Math.max(this.queue.doneThisTurn.size() - 1, 0);
    int upSpaces = Math.max(this.queue.upThisTurn.size() - 1, 0);
    int totalSpaces = doneSpaces + upSpaces;
    int currentWidth = ARROW_WIDTH + totalSpaces * SPACE_WIDTH + portraits * Property.PORTRAIT_SIZE;

    int xOff = (this.width - currentWidth) / 2;

    Iterator<Hero> doneThisTUrn = queue.doneThisTurn.iterator();
    while (doneThisTUrn.hasNext()) {
      Hero hero = doneThisTUrn.next();
      int[] heroPortrait =
          getTextLine(
              hero.getName().substring(0, 2),
              Property.PORTRAIT_SIZE,
              Property.PORTRAIT_SIZE,
              Color.WHITE);
      this.fillWithGraphicsSize(
          xOff, 0, Property.PORTRAIT_SIZE, Property.PORTRAIT_SIZE, heroPortrait, Color.WHITE);
      if (doneThisTUrn.hasNext()) {
        xOff += Property.PORTRAIT_SIZE + SPACE_WIDTH;
      } else {
        xOff += Property.PORTRAIT_SIZE;
      }
    }

    int[] arrow = SpriteLibrary.getSprite("arrow_right");
    this.fillWithGraphicsSize(xOff, 4, 12, 12, arrow, false);
    xOff += 12;

    Iterator<Hero> upThisTurn = queue.upThisTurn.iterator();
    while (upThisTurn.hasNext()) {
      Hero hero = upThisTurn.next();
      int[] heroPortrait =
          getTextLine(
              hero.getName().substring(0, 2),
              Property.PORTRAIT_SIZE,
              Property.PORTRAIT_SIZE,
              Color.WHITE);
      this.fillWithGraphicsSize(
          xOff, 0, Property.PORTRAIT_SIZE, Property.PORTRAIT_SIZE, heroPortrait, Color.WHITE);
      if (upThisTurn.hasNext()) {
        xOff += Property.PORTRAIT_SIZE + SPACE_WIDTH;
      } else {
        xOff += Property.PORTRAIT_SIZE;
      }
    }

    return pixels;
  }

  public boolean heroIsUp(Hero hero) {
    return this.queue.upThisTurn.contains(hero);
  }
  public boolean hasHeroUp() {
    return this.queue.hasHeroUp();
  }

  public Hero peek() {
    return this.queue.peek();
  }

  public void addAll(List<Hero> backwards) {
    this.queue.addAll(backwards);
  }

  public void removeAll(List<Hero> heroes) {
    this.queue.removeAll(heroes);
  }

  public void sendToBack(Hero activeHero) {
    this.queue.sendToBack(activeHero);
  }

  public void addToBack(Hero hero) {
    this.queue.addToBack(hero);
  }

  public void didTurn(Hero activeHero) {
    this.queue.didTurn(activeHero);
  }

  public void restartRoundQueue() {
    this.queue.restartTurnQueue();
  }
}
