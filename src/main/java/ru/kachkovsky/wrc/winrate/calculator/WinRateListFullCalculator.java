package ru.kachkovsky.wrc.winrate.calculator;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.eventsgraph.TurnNode;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.stage.strategy.StageActionsStrategyResolver;
import ru.kachkovsky.wrc.subject.Subject;
import ru.kachkovsky.wrc.team.SubjectTeamAreaDeterminant;
import ru.kachkovsky.wrc.winrate.WinRate;
import ru.kachkovsky.wrc.winrate.calculator.helper.MultDoersWithProbablyOrPossibleToCalcWinRateCalculatorHelper;
import ru.kachkovsky.wrc_console_ui.ConsoleUI;

import java.util.*;

//calculator needed for games with simultaneous turns
public class WinRateListFullCalculator {
    private MultDoersWithProbablyOrPossibleToCalcWinRateCalculatorHelper calcHelper = new MultDoersWithProbablyOrPossibleToCalcWinRateCalculatorHelper();
    private ConsoleUI consoleUI = new ConsoleUI();

    public <T extends SubjectsArea<T>> List<ActionResults<T>> eventGraphMapToWinRateMap(Map<Action<T>, TurnNode<T>> map, StageActionsStrategyResolver<T> resolver) {
        List<ActionResults<T>> list = new ArrayList<>();
        for (Map.Entry<Action<T>, TurnNode<T>> entry : map.entrySet()) {
            TurnNode<T> innerNode = entry.getValue();
            consoleUI.writeCurrentArea("[", innerNode.getArea());
            Map<Action<T>, TurnNode<T>> m1 = innerNode.calcWinRate(resolver);
            if (m1 != null) {
                list.add(new ActionResults<>(entry.getKey(), innerNode, calcHelper.calc(eventGraphMapToWinRateMap(m1, resolver), innerNode.getArea())));
            } else {
                list.add(new ActionResults<>(entry.getKey(), innerNode, entry.getValue().getTeamsWinRate()));
            }
            consoleUI.writeCurrentArea("$", innerNode.getArea());
        }
        return list;
    }


}
