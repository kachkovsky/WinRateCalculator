package ru.kachkovsky.wrc.winrate.calculator.helper;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.subject.Subject;
import ru.kachkovsky.wrc.team.SubjectTeamAreaDeterminant;
import ru.kachkovsky.wrc.winrate.WinRate;
import ru.kachkovsky.wrc.winrate.WinRateUtils;
import ru.kachkovsky.wrc.winrate.calculator.ActionResults;

import java.util.List;

public class OneSimultaneousDoerWithPossibleToCalcWinRateCalculatorHelper<T extends SubjectsArea<T>> implements ResultsToWinRateCalculatorHelper<T> {

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


}
