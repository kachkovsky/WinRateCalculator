package ru.kachkovsky.wrc_summoners_duel_ab.turn.evaluator

import ru.kachkovsky.wrc_ab.turn.evaluator.WinPositionEvaluator
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea
import ru.kachkovsky.wrc_summoners_duel.player.UnitUtils

class SDNextTurnWinPositionEvaluator : WinPositionEvaluator<ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea> {
    override fun evaluatePosition(area: ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea): Float? {
        val indexToWin = area.currentTeamIndex
        val indexToLose = area.reversePlayerIndex
        val curTeam = area.teams[indexToWin]
        val otherTeam = area.teams[indexToLose]
        val def = ru.kachkovsky.wrc_summoners_duel.player.UnitUtils.minDefForUnitAliveAfterAttackByAllWithMaxEconomic(otherTeam.units)
        val manaToBuyHp = ru.kachkovsky.wrc_summoners_duel.player.UnitUtils.findMaxAttack(otherTeam.units) - def / 2
        if (curTeam.mp > def + manaToBuyHp) {
            val atk = curTeam.mp - def - manaToBuyHp
            if (atk > otherTeam.hp) {
                return if (indexToWin == 0) Float.POSITIVE_INFINITY else Float.NEGATIVE_INFINITY
            }
        }
        return null
    }
}