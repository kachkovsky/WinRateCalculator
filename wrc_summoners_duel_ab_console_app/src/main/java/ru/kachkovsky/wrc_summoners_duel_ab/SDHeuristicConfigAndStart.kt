package ru.kachkovsky.wrc_summoners_duel_ab

import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsAreaFactory
import ru.kachkovsky.wrc_summoners_duel.stage.strategy.SDBeatBaseBotStrategy
import ru.kachkovsky.wrc_summoners_duel.stage.strategy.SDBuyBaseStrategy
import ru.kachkovsky.wrc_summoners_duel.stage.strategy.SDStrategyUtils


object SDHeuristicConfigAndStart {
    @JvmStatic
    fun start() {
        val area = SummonersDuelSubjectsAreaFactory.createNewGameArea(12)
        val sdBuyBaseStrategy = SDBuyBaseStrategy()
        val sdBeatBaseBotStrategy = SDBeatBaseBotStrategy()
        ConsoleUIWRCAB().ui(area, SDStrategyUtils.createResolver(sdBuyBaseStrategy, sdBeatBaseBotStrategy))
    }
}