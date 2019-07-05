package ru.kachkovsky.wrc.winrate.calculator;

import ru.kachkovsky.wrc.OnlyOneTeamCanDoTurnSubjectArea;
import ru.kachkovsky.wrc.eventsgraph.EventGraphNode;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.winrate.WinRate;
import ru.kachkovsky.wrc.winrate.WinRateUtils;

import java.util.*;

//DON'T USE THIS CLASS IF SIMULTANEOUS TURNS ARE IN THE GAME!!!
public class WinRateListForTeamCalculator extends WinRateListFullCalculator {
    static class StackItem<T extends OnlyOneTeamCanDoTurnSubjectArea> {
        Map<Action<T>, EventGraphNode<T>> map;
        T area;
        Map<Action<T>, List<WinRate>> m;
        Iterator<Map.Entry<Action<T>, EventGraphNode<T>>> iterator;
        Action<T> action;
    }

    public <T extends OnlyOneTeamCanDoTurnSubjectArea> Map<Action<T>, List<WinRate>> eventGraphMapToWinRateMapOnlyOneTeam(Map<Action<T>, EventGraphNode<T>> map, T area) {

        List<StackItem<T>> stack = new ArrayList<>(1000);

        Iterator<Map.Entry<Action<T>, EventGraphNode<T>>> iterator = null;
        Map<Action<T>, List<WinRate>> m = null;
        outer:
        while (true) {
            if (iterator == null) {
                iterator = map.entrySet().iterator();
                m = new HashMap<>();
            }
            while (iterator.hasNext()) {
                Map.Entry<Action<T>, EventGraphNode<T>> entry = iterator.next();
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
                    StackItem<T> stackItem = new StackItem<>();
                    stackItem.map = map;
                    stackItem.area = area;
                    stackItem.iterator = iterator;
                    stackItem.m = m;
                    stackItem.action = entry.getKey();
                    stack.add(stackItem);

                    map = m1;
                    area = innerNode.getArea();
                    iterator = null;
                    continue outer;
                } else {
                    m.put(entry.getKey(), innerNode.getTeamsWinRate());
                }
            }
            if (stack.isEmpty()) {
                return m;
            }
            StackItem<T> si = stack.remove(stack.size() - 1);
            map = si.map;
            area = si.area;
            iterator = si.iterator;
            Action<T> action = si.action;
            si.m.put(action, calc(m, area));
            m = si.m;
        }
    }

}
