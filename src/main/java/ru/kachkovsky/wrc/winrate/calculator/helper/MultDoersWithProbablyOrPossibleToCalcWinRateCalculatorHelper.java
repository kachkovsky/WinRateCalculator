package ru.kachkovsky.wrc.winrate.calculator.helper;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.subject.Subject;
import ru.kachkovsky.wrc.team.SubjectTeamAreaDeterminant;
import ru.kachkovsky.wrc.winrate.WinRate;
import ru.kachkovsky.wrc.winrate.calculator.ActionResults;
import ru.kachkovsky.wrc.winrate.calculator.WinRateListFullCalculator;

import java.util.*;

public class MultDoersWithProbablyOrPossibleToCalcWinRateCalculatorHelper<T extends SubjectsArea<T>> implements ResultsToWinRateCalculatorHelper<T> {

    //TODO: Replace area of getTeamIndex to correct version of node
    public List<WinRate> calc(List<ActionResults<T>> actionResultsList, T areaBeforeAction) {
        SubjectTeamAreaDeterminant<T> subjectTeamAreaDeterminant = areaBeforeAction.getTeamDeterminant();
        //need to rework this to action.getCurrentSubject(area)
        Subject subject = areaBeforeAction.getCurrentSubject();
        int teamIndex = subjectTeamAreaDeterminant.getTeamIndex(areaBeforeAction, subject);
        Map<Subject, List<List<WinRate>>> subjRate = new HashMap<>();
        for (ActionResults<T> results : actionResultsList) {
            WinRate winRate = results.getWrList().get(teamIndex);
            if (hasMoreWinRateForAnySubjectOfTeam(subjRate, teamIndex, winRate, areaBeforeAction)) {
                for (Subject s : subjRate.keySet()) {
                    if (teamIndex == subjectTeamAreaDeterminant.getTeamIndex(areaBeforeAction, s)) {
                        subjRate.remove(s);
                    }
                }
            }
            List<List<WinRate>> listOfEqualForActionCombinationOfRates = subjRate.get(subject);
            if (listOfEqualForActionCombinationOfRates == null) {
                ArrayList<List<WinRate>> lists = new ArrayList<>();
                lists.add(results.getWrList());
                subjRate.put(subject, lists);
            } else {
                Comparator<WinRate> winRateComparator = areaBeforeAction.getWinRateComparator();
                //TODO: it is incorrect to compare only first. Need rework
                int compare = winRateComparator.compare(listOfEqualForActionCombinationOfRates.get(0).get(teamIndex), winRate);
                if (compare == 0) {
                    listOfEqualForActionCombinationOfRates.add(results.getWrList());
                } else if (compare < 0) {
                    ArrayList<List<WinRate>> lists = new ArrayList<>();
                    lists.add(results.getWrList());
                    subjRate.put(subject, lists);
                }
            }
        }
        return subjectAverageWinRateOfSimultaneousActionsToTeamWinRate(
                averageOfMultipleChoicesForDoer(subjRate, areaBeforeAction.getNumberOfTeams()), areaBeforeAction);
    }

    private <T extends SubjectsArea<T>> boolean hasMoreWinRateForAnySubjectOfTeam(Map<Subject, List<List<WinRate>>> subjRate, int teamIndex, WinRate rate, T area) {
        Comparator<WinRate> winRateComparator = area.getWinRateComparator();
        SubjectTeamAreaDeterminant<T> teamDeterminator = area.getTeamDeterminant();
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
        int vars;
        for (Map.Entry<Subject, List<List<WinRate>>> integerListEntry : subjectRate.entrySet()) {
            List<List<WinRate>> rateList = integerListEntry.getValue();
            List<WinRate> averageRateList = new ArrayList<>();
            for (int i = 0; i < numberOfTeams; i++) {
                min = 0;
                max = 0;
                vars = 0;
                for (List<WinRate> winRates : rateList) {
                    WinRate winRate = winRates.get(i);
                    min += winRate.getMinWinRate();
                    max += winRate.getMaxWinRate();
                    vars += winRate.getCalculatedVariants();
                }
                averageRateList.add(new WinRate(min / rateList.size(), max / rateList.size(), vars));
            }
            map.put(integerListEntry.getKey(), averageRateList);
        }
        return map;
    }


    private <T extends SubjectsArea> List<WinRate> subjectAverageWinRateOfSimultaneousActionsToTeamWinRate(Map<Subject, List<WinRate>> winRateForTeam, T area) {
        float min;
        float max;
        int vars;
        int numberOfSubjects;
        int subjectsCountForTeam;
        List<WinRate> rates = new ArrayList<>();
        for (int i = 0; i < area.getNumberOfTeams(); i++) {
            min = 0;
            max = 0;
            vars = 0;
            numberOfSubjects = 0;
            for (Map.Entry<Subject, List<WinRate>> subjectListEntry : winRateForTeam.entrySet()) {
                subjectsCountForTeam = area.getTeamDeterminant().getSubjectsCountForTeam(area, subjectListEntry.getKey());
                numberOfSubjects += subjectsCountForTeam;
                WinRate rate = subjectListEntry.getValue().get(i);
                min += (rate.getMinWinRate() * subjectsCountForTeam);
                max += (rate.getMaxWinRate() * subjectsCountForTeam);
                vars += rate.getCalculatedVariants();
            }
            rates.add(new WinRate(min / numberOfSubjects, max / numberOfSubjects, vars));
        }
        return rates;
    }
}