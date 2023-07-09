package ru.kachkovsky.wrc.stage;

import ru.kachkovsky.wrc.SubjectsArea;

public interface Action<T extends SubjectsArea<T>> {
    T calcAct(T area);
}
