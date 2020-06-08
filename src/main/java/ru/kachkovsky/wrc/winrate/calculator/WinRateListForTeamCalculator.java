package ru.kachkovsky.wrc.winrate.calculator;

import ru.kachkovsky.wrc.OnlyOneTeamCanDoTurnSubjectArea;
import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.eventsgraph.TurnNode;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.stage.strategy.StageActionsStrategyResolver;
import ru.kachkovsky.wrc.winrate.WinRate;
import ru.kachkovsky.wrc.winrate.WinRateUtils;
import ru.kachkovsky.wrc.winrate.calculator.helper.MultDoersWithProbablyOrPossibleToCalcWinRateCalculatorHelper;
import ru.kachkovsky.wrc.winrate.calculator.helper.OneSimultaneousDoerWithPossibleToCalcWinRateCalculatorHelper;
import ru.kachkovsky.wrc_console_ui.ConsoleUI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//DON'T USE THIS CLASS IF SIMULTANEOUS TURNS ARE IN THE GAME!!!
public class WinRateListForTeamCalculator {
    private MultDoersWithProbablyOrPossibleToCalcWinRateCalculatorHelper calcHelper = new MultDoersWithProbablyOrPossibleToCalcWinRateCalculatorHelper();
    private ConsoleUI consoleUI = new ConsoleUI();

    int i = 1;
    public static final int ITERATIONS_TO_CHECK_PARENT = 3;

    public static final float MIN_WIN_RATE_TO_STOP_SEARCH = 1f;

    static class StackItem<T extends OnlyOneTeamCanDoTurnSubjectArea<T>> {
        List<ActionResults<T>> list;
        Iterator<Map.Entry<Action<T>, TurnNode<T>>> iterator;
        Map.Entry<Action<T>, TurnNode<T>> entry;
        T area;

        @Override
        public String toString() {
            return "" + list.size() + "   " + entry.toString();
        }
    }

    public <T extends OnlyOneTeamCanDoTurnSubjectArea<T>> List<ActionResults<T>> eventGraphMapToWinRateMapOnlyOneTeam(Map<Action<T>, TurnNode<T>> map, T area, StageActionsStrategyResolver<T> resolver) {

        List<StackItem<T>> stack = new ArrayList<>(1000);

        Iterator<Map.Entry<Action<T>, TurnNode<T>>> iterator = null;
        List<ActionResults<T>> list = null;
        outer:
        while (true) {
            if (iterator == null) {
                iterator = map.entrySet().iterator();
                list = new ArrayList<>();
            }

//            if (((SummonersDuelSubjectsArea) area).getCurrentStage().equals(SummonersDuelSubjectsArea.FIRST_STAGE)) {
//                if (!stack.isEmpty()) {
//                    consoleUI.printAction(0, stack.get(stack.size() - 1).entry.getKey(), "");
//                }
//                consoleUI.writeCurrentArea(stack.size() + "-[- ", area);
//            }
            iteratorLabel:
            while (iterator.hasNext()) {
                Map.Entry<Action<T>, TurnNode<T>> entry = iterator.next();
                TurnNode<T> innerNode = entry.getValue();
                Map<Action<T>, TurnNode<T>> m1 = innerNode.calcWinRate(resolver, true);

                if (innerNode.getTeamsWinRate() == null) {
                    TurnNode<T> p = innerNode;
                    while ((p = p.getParent()) != null) {
                        if (innerNode.getArea().equals(p.getArea())) {
                            //System.out.println(i++);
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

                if (innerNode.getTeamsWinRate() != null && innerNode.getTeamsWinRate().get(area.getCurrentTeamIndex()).getMinWinRate() >= MIN_WIN_RATE_TO_STOP_SEARCH) {
                    list.add(new ActionResults<>(entry.getKey(), innerNode, innerNode.getTeamsWinRate()));
                    break;
                }
                if (m1 != null) {
//                    if (soDeep(stack)) {
//                        list.add(new ActionResults<>(entry.getKey(), innerNode, WinRateUtils.twoPlayersUnknownAll()));
//                        break;
//                    }

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
//            if (((SummonersDuelSubjectsArea) area).getCurrentStage().equals(SummonersDuelSubjectsArea.FIRST_STAGE)) {
//                consoleUI.writeCurrentArea(stack.size() + "-$$$", area);
//            }
            boolean up;
            do {
                if (stack.isEmpty()) {
                    return list;
                }
                StackItem<T> si = stack.remove(stack.size() - 1);
                area = si.area;
                List<WinRate> calculatedWR = calcHelper.calc(list, si.entry.getValue().getArea());
                up = false;
                //calculate all options for root
                if (!stack.isEmpty()) {
                    up = calculatedWR.get(area.getCurrentTeamIndex()).getMinWinRate() >= MIN_WIN_RATE_TO_STOP_SEARCH;
                }
                iterator = si.iterator;
                Action<T> action = si.entry.getKey();
                si.list.add(new ActionResults<>(action, si.entry.getValue(), calculatedWR));
                list = si.list;
            } while (up);
        }
    }


    public static <T extends OnlyOneTeamCanDoTurnSubjectArea<T>> boolean soDeep(List<StackItem<T>> stack1) {
        return stack1.size() > 14;
//        List<StackItem<SummonersDuelSubjectsArea>> stack = (List) stack1;
//        if (stack.isEmpty()) {
//            return false;
//        }
//        StackItem<SummonersDuelSubjectsArea> last = stack.get(stack.size() - 1);
//        int DEEP_IN_STACK =100;
//        if (stack.size() <= DEEP_IN_STACK) {
//            return false;
//        }
//        StackItem<SummonersDuelSubjectsArea> checkItem = stack.get(stack.size() - DEEP_IN_STACK);
//        return last.area.getTeams()[0].getHp() == checkItem.area.getTeams()[0].getHp()
//                && last.area.getTeams()[1].getHp() == checkItem.area.getTeams()[1].getHp();
    }
}
