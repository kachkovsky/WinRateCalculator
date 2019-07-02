package ru.kachkovsky.wrc.winrate;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.subject.Subject;
import ru.kachkovsky.wrc.team.SubjectTeamAreaDeterminator;

import java.util.*;

public class WinRateListCalculator {
    private static WinRate MIN_POSSIBLE_WIN_RATE = new WinRate(0, 0);

    public <T extends SubjectsArea> List<WinRate> calc(Map<Action<T>, List<WinRate>> actionWRMap, T area) {
        SubjectTeamAreaDeterminator<T> subjectTeamAreaDeterminator = area.getTeamDeterminator();
        Comparator<WinRate> winRateComparator = area.getWinRateComparator();
        //How to calc winrate, if each team can action
        Map<Subject, List<List<WinRate>>> subjRate = new HashMap<>();
        for (Map.Entry<Action<T>, List<WinRate>> e : actionWRMap.entrySet()) {
            Subject subject = area.getCurrentSubject();
            int teamIndex = subjectTeamAreaDeterminator.getTeamIndex(area, subject);
            WinRate winRate = e.getValue().get(teamIndex);
            if (hasMoreWinRateForAnySubjectOfTeam(subjRate, teamIndex, winRate, area)) {
                for (Subject s : subjRate.keySet()) {
                    if (teamIndex == subjectTeamAreaDeterminator.getTeamIndex(area, s)) {
                        subjRate.remove(s);
                    }
                }
            }
            List<List<WinRate>> listOfEqualForActionCombinationOfRates = subjRate.get(subject);
            if (listOfEqualForActionCombinationOfRates == null) {
                ArrayList<List<WinRate>> lists = new ArrayList<>();
                lists.add(e.getValue());
                subjRate.put(subject, lists);
            } else {
                int compare = winRateComparator.compare(listOfEqualForActionCombinationOfRates.get(0).get(0), winRate);
                if (compare == 0) {
                    listOfEqualForActionCombinationOfRates.add(e.getValue());
                } else if (compare < 0) {
                    ArrayList<List<WinRate>> lists = new ArrayList<>();
                    lists.add(e.getValue());
                    subjRate.put(subject, lists);
                }
            }
        }
        return subjectAverageWinRateOfSimultaneousActionsToTeamWinRate(
                averageOfMultipleChoicesForDoer(subjRate, area.getNumberOfTeams()), area);
    }

    private <T extends SubjectsArea> boolean hasMoreWinRateForAnySubjectOfTeam(Map<Subject, List<List<WinRate>>> subjRate, int teamIndex, WinRate rate, T area) {
        Comparator<WinRate> winRateComparator = area.getWinRateComparator();
        SubjectTeamAreaDeterminator<SubjectsArea> teamDeterminator = area.getTeamDeterminator();
        for (Map.Entry<Subject, List<List<WinRate>>> entry : subjRate.entrySet()) {
            if (teamDeterminator.getTeamIndex(area, entry.getKey()) == teamIndex) {
                List<List<WinRate>> value = entry.getValue();
                for (List<WinRate> rates : value) {
                    if (winRateComparator.compare(rates.get(teamIndex), rate) < 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param subjectRate   map of actions for Subject. value - is list of combination of winrates list for best actions of doer
     * @param numberOfTeams
     * @return
     */
    private Map<Subject, List<WinRate>> averageOfMultipleChoicesForDoer(Map<Subject, List<List<WinRate>>> subjectRate, int numberOfTeams) {
        Map<Subject, List<WinRate>> map = new HashMap<>(subjectRate.size());
        float min;
        float max;
        for (Map.Entry<Subject, List<List<WinRate>>> integerListEntry : subjectRate.entrySet()) {
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
                averageRateList.add(new WinRate(min / rateList.size(), max / rateList.size()));
            }
            map.put(integerListEntry.getKey(), averageRateList);
        }
        return map;
    }


    private <T extends SubjectsArea> List<WinRate> subjectAverageWinRateOfSimultaneousActionsToTeamWinRate(Map<Subject, List<WinRate>> winRateForTeam, T area) {
        float min;
        float max;
        int numberOfSubjects;
        int subjectsCountForTeam;
        List<WinRate> rates = new ArrayList<>();
        for (int i = 0; i < area.getNumberOfTeams(); i++) {
            min = 0;
            max = 0;
            numberOfSubjects = 0;
            for (Map.Entry<Subject, List<WinRate>> subjectListEntry : winRateForTeam.entrySet()) {
                subjectsCountForTeam = area.getTeamDeterminator().getSubjectsCountForTeam(area, subjectListEntry.getKey());
                numberOfSubjects += subjectsCountForTeam;
                WinRate rate = subjectListEntry.getValue().get(i);
                min += (rate.getMinWinRate() * subjectsCountForTeam);
                max += (rate.getMaxWinRate() * subjectsCountForTeam);
            }
            rates.add(new WinRate(min / numberOfSubjects, max / numberOfSubjects));
        }
        return rates;
    }
}
