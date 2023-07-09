package ru.kachkovsky.wrc_summoners_duel_ab

import ru.kachkovsky.wrc.stage.strategy.StageActionsStrategyResolver
import ru.kachkovsky.wrc_ab.MinMaxCalculator
import ru.kachkovsky.wrc_console_ui.ConsoleUI
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea
import ru.kachkovsky.wrc_summoners_duel_ab.turn.evaluator.GameEndsEvaluator
import ru.kachkovsky.wrc_summoners_duel_ab.turn.evaluator.SDPositionEvaluator

class ConsoleUIWRCAB : ConsoleUI() {
    private val calculator = MinMaxCalculator<SummonersDuelSubjectsArea>()
    private val evaluator = SDPositionEvaluator()
    private val gameEndsEvaluator = GameEndsEvaluator()

    fun ui(
        area: SummonersDuelSubjectsArea,
        stageActionsStrategyResolver: StageActionsStrategyResolver<SummonersDuelSubjectsArea>
    ) {
        val actionList = stageActionsStrategyResolver.resolve(area)
        for (depth in 1..10) {
            println("-------------------")
            println("Depth: $depth")
            var t = System.currentTimeMillis()
            for (action in actionList) {
                val innerArea = action.calcAct(area)
                val result = calculator.calcPosition(
                    area = innerArea,
                    stageActionsStrategyResolver = stageActionsStrategyResolver,
                    evaluator = evaluator,
                    gameEndsEvaluator = gameEndsEvaluator,
                    depth = depth,
                    prune = if (innerArea.currentPlayerUnitIndex == 0) Float.POSITIVE_INFINITY else Float.NEGATIVE_INFINITY,
                )
                printAction("x", action, "result: " + result + " time:" + (System.currentTimeMillis() - t))
                t = System.currentTimeMillis()
            }
        }
        //val scanner = Scanner(System.`in`)


    }
}