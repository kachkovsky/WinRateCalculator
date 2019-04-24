package ru.kachkovsky.wrc.stage;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.winrate.WinRate;

import java.util.List;

public abstract class Stage<T extends SubjectsArea> {
    private List<FinishCheck<T>> finishCheckList;

    public Stage(List<FinishCheck<T>> finishCheckList) {
        this.finishCheckList = finishCheckList;
    }


    public List<WinRate> calcFinishChecks(T area) {
        if (finishCheckList != null) {
            for (FinishCheck<T> finishCheck : finishCheckList) {
                List<WinRate> check = finishCheck.checkTeamsWinRate(area);
                if (check != null) {
                    return check;
                }
            }
        }
        return null;
    }

    public abstract List<Action<T>> getActions(T area);
}
