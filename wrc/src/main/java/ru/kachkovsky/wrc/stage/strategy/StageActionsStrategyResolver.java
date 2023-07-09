package ru.kachkovsky.wrc.stage.strategy;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.stage.Stage;

import java.util.List;

@FunctionalInterface
public interface StageActionsStrategyResolver<T extends SubjectsArea<T>> {

    StageActionsStrategy<T> resolve(Stage<T> stage);

    default List<Action<T>> resolve(T area) {
        return resolve(area.getCurrentStage()).getActions(area);
    }
}
