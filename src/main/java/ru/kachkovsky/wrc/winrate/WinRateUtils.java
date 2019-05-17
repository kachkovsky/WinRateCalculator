package ru.kachkovsky.wrc.winrate;

import ru.kachkovsky.wrc.SubjectsArea;

import java.util.ArrayList;
import java.util.List;

public class WinRateUtils {

    public static List<WinRate> winRateListForOnlyOneWinner(SubjectsArea area, int teamIndexToWin) {
        ArrayList<WinRate> wrList = new ArrayList<>(area.getNumberOfTeams());
        for (int i = 0; i < area.getNumberOfTeams(); i++) {
            wrList.add(new WinRate(teamIndexToWin == i ? 1f : 0f));
        }
        return wrList;
    }

}
