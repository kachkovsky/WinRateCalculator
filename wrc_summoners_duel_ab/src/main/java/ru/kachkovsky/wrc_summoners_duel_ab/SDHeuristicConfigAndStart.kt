package ru.kachkovsky.wrc_summoners_duel_ab

import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsAreaFactory
import ru.kachkovsky.wrc_summoners_duel.stage.strategy.SDBeatBaseBotStrategy
import ru.kachkovsky.wrc_summoners_duel.stage.strategy.SDBuyBaseStrategy
import ru.kachkovsky.wrc_summoners_duel.stage.strategy.SDStrategyUtils


object SDHeuristicConfigAndStart {
    @JvmStatic
    fun start() {
        val area = ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsAreaFactory.createNewGameArea(12)
        val sdBuyBaseStrategy = ru.kachkovsky.wrc_summoners_duel.stage.strategy.SDBuyBaseStrategy()
        val sdBeatBaseBotStrategy =
            ru.kachkovsky.wrc_summoners_duel.stage.strategy.SDBeatBaseBotStrategy()
        ConsoleUIWRCAB().ui(area, ru.kachkovsky.wrc_summoners_duel.stage.strategy.SDStrategyUtils.createResolver(sdBuyBaseStrategy, sdBeatBaseBotStrategy))
    }
}