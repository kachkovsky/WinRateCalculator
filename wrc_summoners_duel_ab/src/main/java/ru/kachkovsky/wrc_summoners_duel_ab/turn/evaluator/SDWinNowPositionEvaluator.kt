package ru.kachkovsky.wrc_summoners_duel_ab.turn.evaluator

import ru.kachkovsky.wrc_ab.turn.evaluator.WinPositionEvaluator
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea
import ru.kachkovsky.wrc_summoners_duel.player.UnitUtils

class SDWinNowPositionEvaluator : WinPositionEvaluator<ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea> {

    override fun evaluatePosition(area: ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea): Float? {
        val result: Float?
        if (isWin(true, area)) {
            result = if (area.currentTeamIndex == 0) Float.POSITIVE_INFINITY else Float.NEGATIVE_INFINITY
        } else if (isHpWin(false, area)) {
            result = if (area.currentTeamIndex != 0) Float.POSITIVE_INFINITY else Float.NEGATIVE_INFINITY
        } else {
            result = null
        }
        return result
    }

    private fun isWin(currentPlayerWin: Boolean, area: ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea): Boolean {
        val indexToWin = if (currentPlayerWin) area.currentTeamIndex else area.reversePlayerIndex
        val indexToLose = if (currentPlayerWin) area.reversePlayerIndex else area.currentTeamIndex
        val curTeam = area.teams[indexToWin]
        val otherTeam = area.teams[indexToLose]
        return isHpWin(currentPlayerWin, area) || ru.kachkovsky.wrc_summoners_duel.player.UnitUtils.summaryAttack(curTeam.units) >= otherTeam.hp
    }

    private fun isHpWin(currentPlayerWin: Boolean, area: ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea): Boolean {
        val indexToLose = if (currentPlayerWin) area.reversePlayerIndex else area.currentTeamIndex
        return area.teams[indexToLose].hp <= 0
    }
}