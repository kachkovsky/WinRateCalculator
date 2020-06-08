package ru.kachkovsky.wrc.winrate.calculator.helper;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.winrate.WinRate;
import ru.kachkovsky.wrc.winrate.calculator.ActionResults;

import java.util.List;

public interface ResultsToWinRateCalculatorHelper<T extends SubjectsArea<T>> {
    List<WinRate> calc(List<ActionResults<T>> actionResultsList, T areaBeforeAction);
}
