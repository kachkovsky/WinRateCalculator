package ru.kachkovsky.wrc.stage;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.winrate.WinRate;

import java.util.List;

public interface FinishCheck<T extends SubjectsArea> {

    List<WinRate> checkTeamsWinRate(T area);
}
