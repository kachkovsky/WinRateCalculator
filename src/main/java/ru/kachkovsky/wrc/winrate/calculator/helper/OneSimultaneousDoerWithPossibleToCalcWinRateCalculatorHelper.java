package ru.kachkovsky.wrc.winrate.calculator.helper;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.subject.Subject;
import ru.kachkovsky.wrc.team.SubjectTeamAreaDeterminant;
import ru.kachkovsky.wrc.winrate.WinRate;
import ru.kachkovsky.wrc.winrate.WinRateUtils;
import ru.kachkovsky.wrc.winrate.calculator.ActionResults;

import java.util.List;

public class OneSimultaneousDoerWithPossibleToCalcWinRateCalculatorHelper<T extends SubjectsArea<T>> implements ResultsToWinRateCalculatorHelper<T> {

    public static class DetailedResults {
        public List<WinRate> wrList;
        public boolean hasIndirectWRDependencies;
    }

    @Override
    public List<WinRate> calc(List<ActionResults<T>> actionResultsList, T areaBeforeAction) {
        SubjectTeamAreaDeterminant<T> subjectTeamAreaDeterminant = areaBeforeAction.getTeamDeterminant();
        Subject subject = areaBeforeAction.getCurrentSubject();
        int teamIndex = subjectTeamAreaDeterminant.getTeamIndex(areaBeforeAction, subject);
        WinRate best = WinRateUtils.LOSE;
        for (ActionResults<T> results : actionResultsList) {
            best = WinRateUtils.calcRiskyBest(results.getWrList().get(teamIndex), best);
        }
        return WinRateUtils.twoItemsWRListByWRAndIndex(best, teamIndex);
    }

    public DetailedResults calcDetailed(List<ActionResults<T>> actionResultsList, T areaBeforeAction) {
        DetailedResults r = new DetailedResults();
        SubjectTeamAreaDeterminant<T> subjectTeamAreaDeterminant = areaBeforeAction.getTeamDeterminant();
        Subject subject = areaBeforeAction.getCurrentSubject();
        int teamIndex = subjectTeamAreaDeterminant.getTeamIndex(areaBeforeAction, subject);
        WinRate best = WinRateUtils.LOSE;
        WinRate buf;
        actionResultsList.sort((o1, o2) -> (o1.isIndirectWRDependency() ? 1 : 0) - (o2.isIndirectWRDependency() ? 1 : 0));
        for (ActionResults<T> results : actionResultsList) {
            buf = WinRateUtils.calcRiskyBest(results.getWrList().get(teamIndex), best);
            if (buf != best) {
                r.hasIndirectWRDependencies |= results.isIndirectWRDependency();
            }
            best = buf;
        }
        r.wrList = WinRateUtils.twoItemsWRListByWRAndIndex(best, teamIndex);
        return r;
    }
}
