package ru.kachkovsky.wrc;

import ru.kachkovsky.wrc.stage.Stage;
import ru.kachkovsky.wrc.subject.Subject;
import ru.kachkovsky.wrc.team.SubjectTeamAreaDeterminator;
import ru.kachkovsky.wrc.winrate.WinRate;

import java.util.Comparator;
import java.util.List;

public interface SubjectsArea<T extends SubjectsArea<T>> {
    SubjectTeamAreaDeterminator<T> getTeamDeterminator();

    Comparator<WinRate> getWinRateComparator();

    int getNumberOfTeams();

    List<Subject> getSubjectList();

    Subject getCurrentSubject();

    String areaToLogString();

    Stage<T> getCurrentStage();
}
