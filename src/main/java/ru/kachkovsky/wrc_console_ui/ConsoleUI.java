package ru.kachkovsky.wrc_console_ui;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.eventsgraph.EventGraphNode;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.subject.Subject;
import ru.kachkovsky.wrc.team.SubjectTeamAreaDeterminator;
import ru.kachkovsky.wrc.winrate.WinRate;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleUI {

    private static final int FIRST_CHOICE = 1;

    public void uiForFullGame(EventGraphNode node) {
        Scanner scanner = new Scanner(System.in);
        EventGraphNode prevNode = null;
        while (node != null) {
            writeCurrentTurn(prevNode = node);
            node = uiForTurn(scanner, node);
        }
        if (prevNode != null) {
            printWinRateList(prevNode.getTeamsWinRate(), " ");
        }
    }

    public EventGraphNode uiForTurn(Scanner scanner, EventGraphNode node) {
        Map<Action, EventGraphNode> map = node.calcWinRate();
        if (map == null || map.isEmpty()) {
            return null;
        } else {
            int i = FIRST_CHOICE;
            for (Map.Entry<Action, EventGraphNode> entry : map.entrySet()) {
                printAction(i, entry.getKey(), "");
                printWinRateList(entry.getValue().getTeamsWinRate(), "   ");
                i++;
            }
            int val = scanner.nextInt();
            i = FIRST_CHOICE;
            for (Map.Entry<Action, EventGraphNode> entry : map.entrySet()) {
                if (val == i) {
                    return entry.getValue();
                }
                i++;
            }
        }
        return node;
    }

    public void printAction(int index, Action a, String postfix) {
        System.out.println(index + ")" + a.toString() + postfix);
    }

    public void writeCurrentTurn(EventGraphNode node) {
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
        SubjectTeamAreaDeterminator<SubjectsArea> teamDeterminator = area.getTeamDeterminator();
        for (Subject subject : subjectList) {
            System.out.print(subject.toString() + " " + teamDeterminator.getTeamIndex(area, subject) + " | ");
        }
        System.out.println();
    }

    public void printWinRateList(List<WinRate> wrl, String prefix) {
        printWinRateList(wrl,prefix,true);
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
