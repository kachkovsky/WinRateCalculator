package ru.kachkovsky.wrc_summoners_duel_ab.turn.evaluator

import ru.kachkovsky.wrc_ab.turn.evaluator.PositionEvaluator
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea
import ru.kachkovsky.wrc_summoners_duel.player.Player
import ru.kachkovsky.wrc_summoners_duel.player.Unit

class SDHeuristicPositionEvaluator : PositionEvaluator<SummonersDuelSubjectsArea> {
    override fun evaluatePosition(area: SummonersDuelSubjectsArea): Float {
        return calcPlayer(area.teams[0]) - calcPlayer(area.teams[1])
    }

    fun calcPlayer(player: Player): Float {
        return player.hp + player.mp + calcUnits(player.units)
    }

    fun calcUnits(units: List<Unit>): Float {
        var result = 0F
        for (unit in units) {
            result += unit.cost
        }
        return result
    }
}