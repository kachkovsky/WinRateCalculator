package ru.kachkovsky.wrc.team;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.subject.Subject;


public interface SubjectTeamAreaDeterminator<T extends SubjectsArea> {
    int getTeamIndex(T area, Subject subject);

    int getSubjectsCountForTeam(T area, Subject subject);
}
