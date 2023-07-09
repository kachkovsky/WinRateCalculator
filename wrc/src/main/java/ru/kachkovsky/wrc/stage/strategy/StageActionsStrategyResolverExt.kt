package ru.kachkovsky.wrc.stage.strategy

import ru.kachkovsky.wrc.SubjectsArea
import ru.kachkovsky.wrc.stage.Action

fun <T : SubjectsArea<T>> StageActionsStrategyResolver<T>.resolve(area: T): MutableList<Action<T>> {
    return this.resolve(area.currentStage).getActions(area)
}