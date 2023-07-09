package ru.kachkovsky.wrc_summoners_duel_ab.turn.evaluator

import ru.kachkovsky.wrc_ab.turn.evaluator.WinPositionEvaluator
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea

class GameEndsEvaluator : WinPositionEvaluator<SummonersDuelSubjectsArea> {
    val eval1 = SDWinNowPositionEvaluator()

    override fun evaluatePosition(area: SummonersDuelSubjectsArea): Float? {
        return eval1.evaluatePosition(area)
    }
}