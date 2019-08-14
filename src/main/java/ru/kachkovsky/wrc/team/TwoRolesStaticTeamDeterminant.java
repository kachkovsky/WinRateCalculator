package ru.kachkovsky.wrc.team;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.subject.Subject;

public abstract class TwoRolesStaticTeamDeterminant<T extends SubjectsArea<T>> implements SubjectTeamAreaDeterminant<T> {

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
