package ru.kachkovsky.wrc;

import ru.kachkovsky.wrc.team.SubjectTeamAreaDeterminator;
import ru.kachkovsky.wrc.winrate.WinRate;

import java.util.Comparator;

public interface SubjectsArea {
    <T extends SubjectsArea> SubjectTeamAreaDeterminator<T> getTeamDeterminator();

    Comparator<WinRate> getWinRateComparator();

    int getNumberOfTeams();
}
