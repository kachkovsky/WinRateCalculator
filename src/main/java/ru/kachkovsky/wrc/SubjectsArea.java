package ru.kachkovsky.wrc;

import ru.kachkovsky.wrc.team.TeamDeterminator;
import ru.kachkovsky.wrc.winrate.WinRate;

import java.util.Comparator;

public interface SubjectsArea {
    <T extends SubjectsArea> TeamDeterminator<T> getTeamDeterminator();

    Comparator<WinRate> getWinRateComparator();
}
