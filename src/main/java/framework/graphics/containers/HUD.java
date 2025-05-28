package framework.graphics.containers;

import framework.Engine;
import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import framework.states.Arena;
import framework.states.Draft;
import framework.states.PvpDraft;
import game.entities.Hero;


public class HUD extends GUIElement {
    Engine e;
    Arena arena;
    Draft draft;
    PvpDraft pvpDraft;
    TeamArenaOverview arenaOv;
    TeamDraftOverview draftOV;
    TeamPvpDraftOverview pvpDraftOV;

    public HUD(Engine e) {
        super(Engine.X, Engine.Y);
        this.e = e;
    }

    public void update(int frame) {
        if (this.arenaOv!=null){
            this.arenaOv.update(frame);
        }
        if (this.draftOV!=null){
            this.draftOV.update(frame);
        }
    }
    public int[] render() {
        background(Color.VOID);
        renderChildren();
        return this.pixels;
    }
    public void setArena(Arena arena) {
        this.arena = arena;
        this.arenaOv = new TeamArenaOverview(e, arena);
        this.children.add(arenaOv);
    }
    public void setDraft(Draft draft) {
        this.draft = draft;
        this.draftOV = new TeamDraftOverview(e, draft);
        this.children.add(draftOV);
    }
    public void setPvpDraft(PvpDraft pvpDraft) {
        this.pvpDraft = pvpDraft;
        this.pvpDraftOV = new TeamPvpDraftOverview(e, draft);
        this.children.add(pvpDraftOV);
    }
    public void setActiveHero(Hero e) {
        if (this.arenaOv != null) {
            this.arenaOv.setActiveHero(e);
        }
        if (this.draftOV != null) {
            this.draftOV.setActiveHero(e);
        }
    }
    public void activateTeamArenaOv() {
        this.arenaOv.activate();
    }
    public void disableTeamArenaOV() {
        this.arenaOv.deactivate();
    }
    public void activateTeamDraftOV() {
        this.draftOV.activate();
    }
}
