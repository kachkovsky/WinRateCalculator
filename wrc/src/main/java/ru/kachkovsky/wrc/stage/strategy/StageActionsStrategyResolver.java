package ru.kachkovsky.wrc.stage.strategy;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.stage.Stage;

@FunctionalInterface
public interface StageActionsStrategyResolver<T extends SubjectsArea<T>> {

    StageActionsStrategy<T> resolve(Stage<T> stage);
}
