package framework.states;

import framework.Engine;
import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import framework.resources.SpriteLibrary;
import game.dungeon.DungeonRoom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dungeon extends State {

  private DungeonRoom[][] rooms;

  private int teamX = 0;
  private int teamY = 0;

  public boolean startEncounter = false;
  public boolean startLoot = false;

  public Dungeon(Memory memory) {
    super(memory);
    this.id = StateManager.DUNGEON;

    this.buildRoom();
  }

  private void buildRoom() {
    this.rooms = new DungeonRoom[4][4];
    List<Integer> xValues = new ArrayList<>(List.of(0, 1, 2, 3));
    List<Integer> yValues = new ArrayList<>(List.of(0, 1, 2, 3));
    Random rand = new Random();
    int xIndex = rand.nextInt(0, xValues.size());
    int yIndex = rand.nextInt(0, yValues.size());
    this.teamX = xValues.get(xIndex);
    this.teamY = yValues.get(yIndex);
    xValues.remove(xIndex);
    yValues.remove(yIndex);
    int bossX = rand.nextInt(0, xValues.size());
    int bossY = rand.nextInt(0, yValues.size());
    rooms[bossX][bossY] = DungeonRoom.getBossRoom();
    rooms[this.teamX][this.teamY] = DungeonRoom.getStartRoom();

    for (int y = 0; y < 4; y++) {
      for (int x = 0; x < 4; x++) {
        if (rooms[x][y] == null) {
          int roomType = rand.nextInt(10);
          if (roomType < 1) {
            rooms[x][y] = DungeonRoom.getLootRoom();
          } else if (roomType < 3) {
            rooms[x][y] = DungeonRoom.getMoneyRoom();
          } else if (roomType < 4) {
            rooms[x][y] = DungeonRoom.getRandomRoom();
          } else if (roomType < 7) {
            rooms[x][y] = DungeonRoom.getEncounterRoom(memory.dungeonLevel);
          } else {
            rooms[x][y] = DungeonRoom.getEmptyRoom();
          }
        }
      }
    }
    this.seeRooms();
  }

  @Override
  public void update(int frame) {
    if (active) {
      updateKeys();
    }
  }

  private void updateKeys() {
    if (Engine.KeyBoard._leftPressed) {
      if (this.teamX != 0) {
        this.teamX--;
      }
    }
    if (Engine.KeyBoard._rightPressed) {
      if (this.teamX != 3) {
        this.teamX++;
      }
    }
    if (Engine.KeyBoard._upPressed) {
      if (this.teamY != 0) {
        this.teamY--;
      }
    }
    if (Engine.KeyBoard._downPressed) {
      if (this.teamY != 3) {
        this.teamY++;
      }
    }
    this.seeRooms();
    if (!this.rooms[this.teamX][this.teamY].activated) {
      this.effect();
    }
  }

  private void seeRooms() {
    try {
      if (this.teamX != 0) {
        this.rooms[this.teamX - 1][this.teamY].visible = true;
      }
      if (this.teamX != 3) {
        this.rooms[this.teamX + 1][this.teamY].visible = true;
      }
      if (this.teamY != 0) {
        this.rooms[this.teamX][this.teamY - 1].visible = true;
      }
      if (this.teamY != 3) {
        this.rooms[this.teamX][this.teamY + 1].visible = true;
      }
      this.rooms[this.teamX][this.teamY].visible = true;
    } catch (ArrayIndexOutOfBoundsException e) {

    }
  }

  private void effect() {
    DungeonRoom room = this.rooms[this.teamX][this.teamY];
    room.activated = true;
    if (room.getLoot() != null) {
      this.memory.dungeonLoot = room.getLoot();
      this.startLoot = true;
    } else if (room.getEnemies() != null) {
      this.memory.dungeonEnemies = room.getEnemies();
      this.memory.bossEncounter = room.isBoss();
      this.startEncounter = true;
    }
  }

  @Override
  public int[] render() {
    background(Color.BLACK);
    renderRooms();
    return this.pixels;
  }

  private void renderRooms() {
    for (int x = 0; x < 4; x++) {
      for (int y = 0; y < 4; y++) {
        int xFrom = 100 + (32 * x);
        int yFrom = 100 + (32 * y);
        DungeonRoom room = this.rooms[x][y];
        int[] sprite;
        if (!room.visible) {
          sprite = SpriteLibrary.getSprite("notseenyet").clone();
        } else {
          sprite =
              (x == teamX && y == teamY)
                  ? SpriteLibrary.getSprite("group").clone()
                  : room.getIcon();
        }
        fillWithGraphicsSize(xFrom, yFrom, 32, 32, sprite, false);
      }
    }
  }
}
