package ru.kachkovsky.wrc;

import ru.kachkovsky.wrc.stage.Stage;
import ru.kachkovsky.wrc.subject.Subject;
import ru.kachkovsky.wrc.team.SubjectTeamAreaDeterminant;
import ru.kachkovsky.wrc.winrate.WinRate;

import java.util.Comparator;
import java.util.List;

public interface SubjectsArea<T extends SubjectsArea<T>> {
    SubjectTeamAreaDeterminant<T> getTeamDeterminant();

    Comparator<WinRate> getWinRateComparator();

    int getNumberOfTeams();

    List<Subject> getSubjectList();

    Subject getCurrentSubject();

    String areaToLogString();

    Stage<T> getCurrentStage();
}
