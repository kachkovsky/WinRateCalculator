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
        while (true) {
            Map<Action, EventGraphNode> map = node.calcWinRate();
            writeCurrentTurn(node);
            if (map == null || map.isEmpty()) {
                return;
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
                        node = entry.getValue();
                        break;
                    }
                    i++;
                }
            }
        }
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
        if (wrl != null) {
            for (WinRate wr : wrl) {
                printWinRate(prefix, wr);
            }
            System.out.println();
        }
    }

    public void printWinRate(String prefix, WinRate wr) {
        System.out.println(prefix + "min:" + wr.getMaxWinRate() + " | max:" + wr.getMaxWinRate());
    }
}
