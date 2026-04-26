package framework.graphics.elements;

import framework.Engine;
import framework.Property;
import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import framework.resources.SpriteLibrary;
import game.entities.Hero;
import game.objects.Equipment;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class ItemField extends GUIElement {
  private Hero hero;
  private int pointer = 0;

  private static final int EQUIPMENT_SIZE = 18;
  private static final int ICONS_Y = 2;
  private static final int AMOUNT_VISIBLE_ITEMS = 4;
  private static final int WIDTH = 98;
  private static final int EQUIPMENT_INFO_Y = ICONS_Y + EQUIPMENT_SIZE + 5;
  private static final int[] ICON_X_POSITIONS = new int[] {2, 25, 55, 78};

  private GUIElement[] itemIcons;
  ItemInfo itemInfo;


  public ItemField(Hero hero) {
    this.hero = hero;
    this.itemIcons = new GUIElement[0];
    this.setSize(WIDTH, 100);
    recalculateItemList(0);
  }

  @Override
  public void update(int frame) {
    if (this.active) {
      if (!this.hero.getEquipments().isEmpty()) {
        if (Engine.KeyBoard._leftPressed) {
          recalculateItemList(-1);
        }

        if (Engine.KeyBoard._rightPressed) {
          recalculateItemList(1);
        }
      }
    }
  }

  private void recalculateItemList(int dir) {
    //        this.children.remove()
    List<Equipment> equipments = this.hero.getEquipments();
    setNextPointer(equipments.size(), dir);

    setEquipments();

    setEquipmentInfo();
  }

  private void setNextPointer(int equipmentsSize, int dir) {
    int newPointer = this.pointer + dir;
    int amountIcons = Math.min(AMOUNT_VISIBLE_ITEMS, equipmentsSize);
    if (amountIcons == 0) {
      return;
    }
    if (newPointer >= 0 && newPointer < amountIcons) {
      this.pointer = newPointer;
    }
  }

  private void setEquipments() {
    List<Equipment> equipments = this.hero.getEquipments();
    this.itemIcons = new GUIElement[equipments.size()];
    for (int i = 0; i < this.itemIcons.length; i++) {
      Equipment equipment = this.hero.getEquipments().get(i);
      if (equipment != null) {
        int[] pixels = new int[EQUIPMENT_SIZE * EQUIPMENT_SIZE];
        Arrays.fill(pixels, Color.VOID.VALUE);
        if (this.pointer == i) {
          GUIElement.addBorder(EQUIPMENT_SIZE, EQUIPMENT_SIZE, pixels, Color.WHITE);
        }
        GUIElement.staticFillSize(
            1,
            1,
            Property.EQUIPMENT_ICON_SIZE,
            Property.EQUIPMENT_ICON_SIZE,
            EQUIPMENT_SIZE,
            pixels,
            equipment.getIcon());
        GUIElement equipmentElement = new GUIElement();
        equipmentElement.setSize(EQUIPMENT_SIZE, EQUIPMENT_SIZE);
        equipmentElement.setPosition(ICON_X_POSITIONS[i], ICONS_Y);
        equipmentElement.setPixels(pixels);
        this.itemIcons[i] = equipmentElement;
      }
    }
  }

  private void setEquipmentInfo() {
    if (this.pointer < this.hero.getEquipments().size()) {
      Equipment equipment = this.hero.getEquipments().get(this.pointer);
      this.itemInfo = new ItemInfo(equipment);
      this.itemInfo.setPosition(2, EQUIPMENT_INFO_Y);
      this.children.add(this.itemInfo);
    }
  }

  @Override
  public int[] render() {
    background(Color.VOID);
    renderChildren();
    renderEquipments();
    return this.pixels;
  }

  private void renderEquipments() {
    for (GUIElement item : this.itemIcons) {
      fillWithGraphicsSize(
          item.getX(), item.getY(), item.getWidth(), item.getHeight(), item.render(), false);
    }
  }
}
