package ru.kachkovsky.wrc_mafia;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.subject.Subject;
import ru.kachkovsky.wrc.team.SubjectTeamAreaDeterminator;
import ru.kachkovsky.wrc.winrate.WinRate;

import java.util.Comparator;
import java.util.List;

public class SimpleMafiaArea implements SubjectsArea {
    @Override
    public <T extends SubjectsArea> SubjectTeamAreaDeterminator<T> getTeamDeterminator() {
        return null;
    }

    @Override
    public Comparator<WinRate> getWinRateComparator() {
        return null;
    }

    @Override
    public int getNumberOfTeams() {
        return 0;
    }

    @Override
    public List<Subject> getSubjectList() {
        return null;
    }
}
