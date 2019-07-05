package ru.kachkovsky.wrc.eventsgraph;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.stage.Stage;
import ru.kachkovsky.wrc.winrate.WinRate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EventGraphNode<T extends SubjectsArea> {

    private T area;
    private Stage<T> nextStage;
    private EventGraphNode<T> parent;

    //calc nextStage finishChecks to get these values
    private List<WinRate> teamsWinRate;

    public EventGraphNode(T area, Stage<T> nextStage) {
        this.area = area;
        this.nextStage = nextStage;
    }

    public EventGraphNode<T> getParent() {
        return parent;
    }

    public T getArea() {
        return area;
    }

    public Map<Action<T>, EventGraphNode<T>> calcWinRate() {
        return calcWinRate(false);
    }

    /**
     * @return map of action and their child nodes. Don't save it while recursion to save memory
     */
    public Map<Action<T>, EventGraphNode<T>> calcWinRate(boolean keepParentNode) {
        calcNextStageWinRate();
        if (teamsWinRate == null) {
            List<Action<T>> actions = nextStage.getActions(area);
            Map<Action<T>, EventGraphNode<T>> map = new LinkedHashMap<>(actions.size());
            for (Action<T> a : actions) {
                EventGraphNode<T> eventGraphNode = a.calcAct(area);
                if (keepParentNode) {
                    eventGraphNode.parent = this;
                }
                map.put(a, eventGraphNode);
            }
            return map;
        }
        return null;
    }

    public void calcNextStageWinRate() {
        teamsWinRate = nextStage.calcFinishChecks(area);
    }

    public List<WinRate> getTeamsWinRate() {
        return teamsWinRate;
    }

}
