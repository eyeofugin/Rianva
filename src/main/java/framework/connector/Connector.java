package framework.connector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Connector {

  public static String START_OF_MATCH = "START_OF_MATCH";
  public static String START_OF_ROUND = "START_OF_ROUND";
  public static String END_OF_ROUND = "END_OF_ROUND";
  public static String START_OF_TURN = "START_OF_TURN";
  public static String END_OF_TURN = "END_OF_TURN";

  public static String CAST_CHANGE = "CAST_CHANGE"; // Replacements like

  public static String BASE_STAT_CHANGE = "BASE_STAT_CHANGE";
  public static String STAT_CHANGE_MULT = "STAT_CHANGE_MULT";

  public static String BASE_STAT_PERMANENT_CHANGE = "BASE_STAT_PERMANENT_CHANGE";
  public static String STAT_PERMANENT_CHANGE_MULT = "STAT_PERMANENT_CHANGE_MULT";

  public static String CAN_PERFORM = "CAN_PERFORM";
  public static String ON_PERFORM = "ON_PERFORM";
  public static String ON_TARGET = "ON_TARGET";
  public static String TARGET_CHANGE = "TARGET_CHANGE";

  public static String BASE_DMG_CHANGES = "BASE_DMG_CHANGES";
  public static String BASE_HEAL_CHANGES = "BASE_HEAL_CHANGES";
  public static String BASE_ENERGY_CHANGES = "BASE_ENERGY_CHANGES";
  public static String BASE_SHIELD_CHANGES = "SHIELD_CHANGES";
  public static String BASE_REGAIN_CHANGES = "BASE_REGAIN_CHANGES";
  public static String BASE_EFFECT_DMG_CHANGES = "BASE_EFFECT_DMG_CHANGES";
  public static String DMG_CHANGES_MULT = "DMG_CHANGES_MULT";
  public static String HEAL_CHANGES_MULT = "HEAL_CHANGES_MULT";
  public static String ENERGY_CHANGES_MULT = "ENERGY_CHANGES_MULT";
  public static String SHIELD_CHANGES_MULT = "SHIELD_CHANGES_MULT";
  public static String EFFECT_DMG_CHANGES_MULT = "EFFECT_DMG_CHANGES_MULT";
  public static String REGAIN_CHANGES_MULT = "REGAIN_CHANGES_MULT";

  public static String BASE_RESOURCE_CHANGES = "BASE_RESOURCE_CHANGES";
  public static String RESOURCE_CHANGES_MULT = "RESOURCE_CHANGES_MULT";
  public static String EXCESS_RESOURCE = "EXCESS_RESOURCE";

  public static String DMG_TRIGGER = "DMG_TRIGGER";
  public static String DMG_TO_SHIELD = "DMG_TO_SHIELD";
  public static String SHIELD_BROKEN = "SHIELD_BROKEN";
  public static String EFFECT_DMG_TRIGGER = "EFFECT_DMG_TRIGGER";
  public static String CRITICAL_TRIGGER = "CRITICAL_TRIGGER";
  public static String DEATH_TRIGGER = "DEATH_TRIGGER";

  public static String GLOBAL_EFFECT_CHANGE = "GLOBAL_EFFECT_CHANGE";
  public static String EFFECT_FAILURE_CHECK = "EFFECT_FAILURE_CHECK";
  public static String EFFECT_ADDED = "EFFECT_ADDED";
  public static String EFFECT_REMOVED = "EFFECT_REMOVED";
  public static String EQUIPMENT_CHANGE_TRIGGER = "EQUIPMENT_CHANGE_TRIGGER";

  public static String ON_LEAVE = "ON_LEAVE";
  public static String ON_ENTER = "ON_ENTER";

  public static Map<String, ArrayList<Connection>> subscriptions = new HashMap<>();

  public static void addSubscription(String topic, Connection connection) {
    if (subscriptions.containsKey(topic)) {
      subscriptions.get(topic).add(connection);
    } else {
      ArrayList<Connection> newList = new ArrayList<>();
      newList.add(connection);
      subscriptions.put(topic, newList);
    }
  }

  public static void removeSubscriptions(Object obj) {
    if (obj == null) {
      subscriptions = new HashMap<>();
    }
    for (Map.Entry<String, ArrayList<Connection>> topicSubscription : subscriptions.entrySet()) {
      topicSubscription
          .getValue()
          .removeIf(
              connection ->
                  connection.element == null || (connection.equals(obj) && !connection.persistant));
    }
  }

  public static void cleanUpSubscriptions() {
    for (Map.Entry<String, ArrayList<Connection>> topicSubscription : subscriptions.entrySet()) {
      topicSubscription.getValue().removeIf(connection -> connection.element == null);
    }
  }

  public static void fireTopic(String topic, ConnectionPayload payload) {
    if (subscriptions.containsKey(topic)) {
      for (Connection connection : subscriptions.get(topic)) {
        try {
          Method method =
              connection
                  .element
                  .getClass()
                  .getMethod(connection.methodName, ConnectionPayload.class);
          method.invoke(connection.element, payload);
        } catch (NoSuchMethodException e) {
          System.out.println("Hä" + connection.methodName);
        } catch (InvocationTargetException e) {
          System.out.println("Hä2" + connection.methodName);
        } catch (IllegalAccessException e) {
          System.out.println("Hä3" + connection.methodName);
        }
      }
    }
  }
}
