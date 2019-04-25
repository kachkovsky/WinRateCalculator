package ru.kachkovsky.wrc.winrate;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.subject.Subject;
import ru.kachkovsky.wrc.team.TeamDeterminator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WinRateListCalculator {
    private static WinRate MIN_POSSIBLE_WIN_RATE = new WinRate(0, 0);

    public <T extends SubjectsArea> List<WinRate> calc(Map<Action<T>, List<WinRate>> actionWRMap, T area) {
        TeamDeterminator<T> teamDeterminator = area.getTeamDeterminator();
        Comparator<WinRate> winRateComparator = area.getWinRateComparator();
        //How to calc winrate, if each team can action
        Map<Integer, WinRate> teamRate = new HashMap<>();
        for (Map.Entry<Action<T>, List<WinRate>> e : actionWRMap.entrySet()) {
            Subject subject = e.getKey().getSubject();
            int teamIndex = teamDeterminator.getTeamIndex(area, subject);
            WinRate winRate = e.getValue().get(teamIndex);
            WinRate oldRate = teamRate.get(teamIndex);
            if (oldRate == null) {
                teamRate.put(teamIndex, winRate);
            } else {
                if (winRateComparator.compare(oldRate, winRate) < 0) {
                    teamRate.put(teamIndex, winRate);
                }
            }
        }
        return averageWinRateOfSimultaneousActions(teamRate);
    }

    private WinRate averageWinRateOfSimultaneousActions(Map<Integer, WinRate> teamRate) {
        float min = 0;
        float max = 0;
        for (WinRate rate : teamRate.values()) {
            min += rate.getMinWinRate();
            max += rate.getMaxWinRate();
        }
        return new WinRate(min / teamRate.size(), max / teamRate.size());
    }
}
