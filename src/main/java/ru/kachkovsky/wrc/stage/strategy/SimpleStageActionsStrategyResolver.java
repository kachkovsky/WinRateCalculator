package ru.kachkovsky.wrc.stage.strategy;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class SimpleStageActionsStrategyResolver<T extends SubjectsArea<T>> implements StageActionsStrategyResolver<T> {

    private Map<Stage<T>, StageActionsStrategy<T>> map = new HashMap<>();

    public void put(Stage<T> stage, StageActionsStrategy<T> strategy) {
        map.put(stage, strategy);
    }

    @Override
    public StageActionsStrategy<T> resolve(Stage<T> stage) {
        return map.get(stage);
    }
}
