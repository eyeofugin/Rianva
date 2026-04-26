package framework.graphics.containers;

import framework.Engine;
import framework.Property;
import framework.graphics.GUIElement;
import framework.graphics.elements.LogStatement;
import framework.graphics.text.Color;

import java.util.Random;
import java.util.Stack;

public class LogCard extends GUIElement {
  String currentLog = "";
  Stack<String> logs = new Stack<>();
  final int padding = 10;
  int tempY = 0;

  public LogCard() {
    super(Property.LOG_WIDTH, Property.LOG_HEIGHT);
    this.x = Property.LOG_X;
    this.y = Property.HUD_BOXES_Y;
    this.lazy = true;
  }

  public void finishLog() {
    if (!this.currentLog.isEmpty()) {
      this.logs.add(this.currentLog);
      this.currentLog = "";
      if (this.logs.size() > 5) {
        this.logs.remove(0);
      }
    }
  }

  public void openLog(String newLog) {
    if (!this.currentLog.isEmpty()) {
      this.logs.add(this.currentLog);
    }
    this.currentLog = newLog;
  }

  public void addToLog(String logStatement) {
    this.currentLog += logStatement;
  }

  public void addLog(String logStatement) {
    this.logs.add(logStatement);
  }

  @Override
  public void update(int frame) {
    if (Engine.KeyBoard._lPressed) {
      logs.pop();
    }
  }

  @Override
  public int[] render() {
    if (Engine.redraw) {
      clear();
      background(new Random().nextInt(10000));
      renderVisibleLogs();
      //        renderXButton();
      if (this.active) {
        addBorder(this.width, this.height, this.pixels, Color.WHITE);
      }
      this.g = this.pixels;
      return this.pixels;
    } else {
      return g;
    }
  }

  private void renderVisibleLogs() {
    tempY = padding;

    for (int i = this.logs.size() - 1; i >= 0; i--) {
      LogStatement log = new LogStatement(this.logs.get(i));
      if (tempY + log.getHeight() < this.height - padding) {
        fillWithGraphicsSize(
            padding, tempY, Property.LOG_STATEMENT_WIDTH, log.getHeight(), log.render(), Color.WHITE);
        tempY += log.getHeight() + 5;
      }
    }
  }
}
