package ru.kachkovsky.wrc.winrate.calculator;

import ru.kachkovsky.wrc.OnlyOneTeamCanDoTurnSubjectArea;
import ru.kachkovsky.wrc.eventsgraph.EventGraphNode;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.winrate.WinRate;
import ru.kachkovsky.wrc.winrate.WinRateUtils;
import ru.kachkovsky.wrc_console_ui.ConsoleUI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//DON'T USE THIS CLASS IF SIMULTANEOUS TURNS ARE IN THE GAME!!!
public class WinRateListForTeamCalculator extends WinRateListFullCalculator {
    public <T extends OnlyOneTeamCanDoTurnSubjectArea> Map<Action<T>, List<WinRate>> eventGraphMapToWinRateMapOnlyOneTeam(Map<Action<T>, EventGraphNode<T>> map, T area) {
        Map<Action<T>, List<WinRate>> m = new HashMap<>();
        try {
            for (Map.Entry<Action<T>, EventGraphNode<T>> entry : map.entrySet()) {
                EventGraphNode<T> innerNode = entry.getValue();
                Map<Action<T>, EventGraphNode<T>> m1 = innerNode.calcWinRate(true);

                if (innerNode.getTeamsWinRate() == null) {
                    EventGraphNode<T> p = innerNode;
                    while ((p = p.getParent()) != null) {
                        if (innerNode.getArea().equals(p.getArea())) {
                            m.put(entry.getKey(), WinRateUtils.twoPlayersUnknownOrWin(innerNode.getArea().getCurrentTeamIndex()));
                            break;
                        }
                    }
                }
//
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("\ncalculator");
//                ConsoleUI consoleUI = new ConsoleUI();
//                consoleUI.writeCurrentTurn(innerNode);

                if (innerNode.getTeamsWinRate() != null && innerNode.getTeamsWinRate().get(area.getCurrentTeamIndex()).getMinWinRate() >= 1f) {
                    m.put(entry.getKey(), innerNode.getTeamsWinRate());
                    break;
                }
                if (m1 != null) {
                    m.put(entry.getKey(), calc(eventGraphMapToWinRateMapOnlyOneTeam(m1, innerNode.getArea()), area));
                } else {
                    m.put(entry.getKey(), innerNode.getTeamsWinRate());
                }
            }
        } catch (StackOverflowError e) {
            ConsoleUI consoleUI = new ConsoleUI();
            EventGraphNode<T> par = map.entrySet().iterator().next().getValue();
            while ((par = par.getParent()) != null) {
                for (Map.Entry<Action<T>, EventGraphNode<T>> entry : par.calcWinRate().entrySet()) {
                    System.out.println(entry.getKey());
                    consoleUI.writeCurrentTurn(entry.getValue());
                }
                System.out.println("------------");
            }
            System.exit(-1);
        }
        return m;
    }

}
