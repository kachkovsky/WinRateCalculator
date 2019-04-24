package ru.kachkovsky.wrc.stage;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.subject.Subject;
import ru.kachkovsky.wrc.winrate.WinRate;

import java.util.Map;

public interface FinishCheck<T extends SubjectsArea> {

    Map<Subject, WinRate> check(T area);
}
