package framework.graphics.containers;

import framework.Engine;
import framework.graphics.GUIElement;
import framework.states.Arena;
import framework.graphics.text.Color;
import game.entities.Hero;

public class TeamArenaOverview extends GUIElement {
  public Arena arena;

  ActiveCharCard activeCharCard;
  ActiveAbilitiesCard activeAbilitiesCard;

  public int cardPointer = 1;

  public TeamArenaOverview(Arena arena) {
    super(Engine.X, Engine.Y);
    this.simpleBorder = false;
    this.arena = arena;
    this.active = false;
    this.activeCharCard = new ActiveCharCard(arena);
    this.activeAbilitiesCard = new ActiveAbilitiesCard(arena);
    this.children.add(activeCharCard);
    this.children.add(activeAbilitiesCard);
  }

  @Override
  public void update(int frame) {
    if (this.active) {
      deactivateAllChildren();
      if (Engine.KeyBoard._shoulderLeftPressed) {
        if (cardPointer != 0) {
          cardPointer--;
        }
      }
      if (Engine.KeyBoard._shoulderRightPressed) {
        if (cardPointer != 2) {
          cardPointer++;
        }
      }
      switch (cardPointer) {
        case 0:
          this.activeCharCard.setActive(true);
          break;
        case 1:
          this.activeAbilitiesCard.setActive(true);
          break;
      }
      updateChildren(frame);
    }
  }

  public void setActiveHero(Hero e) {
    this.activeCharCard.setActiveHero(e);
    this.activeAbilitiesCard.setActiveHero(e);
  }

  public void activate() {
    this.active = true;
  }

  public void deactivate() {
    this.active = false;
  }

  @Override
  public int[] render() {
    background(Color.VOID);
    renderChildren();
    return this.pixels;
  }
}
