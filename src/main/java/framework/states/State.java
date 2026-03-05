package framework.states;

import framework.Engine;
import framework.graphics.GUIElement;

public class State extends GUIElement {

  boolean finished = false;
  boolean close = false;
  boolean goBack = false;
  boolean started = false;
  public final Memory memory;
  public int nextState;

  public State(Memory memory) {
    super(Engine.X, Engine.Y);
    this.memory = memory;
  }

  public void start() {}

  public void finish() {}
}
