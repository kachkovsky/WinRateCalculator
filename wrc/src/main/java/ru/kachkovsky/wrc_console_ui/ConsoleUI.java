package ru.kachkovsky.wrc_console_ui;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.subject.Subject;
import ru.kachkovsky.wrc.team.SubjectTeamAreaDeterminant;

import java.util.List;

public class ConsoleUI {

    public void printAction(Object index, Action a, String postfix) {
        System.out.println(index + ")" + a.toString() + postfix);
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

}
