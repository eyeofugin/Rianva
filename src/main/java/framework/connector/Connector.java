package framework.connector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Connector {

    public static final String EXCESS_RESOURCE = "EXCESS_RESOURCE";
    public static final String STAT_CHANGE =  "STAT_CHANGE";
    public static String GLOBAL_EFFECT_CHANGE = "GLOBAL_EFFECT_CHANGE";
    public static boolean active = true;
    public static String CAST_CHANGE = "CAST_CHANGE"; //Replacements like
    public static String TARGET_MODE = "TARGET_MODE"; //return the target viability of a skill's target
    public static String CAN_PERFORM = "CAN_PERFORM";
    public static String ON_PERFORM = "ON_PERFORM";
    public static String IS_TARGETED = "IS_TARGETED";
    public static String EOT_HEAL_CHANGES = "EOT_HEAL_CHANGES";
    public static String BASE_HEAL_CHANGES ="BASE_HEAL_CHANGES"; //Before defense calc
    public static String BASE_DMG_CHANGES ="BASE_DMG_CHANGES"; //Before defense calc
    public static String CRITICAL_TRIGGER = "CRITICAL_TRIGGER";
    public static String DMG_CHANGES = "DMG_CHANGES"; //Dmg actually done, after def calc
    public static String EFFECT_DMG_CHANGES = "EFFECT_DMG_CHANGES"; //Dmg actually done, after def calc
    public static String DMG_TRIGGER = "DMG_TRIGGER";
    public static String EFFECT_DMG_TRIGGER = "EFFECT_DMG_TRIGGER";
    public static String HEAL_CHANGES = "HEAL_CHANGES";
    public static String EFFECT_FAILURE = "EFFECT_FAILURE";
    public static String EFFECT_ADDED = "EFFECT_ADDED";
    public static String ACTION_INFLICTION = "ACTION_INFLICTION";
    public static String SHIELD_BROKEN = "SHIELD_BROKEN";
    public static String DMG_TO_SHIELD = "DMG_TO_SHIELD";
    public static String SHIELD_CHANGES = "SHIELD_CHANGES";
    public static String EQUIPMENT_CHANGE_TRIGGER = "EQUIPMENT_CHANGE_TRIGGER";
    public static String MANA_REGAIN = "MANA_REGAIN";
    public static String UPDATE = "UPDATE";
    public static String START_OF_TURN = "START_OF_TURN";
    public static String END_OF_ROUND = "END_OF_TURN";
    public static String START_OF_MATCH = "START_OF_MATCH";
    public static String DEATH_TRIGGER = "DEATH_TRIGGER";

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
            topicSubscription.getValue().removeIf(connection -> connection.element == null || (connection.equals(obj) && !connection.persistant));
        }
    }
    public static void cleanUpSubscriptions() {
        for (Map.Entry<String, ArrayList<Connection>> topicSubscription : subscriptions.entrySet()) {
            topicSubscription.getValue().removeIf(connection -> connection.element == null);
        }
    }
    public static void fireTopic(String topic, ConnectionPayload payload) {
        if (Connector.active && subscriptions.containsKey(topic)) {
            Iterator<Connection> iter = subscriptions.get(topic).iterator();
            while(iter.hasNext()) {
                Connection connection = iter.next();
                try {
                    Method method = connection.element.getClass().getMethod(connection.methodName, connection.payloadClass);
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
