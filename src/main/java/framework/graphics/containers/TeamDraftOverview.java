package framework.graphics.containers;

import framework.Engine;
import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import framework.states.Draft;
import game.entities.Hero;

public class TeamDraftOverview extends GUIElement {
    public Engine engine;
    public Draft draft;

    ActiveCharCard activeCharCard;
    ActiveAbilitiesCard activeAbilitiesCard;

    public int cardPointer = 1;

    public TeamDraftOverview(Engine e, Draft draft) {
        super(Engine.X, Engine.Y);
        this.engine = e;
        this.simpleBorder = false;
        this.draft = draft;
        this.active = false;
        this.activeCharCard = new ActiveCharCard(e, draft);
        this.activeAbilitiesCard = new ActiveAbilitiesCard(e, draft);
        this.children.add(activeCharCard);
        this.children.add(activeAbilitiesCard);
    }

    @Override
    public void update(int frame) {
        if (this.active) {
            if (engine.keyB._shoulderLeftPressed) {
                if (cardPointer != 0) {
                    cardPointer--;
                }
            }
            if (engine.keyB._shoulderRightPressed) {
                if (cardPointer != 1) {
                    cardPointer++;
                }
            }
            deactivateAllChildren();
            if (engine.keyB._backPressed) {
                this.active = false;
                this.draft.setActive(true);
                return;
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
