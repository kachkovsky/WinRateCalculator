package ru.kachkovsky.wrc.stage;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.eventsgraph.EventGraphNode;

public interface Action<T extends SubjectsArea> {
    EventGraphNode<T> calcAct(T area);

    //Subject getSubject();
}
