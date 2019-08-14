package ru.kachkovsky.wrc_mafia;

import ru.kachkovsky.wrc.subject.Subject;
import ru.kachkovsky.wrc.team.SubjectTeamAreaDeterminant;

public class TwoRolesMafiaTeamDeterminant implements SubjectTeamAreaDeterminant<SimpleMafiaArea> {
    @Override
    public int getTeamIndex(SimpleMafiaArea area, Subject subject) {
        return area.getStaticContents().getTeamIndex(subject);
    }

    @Override
    public int getSubjectsCountForTeam(SimpleMafiaArea area, Subject subject) {
        int i = 0;
        for (Subject s : area.getSubjectList()) {
            if (subject.equals(s)) {
                i++;
            }
        }
        return i;
    }
}
