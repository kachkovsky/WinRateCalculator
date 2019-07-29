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

    public static List<WinRate> twoPlayersUnknownOrWin(int teamIndexToWin) {
        ArrayList<WinRate> wrList = new ArrayList<>(2);
        for (int i = 0; i < 2; i++) {
            wrList.add(new WinRate(teamIndexToWin == i ? 0.5f : 0f, teamIndexToWin == i ? 1f : 0.5f));
        }
        return wrList;
    }

    public static List<WinRate> twoPlayersUnknownAll() {
        ArrayList<WinRate> wrList = new ArrayList<>(2);
        for (int i = 0; i < 2; i++) {
            wrList.add(new WinRate(0, 1));
        }
        return wrList;
    }
}
