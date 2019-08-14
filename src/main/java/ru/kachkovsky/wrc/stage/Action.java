package ru.kachkovsky.wrc.stage;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.eventsgraph.TurnNode;

public interface Action<T extends SubjectsArea<T>> {
    TurnNode<T> calcAct(T area);
}
