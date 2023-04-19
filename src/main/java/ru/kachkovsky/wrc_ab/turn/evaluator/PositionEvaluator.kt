package ru.kachkovsky.wrc_ab.turn.evaluator

import ru.kachkovsky.wrc.SubjectsArea

fun interface PositionEvaluator<T : SubjectsArea<T>> {
    fun evaluatePosition(area: T): Float
}
