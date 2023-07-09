package ru.kachkovsky.wrc_summoners_duel_ab

import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsAreaFactory
import ru.kachkovsky.wrc_summoners_duel.stage.strategy.SDBeatBaseBotStrategy
import ru.kachkovsky.wrc_summoners_duel.stage.strategy.SDBuyBaseStrategy
import ru.kachkovsky.wrc_summoners_duel.stage.strategy.SDStrategyUtils
import java.util.*


object SDHeuristicConfigAndStart {
    @JvmStatic
    fun start() {
        print("Select mages hp:")
        val scanner = Scanner(System.`in`)
        val pick = scanner.nextInt()
        val area = SummonersDuelSubjectsAreaFactory.createNewGameArea(pick)
        val sdBuyBaseStrategy = SDBuyBaseStrategy()
        val sdBeatBaseBotStrategy = SDBeatBaseBotStrategy()
        print("Select mode (d) details, (p) parallel:")

        do {
            var scannedIncorrect = false
            val mode = scanner.nextLine()
            when (mode) {
                "d" -> {
                    print("Select minDepth(1 or more): ")
                    val minDepth = scanner.nextInt()
                    print("Select maxDepth: ")
                    val maxDepth = scanner.nextInt()
                    ConsoleUIWRCAB().ui(
                        area,
                        SDStrategyUtils.createResolver(sdBuyBaseStrategy, sdBeatBaseBotStrategy),
                        minDepth,
                        maxDepth,
                    )

                }

                "p" -> ConsoleUIWRCABParallel().ui(
                    area,
                    SDStrategyUtils.createResolver(sdBuyBaseStrategy, sdBeatBaseBotStrategy)
                )

                else -> scannedIncorrect = true
            }
        } while (scannedIncorrect)
    }
}