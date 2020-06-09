package ru.kachkovsky.wrc_console_ui;

import ru.kachkovsky.wrc.OnlyOneTeamCanDoTurnSubjectArea;
import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.eventsgraph.TurnNode;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.stage.strategy.StageActionsStrategyResolver;
import ru.kachkovsky.wrc.subject.Subject;
import ru.kachkovsky.wrc.team.SubjectTeamAreaDeterminant;
import ru.kachkovsky.wrc.winrate.WinRate;
import ru.kachkovsky.wrc.winrate.calculator.ActionResults;
import ru.kachkovsky.wrc.winrate.calculator.WinRateListForTeamCalculator;
import ru.kachkovsky.wrc.winrate.calculator.cache.LFUWRCacheHelper;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleUI {

    private static final int FIRST_CHOICE = 1;

    public <T extends OnlyOneTeamCanDoTurnSubjectArea<T>> void uiForFullGameWithRatesDebug(TurnNode<T> node, StageActionsStrategyResolver<T> resolver) {
        Scanner scanner = new Scanner(System.in);
        WinRateListForTeamCalculator calculator = new WinRateListForTeamCalculator();
        calculator.setCacheHelper(new LFUWRCacheHelper());
        Map<Action<T>, TurnNode<T>> actionEventGraphNodeMap;
        while ((actionEventGraphNodeMap = node.calcWinRate(resolver, true)) != null) {
            long t = System.currentTimeMillis();
            List<ActionResults<T>> actionResults = calculator.eventGraphMapToWinRateMapOnlyOneTeam(actionEventGraphNodeMap, node.getArea(), resolver);
            System.out.println("Time: " + (System.currentTimeMillis() - t));
            System.out.println("$$$Game calculated$$$");
            for (ActionResults actionResult : actionResults) {
                printAction("x", actionResult.getAction(), "");
                printWinRateList(actionResult.getWrList(), "");
            }
            writeCurrentTurn(node);
            //can print another resolver for UI
            node = uiForTurn(scanner, node, resolver);
        }
        printWinRateList(node.getTeamsWinRate(), "FIN ");
    }

    public <T extends SubjectsArea<T>> void uiForFullGame(TurnNode node, StageActionsStrategyResolver<T> resolver) {
        Scanner scanner = new Scanner(System.in);
        TurnNode prevNode = null;
        while (node != null) {
            writeCurrentTurn(prevNode = node);
            node = uiForTurn(scanner, node, resolver);
        }
        if (prevNode != null) {
            printWinRateList(prevNode.getTeamsWinRate(), " ");
        }
    }

    public <T extends SubjectsArea<T>> TurnNode uiForTurn(Scanner scanner, TurnNode node, StageActionsStrategyResolver<T> resolver) {
        Map<Action, TurnNode> map = node.calcWinRate(resolver);
        if (map == null || map.isEmpty()) {
            return null;
        } else {
            int i = FIRST_CHOICE;
            for (Map.Entry<Action, TurnNode> entry : map.entrySet()) {
                printAction(i, entry.getKey(), "");
                printWinRateList(entry.getValue().getTeamsWinRate(), "   ");
                i++;
            }
            int val = scanner.nextInt();
            i = FIRST_CHOICE;
            for (Map.Entry<Action, TurnNode> entry : map.entrySet()) {
                if (val == i) {
                    return entry.getValue();
                }
                i++;
            }
        }
        return node;
    }

    public void printAction(Object index, Action a, String postfix) {
        System.out.println(index + ")" + a.toString() + postfix);
    }

    public void writeCurrentTurn(TurnNode node) {
        SubjectsArea area = node.getArea();
        printSubjects(area);
        System.out.println(area.areaToLogString());
        System.out.println("Win rate by team index:");
        printWinRateList(node.getTeamsWinRate(), "");
    }

    public void writeCurrentArea(String prefix, SubjectsArea area) {
        System.out.println(prefix + area.areaToLogString());
    }

    public void printSubjects(SubjectsArea area) {
        System.out.print("Current:" + area.getCurrentSubject() + ". All: ");
        List<Subject> subjectList = area.getSubjectList();
        SubjectTeamAreaDeterminant teamDeterminant = area.getTeamDeterminant();
        for (Subject subject : subjectList) {
            System.out.print(subject.toString() + " " + teamDeterminant.getTeamIndex(area, subject) + " | ");
        }
        System.out.println();
    }

    public void printWinRateList(List<WinRate> wrl, String prefix) {
        printWinRateList(wrl, prefix, true);
    }

    public void printWinRateList(List<WinRate> wrl, String prefix, boolean newLine) {
        if (wrl != null) {
            for (WinRate wr : wrl) {
                printWinRate(prefix, wr);
            }
            if (newLine) {
                System.out.println();
            }
        }
    }

    public void printWinRate(String prefix, WinRate wr) {
        System.out.println(prefix + "min:" + wr.getMinWinRate() + " | max:" + wr.getMaxWinRate() + " calcVariants | " + wr.getCalculatedVariants());
    }
}
