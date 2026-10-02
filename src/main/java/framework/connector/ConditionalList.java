package framework.connector;

import java.util.HashSet;
import java.util.List;

public class ConditionalList<T> {
    public BoolLogic boolLogic;
    public List<T> objects;

    public boolean isMet(List<T> connectionObjects) {
        if (objects == null) {
            return false;
        }
        switch (boolLogic) {
            case SOME_OF -> {
                return hasSome(connectionObjects, objects);
            }
            case NONE_OF -> {
                return hasNone(connectionObjects, objects);
            }
            case ALL_OF -> {
                return hasAll(connectionObjects, objects);
            }
        }
        return false;
    }

    public boolean hasSome(List<T> connectionObjects, List<T> check) {
        return check.stream().anyMatch(connectionObjects::contains);
    }

    public boolean hasNone(List<T> connectionObjects, List<T> check) {
        return check.stream().noneMatch(connectionObjects::contains);
    }

    public boolean hasAll(List<T> connectionObjects, List<T> check) {
        return new HashSet<>(connectionObjects).containsAll(check);
    }
}
