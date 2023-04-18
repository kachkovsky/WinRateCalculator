package ru.kachkovsky.wrc.eventsgraph;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.stage.strategy.StageActionsStrategyResolver;
import ru.kachkovsky.wrc.winrate.WinRate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TurnNode<T extends SubjectsArea<T>> {

    private T area;
    private TurnNode<T> parent;

    //calc nextStage finishChecks to get these values
    private List<WinRate> teamsWinRate;

    public TurnNode(T area) {
        this.area = area;
    }

    public TurnNode<T> getParent() {
        return parent;
    }

    public T getArea() {
        return area;
    }

    public Map<Action<T>, TurnNode<T>> calcWinRate(StageActionsStrategyResolver<T> stageActionsStrategyResolver) {
        return calcWinRate(stageActionsStrategyResolver, false);
    }

    /**
     * @return map of action and their child nodes. Don't save it while recursion to save memory
     */
    public Map<Action<T>, TurnNode<T>> calcWinRate(StageActionsStrategyResolver<T> stageActionsStrategyResolver, boolean keepParentNode) {
        calcFinishChecks();
        if (teamsWinRate == null) {
            List<Action<T>> actions = stageActionsStrategyResolver.resolve(area.getCurrentStage()).getActions(area);
            Map<Action<T>, TurnNode<T>> map = new LinkedHashMap<>(actions.size());
            for (Action<T> a : actions) {
                TurnNode<T> turnNode = new TurnNode<>(a.calcAct(area));
                if (keepParentNode) {
                    turnNode.parent = this;
                }
                map.put(a, turnNode);
            }
            return map;
        }
        return null;
    }

    public void calcFinishChecks() {
        teamsWinRate = area.getCurrentStage().calcFinishChecks(area);
    }

    public List<WinRate> getTeamsWinRate() {
        return teamsWinRate;
    }

}
