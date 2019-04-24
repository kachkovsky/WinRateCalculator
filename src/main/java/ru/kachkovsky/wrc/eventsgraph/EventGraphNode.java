package ru.kachkovsky.wrc.eventsgraph;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.stage.Stage;
import ru.kachkovsky.wrc.subject.Subject;
import ru.kachkovsky.wrc.winrate.WinRate;

import java.util.List;

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

    public void calcWinRateRecursive() {
        calcNextStageWinRate();
        if (teamsWinRate == null) {
            List<Action<T>> actions = nextStage.getActions(area);
            for (Action<T> a : actions) {
                EventGraphNode eventGraphNode = a.calcAct(area);
            }
        }
    }

    public void calcNextStageWinRate() {
        teamsWinRate = nextStage.calcFinishChecks(area);
    }
}
