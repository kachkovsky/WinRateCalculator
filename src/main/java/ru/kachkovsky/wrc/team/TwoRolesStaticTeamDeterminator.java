package ru.kachkovsky.wrc.team;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.subject.Subject;
import ru.kachkovsky.wrc_mafia.SimpleMafiaArea;

public abstract class TwoRolesStaticTeamDeterminator<T extends SubjectsArea> implements SubjectTeamAreaDeterminator<T> {

    @Override
    public int getSubjectsCountForTeam(SubjectsArea area, Subject subject) {
        int i = 0;
        for (Subject s : area.getSubjectList()) {
            if (subject.equals(s)) {
                i++;
            }
        }
        return i;
    }
}
