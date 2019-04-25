package ru.kachkovsky.wrc.eventsgraph;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.stage.Stage;
import ru.kachkovsky.wrc.subject.Subject;
import ru.kachkovsky.wrc.winrate.WinRate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventGraphNode<T extends SubjectsArea> {

    private T area;
    private Stage<T> nextStage;
    private Subject previousActionSubject;

    //calc nextStage finishChecks to get these values
    private List<WinRate> teamsWinRate;

    public EventGraphNode(T area, Stage<T> nextStage, Subject previousActionSubject) {
        this.area = area;
        this.nextStage = nextStage;
        this.previousActionSubject = previousActionSubject;
    }

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

    public static <T extends SubjectsArea> Map<Action<T>, List<WinRate>> eventGraphMapToWinRateMap(Map<Action<T>, EventGraphNode<T>> map) {
        Map<Action<T>, List<WinRate>> m = new HashMap<>();
        for (Map.Entry<Action<T>, EventGraphNode<T>> entry : map.entrySet()) {
            Map<Action<T>, EventGraphNode<T>> m1 = entry.getValue().calcWinRate();
            if (m1 != null) {
                m.put(entry.getKey(), eventGraphMapToWinRateMap(entry.getValue().calcWinRate()));
            }else {
                m.put(entry.getKey(),entry.getValue().getTeamsWinRate());
            }
        }
    }
}
