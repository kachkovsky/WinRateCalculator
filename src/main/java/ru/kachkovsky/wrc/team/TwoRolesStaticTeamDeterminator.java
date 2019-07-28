package ru.kachkovsky.wrc.team;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.subject.Subject;

import java.util.List;

public abstract class TwoRolesStaticTeamDeterminator<T extends SubjectsArea<T>> implements SubjectTeamAreaDeterminator<T> {

    @Override
    public int getSubjectsCountForTeam(T area, Subject subject) {
        int i = 0;
        for (Subject s : area.getSubjectList()) {
            if (subject.equals(s)) {
                i++;
            }
        }
        return i;
    }
}
