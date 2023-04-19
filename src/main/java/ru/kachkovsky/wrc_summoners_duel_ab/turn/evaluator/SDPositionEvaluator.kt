package ru.kachkovsky.wrc_summoners_duel_ab.turn.evaluator

import ru.kachkovsky.wrc_ab.turn.evaluator.PositionEvaluator
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea

class SDPositionEvaluator : PositionEvaluator<SummonersDuelSubjectsArea> {
    val eval1 = GameEndsEvaluator()
    val finEval = SDHeuristicPositionEvaluator()
    override fun evaluatePosition(area: SummonersDuelSubjectsArea): Float {
        var result = eval1.evaluatePosition(area)
        if (result == null) {
            result = finEval.evaluatePosition(area)
        }
        return result
    }

}