package ru.kachkovsky.wrc.winrate.calculator;

import ru.kachkovsky.utils.StringUtils;
import ru.kachkovsky.wrc.OnlyOneTeamCanDoTurnSubjectArea;
import ru.kachkovsky.wrc.eventsgraph.EventGraphNode;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.winrate.WinRateUtils;
import ru.kachkovsky.wrc_console_ui.ConsoleUI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//DON'T USE THIS CLASS IF SIMULTANEOUS TURNS ARE IN THE GAME!!!
public class WinRateListForTeamCalculator extends WinRateListFullCalculator {

    ConsoleUI consoleUI = new ConsoleUI();

    int i = 1;
    public static final int ITERATIONS_TO_CHECK_PARENT = 3;

    static class StackItem<T extends OnlyOneTeamCanDoTurnSubjectArea> {
        List<ActionResults<T>> list;
        Iterator<Map.Entry<Action<T>, EventGraphNode<T>>> iterator;
        Map.Entry<Action<T>, EventGraphNode<T>> entry;
        T area;
    }

    public <T extends OnlyOneTeamCanDoTurnSubjectArea> List<ActionResults<T>> eventGraphMapToWinRateMapOnlyOneTeam(Map<Action<T>, EventGraphNode<T>> map, T area) {

        List<StackItem<T>> stack = new ArrayList<>(1000);

        Iterator<Map.Entry<Action<T>, EventGraphNode<T>>> iterator = null;
        List<ActionResults<T>> list = null;
        outer:
        while (true) {
            if (iterator == null) {
                iterator = map.entrySet().iterator();
                list = new ArrayList<>();
            }
            consoleUI.writeCurrentArea(StringUtils.spaces(stack.size(), '-') + "[", area);
            iteratorLabel:
            while (iterator.hasNext()) {
                Map.Entry<Action<T>, EventGraphNode<T>> entry = iterator.next();
                EventGraphNode<T> innerNode = entry.getValue();
                Map<Action<T>, EventGraphNode<T>> m1 = innerNode.calcWinRate(true);

                if (innerNode.getTeamsWinRate() == null) {
                    EventGraphNode<T> p = innerNode;
                    while ((p = p.getParent()) != null) {
                        if (innerNode.getArea().equals(p.getArea())) {
                            System.out.println(i++);
                            list.add(new ActionResults<>(entry.getKey(), innerNode, WinRateUtils.twoPlayersUnknownOrWin(innerNode.getArea().getCurrentTeamIndex())));
                            break iteratorLabel;
                        }
                    }
                }


//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("\ncalculator");
//                ConsoleUI consoleUI = new ConsoleUI();
//                consoleUI.writeCurrentTurn(innerNode);

                if (innerNode.getTeamsWinRate() != null && innerNode.getTeamsWinRate().get(area.getCurrentTeamIndex()).getMinWinRate() >= 1f) {
                    list.add(new ActionResults<>(entry.getKey(), innerNode, innerNode.getTeamsWinRate()));
                    break;
                }
                if (m1 != null) {
                    System.out.println(entry.getKey());
                    StackItem<T> stackItem = new StackItem<>();
                    stackItem.list = list;
                    stackItem.iterator = iterator;
                    stackItem.entry = entry;
                    stackItem.area = area;
                    stack.add(stackItem);

                    map = m1;
                    iterator = null;
                    area = innerNode.getArea();
                    continue outer;
                } else {
                    list.add(new ActionResults<>(entry.getKey(), innerNode, innerNode.getTeamsWinRate()));
                }
            }
            consoleUI.writeCurrentArea(StringUtils.spaces(stack.size(), '-') + "$", area);
            if (stack.isEmpty()) {
                return list;
            }
            StackItem<T> si = stack.remove(stack.size() - 1);
            area = si.area;
            iterator = si.iterator;
            Action<T> action = si.entry.getKey();
            si.list.add(new ActionResults<>(action, si.entry.getValue(), calc(list, si.entry.getValue().getArea())));
            list = si.list;
        }
    }

}
