package ru.kachkovsky.wrc_ab.turn.evaluator

import ru.kachkovsky.wrc.SubjectsArea

fun interface WinPositionEvaluator<T : SubjectsArea<T>> {
    fun evaluatePosition(area: T): Float?
}
