package ru.kachkovsky.wrc.stage.strategy;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.stage.Action;

import java.util.List;

@FunctionalInterface
public interface StageActionsStrategy<T extends SubjectsArea<T>> {

    List<Action<T>> getActions(T area);

}
