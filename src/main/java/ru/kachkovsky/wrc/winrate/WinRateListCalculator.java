package ru.kachkovsky.wrc.winrate;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.subject.Subject;
import ru.kachkovsky.wrc.team.TeamDeterminator;

import java.util.*;

public class WinRateListCalculator {
    private static WinRate MIN_POSSIBLE_WIN_RATE = new WinRate(0, 0);

    public <T extends SubjectsArea> List<WinRate> calc(Map<Action<T>, List<WinRate>> actionWRMap, T area) {
        TeamDeterminator<T> teamDeterminator = area.getTeamDeterminator();
        Comparator<WinRate> winRateComparator = area.getWinRateComparator();
        //How to calc winrate, if each team can action
        Map<Integer, List<List<WinRate>>> teamRate = new HashMap<>();
        for (Map.Entry<Action<T>, List<WinRate>> e : actionWRMap.entrySet()) {
            Subject subject = e.getKey().getSubject();
            int teamIndex = teamDeterminator.getTeamIndex(area, subject);
            WinRate winRate = e.getValue().get(teamIndex);
            List<List<WinRate>> listOfEqualForActionCombinationOfRates = teamRate.get(teamIndex);
            if (listOfEqualForActionCombinationOfRates == null) {
                ArrayList<List<WinRate>> lists = new ArrayList<>();
                lists.add(e.getValue());
                teamRate.put(teamIndex, lists);
            } else {
                int compare = winRateComparator.compare(listOfEqualForActionCombinationOfRates.get(0).get(0), winRate);
                if (compare == 0) {
                    listOfEqualForActionCombinationOfRates.add(e.getValue());
                } else if (compare < 0) {
                    ArrayList<List<WinRate>> lists = new ArrayList<>();
                    lists.add(e.getValue());
                    teamRate.put(teamIndex, lists);
                }
            }
        }
        return averageWinRateOfSimultaneousActions(
                averageOfMultipleChoicesForDoer(teamRate, area.getNumberOfTeams()),
                area.getNumberOfTeams());
    }

    private Map<Integer, List<WinRate>> averageOfMultipleChoicesForDoer(Map<Integer, List<List<WinRate>>> teamRate, int numberOfTeams) {
        Map<Integer, List<WinRate>> map = new HashMap<>(teamRate.size());
        float min;
        float max;
        for (Map.Entry<Integer, List<List<WinRate>>> integerListEntry : teamRate.entrySet()) {
            List<List<WinRate>> rateList = integerListEntry.getValue();
            List<WinRate> averageRateList = new ArrayList<>();
            for (int i = 0; i < numberOfTeams; i++) {
                min = 0;
                max = 0;
                for (List<WinRate> winRates : rateList) {
                    WinRate winRate = winRates.get(i);
                    min += winRate.getMinWinRate();
                    max += winRate.getMaxWinRate();
                }
                averageRateList.add(new WinRate(min, max));
            }
            map.put(integerListEntry.getKey(), averageRateList);
        }
        return map;
    }


    private List<WinRate> averageWinRateOfSimultaneousActions(Map<Integer, List<WinRate>> winRateForTeam, int numberOfTeams) {
        float min;
        float max;
        List<WinRate> rates = new ArrayList<>();
        for (int i = 0; i < numberOfTeams; i++) {
            min = 0;
            max = 0;
            for (List<WinRate> rateList : winRateForTeam.values()) {
                WinRate rate = rateList.get(i);
                min += rate.getMinWinRate();
                max += rate.getMaxWinRate();
            }
            rates.add(new WinRate(min / winRateForTeam.size(), max / winRateForTeam.size()));
        }
        return rates;
    }
}
