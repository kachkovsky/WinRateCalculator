package ru.kachkovsky.wrc.eventsgraph;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.stage.Stage;
import ru.kachkovsky.wrc.winrate.WinRate;
import ru.kachkovsky.wrc.winrate.WinRateListCalculator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventGraphNode<T extends SubjectsArea> {

    private T area;
    private Stage<T> nextStage;

    //calc nextStage finishChecks to get these values
    private List<WinRate> teamsWinRate;

    public EventGraphNode(T area, Stage<T> nextStage) {
        this.area = area;
        this.nextStage = nextStage;
    }

    public T getArea() {
        return area;
    }

    /**
     * @return map of action and their child nodes. Don't save it while recursion to save memory
     */
    public Map<Action<T>, EventGraphNode<T>> calcWinRate() {
        calcNextStageWinRate();
        if (teamsWinRate == null) {
            List<Action<T>> actions = nextStage.getActions(area);
            Map<Action<T>, EventGraphNode<T>> map = new HashMap<>();
            for (Action<T> a : actions) {
                EventGraphNode<T> eventGraphNode = a.calcAct(area);
                //eventGraphNode.
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

    static WinRateListCalculator CALCULATOR = new WinRateListCalculator();

    public static <T extends SubjectsArea> Map<Action<T>, List<WinRate>> eventGraphMapToWinRateMap(Map<Action<T>, EventGraphNode<T>> map, T area) {
        Map<Action<T>, List<WinRate>> m = new HashMap<>();
        for (Map.Entry<Action<T>, EventGraphNode<T>> entry : map.entrySet()) {
            Map<Action<T>, EventGraphNode<T>> m1 = entry.getValue().calcWinRate();
            if (m1 != null) {
                m.put(entry.getKey(), CALCULATOR.calc(eventGraphMapToWinRateMap(entry.getValue().calcWinRate(), area), area));
            } else {
                m.put(entry.getKey(), entry.getValue().getTeamsWinRate());
            }
        }
        return m;
    }
}
