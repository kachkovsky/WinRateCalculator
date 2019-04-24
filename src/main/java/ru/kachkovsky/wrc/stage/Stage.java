package ru.kachkovsky.wrc.stage;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.subject.Subject;
import ru.kachkovsky.wrc.winrate.WinRate;

import java.util.List;
import java.util.Map;

public abstract class Stage<T extends SubjectsArea> {
    private List<FinishCheck<T>> finishCheckList;

    public Stage(List<FinishCheck<T>> finishCheckList) {
        this.finishCheckList = finishCheckList;
    }


    public Map<Subject, WinRate> calcFinishChecks(T area) {
        if (finishCheckList != null) {
            for (FinishCheck<T> finishCheck : finishCheckList) {
                Map<Subject, WinRate> check = finishCheck.check(area);
                if (check != null) {
                    return check;
                }
            }
        }
        return null;
    }

    public abstract List<Action<T>> getActions(T area);
}
