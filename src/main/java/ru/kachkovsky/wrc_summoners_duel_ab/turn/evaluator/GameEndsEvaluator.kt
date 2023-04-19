package ru.kachkovsky.wrc_summoners_duel_ab.turn.evaluator

import ru.kachkovsky.wrc_ab.turn.evaluator.WinPositionEvaluator
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea

class GameEndsEvaluator : WinPositionEvaluator<SummonersDuelSubjectsArea> {
    val eval1 = SDWinNowPositionEvaluator()
    val eval2 = SDNextTurnWinPositionEvaluator()

    override fun evaluatePosition(area: SummonersDuelSubjectsArea): Float? {
        var result = eval1.evaluatePosition(area)
        if (result == null) {
            result = eval2.evaluatePosition(area)
        }
        return result
    }
}