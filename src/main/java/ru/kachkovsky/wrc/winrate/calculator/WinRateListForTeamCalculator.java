package ru.kachkovsky.wrc.winrate.calculator;

import ru.kachkovsky.wrc.OnlyOneTeamCanDoTurnSubjectArea;
import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.eventsgraph.EventGraphNode;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.winrate.WinRate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//DON'T USE THIS CLASS IF SIMULTANEOUS TURNS ARE IN THE GAME!!!
public class WinRateListForTeamCalculator {

    public <T extends OnlyOneTeamCanDoTurnSubjectArea> Map<Action<T>, List<WinRate>> eventGraphMapToWinRateMapOnlyOneTeam(Map<Action<T>, EventGraphNode<T>> map, T area, int currentPlayerTeamIndex, int winTeamIndex) {
        boolean found = false;
        Map<Action<T>, List<WinRate>> m = new HashMap<>();
        Map<Action<T>, EventGraphNode<T>> m1 = null;
        for (Map.Entry<Action<T>, EventGraphNode<T>> entry : map.entrySet()) {
            EventGraphNode<T> node = entry.getValue();
            if (!found) {
                m1 = node.calcWinRate();
                if (node.getTeamsWinRate() != null && node.getTeamsWinRate().get(winTeamIndex).getMinWinRate() >= 1f && node.getArea().getCurrentPlayerIndex() == winTeamIndex) {
                    found = true;
                }
            }
            if (m1 != null) {
                m.put(entry.getKey(), calc(eventGraphMapToWinRateMapOnlyOneTeam(m1, node.getArea(), node.getArea().getCurrentPlayerIndex(), winTeamIndex), node.getArea(), winTeamIndex));
            } else {
                m.put(entry.getKey(), entry.getValue().getTeamsWinRate());
            }
        }
        return m;
    }

    private <T extends SubjectsArea> List<WinRate> calc(Map<Action<T>, List<WinRate>> actionWRMap, T area, int winTeamIndex) {
        throw new RuntimeException("not implemented");
    }
}
