package ru.kachkovsky.wrc.winrate;

import ru.kachkovsky.wrc.SubjectsArea;

import java.util.ArrayList;
import java.util.List;

public class WinRateUtils {

    public static final WinRate UNKNOWN = new WinRate(0f, 1f);
    public static final WinRate LOSE = new WinRate(0f, 0f);

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

    public static WinRate calcRiskyBest(WinRate a, WinRate b) {
//        if (a.getMinWinRate() >= b.getMinWinRate() && a.getMaxWinRate() >= b.getMaxWinRate()) {
//            return a;
//        } else if (a.getMinWinRate() <= b.getMinWinRate() && a.getMaxWinRate() <= b.getMaxWinRate()) {
//            return b;
//        } else {
            return new WinRate(Math.max(a.getMinWinRate(), b.getMinWinRate()), Math.max(a.getMaxWinRate(), b.getMaxWinRate()));
//        }
    }

    public static List<WinRate> twoItemsWRListByWRAndIndex(WinRate wr, int teamIndex) {
        ArrayList<WinRate> wrList = new ArrayList<>(2);
        for (int i = 0; i < 2; i++) {
            if (i == teamIndex) {
                wrList.add(wr);
            } else {
                wrList.add(opposingWinRate(wr));
            }
        }
        return wrList;
    }

    public static WinRate opposingWinRate(WinRate wr) {
        return new WinRate(1f - wr.getMaxWinRate() , 1f - wr.getMinWinRate());
        //replace if true and needed
       // return new WinRate(1f - wr.getMinWinRate(), 1f - wr.getMaxWinRate(), wr.getCalculatedVariants());
    }
}
