package utils;

import java.util.ArrayList;
import java.util.List;

public class ActionQueue {
    private final List<Action> actionList;

    public ActionQueue() {
        this.actionList = new ArrayList<>();
    }

    public void addAction(Action action) {
        this.actionList.add(action);
    }

//    public Action getNextAction() {;
//        updateActions();
//        actionList.sort((o1, o2) -> {
//            int priorityCompare = Integer.compare(o2.skill.priority, o1.skill.priority);
//            if (priorityCompare != 0) {
//                return priorityCompare;
//            } else {
//                return Integer.compare(o2.caster.getStat(Stat.SPEED), o1.caster.getStat(Stat.SPEED));
//            }
//        });
//        return actionList.get(0);
//    }

    public boolean hasAction() {
        return !this.actionList.isEmpty();
    }

    public void remove(Action action) {
        this.actionList.remove(action);
    }
    public void updateActions() {
        for (Action action : this.actionList) {
            action.skill.getCurrentVersion();
        }
    }
}
