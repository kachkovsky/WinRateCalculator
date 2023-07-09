package ru.kachkovsky.wrc_ab

import ru.kachkovsky.wrc.SubjectsArea
import ru.kachkovsky.wrc.stage.Action
import ru.kachkovsky.wrc.stage.strategy.StageActionsStrategyResolver
import ru.kachkovsky.wrc_ab.turn.evaluator.PositionEvaluator
import ru.kachkovsky.wrc_ab.turn.evaluator.WinPositionEvaluator
import ru.kachkovsky.wrc_summoners_duel.stage.strategy.SDStrategyUtils

class MinMaxCalculator<T : SubjectsArea<T>> {

    fun calcPosition(
        area: T,
        stageActionsStrategyResolver: StageActionsStrategyResolver<T>,
        evaluator: PositionEvaluator<T>,
        gameEndsEvaluator: WinPositionEvaluator<T>,
        depth: Int,
        prune: Float,
    ): Float {
        if (depth == 0) {
            return evaluator.evaluatePosition(area)
        } else {
            val pos = gameEndsEvaluator.evaluatePosition(area)
            if (pos != null)
                return pos
        }
        val areaList: MutableList<T> = mutableListOf()
        calcPositionInner(area, stageActionsStrategyResolver, areaList)
        //println("areaList:${areaList.size}")
        var result = worstResult(area)
        for (a in areaList) {
            result = updateValue(
                result,
                calcPosition(a, stageActionsStrategyResolver, evaluator, gameEndsEvaluator, depth - 1, result),
                area,
            )
            if (needPrune(result, prune, area)) {
                break
            }
        }
        return result
    }

    private fun calcPositionInner(
        area: T,
        stageActionsStrategyResolver: StageActionsStrategyResolver<T>,
        areaList: MutableList<T>
    ) {
        var pairsToIterate = toPairList(area, stageActionsStrategyResolver.resolve(area))

        while (!pairsToIterate.isEmpty()) {
            val pairs = pairsToIterate
            pairsToIterate = mutableListOf()
            for (p in pairs) {
                val nextArea = p.first.calcAct(p.second)
                if (nextArea.currentStage != SDStrategyUtils.FIRST_STAGE) {
                    val toPairList = toPairList(nextArea, stageActionsStrategyResolver.resolve(nextArea))
                    pairsToIterate.addAll(toPairList)
                } else {
                    areaList += nextArea
                }
            }
        }
    }

    private fun toPairList(area: T, actions: List<Action<T>>): List<Pair<Action<T>, T>> {
        val pairList = mutableListOf<Pair<Action<T>, T>>()
        for (a in actions) {
            pairList.add(Pair(a, area))
        }
        return pairList
    }

    private fun worstResult(area: T): Float {
        return if (area.currentSubjectIndex == 0) Float.NEGATIVE_INFINITY else Float.POSITIVE_INFINITY
    }

    private fun updateValue(value: Float, newValue: Float, area: T): Float {
        return if (area.currentSubjectIndex == 0) Math.max(value, newValue) else Math.min(value, newValue)
    }

    private fun needPrune(newValue: Float, prune: Float, area: T): Boolean {
        //> and < can be replaced to >= and <=, but last values cann't be result
        return if (area.currentSubjectIndex == 0) prune <= newValue else prune >= newValue
    }
}