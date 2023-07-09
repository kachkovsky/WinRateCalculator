package ru.kachkovsky.wrc_summoners_duel_ab

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.kachkovsky.wrc.stage.strategy.StageActionsStrategyResolver
import ru.kachkovsky.wrc_ab.MinMaxCalculator
import ru.kachkovsky.wrc_console_ui.ConsoleUI
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea
import ru.kachkovsky.wrc_summoners_duel_ab.turn.evaluator.GameEndsEvaluator
import ru.kachkovsky.wrc_summoners_duel_ab.turn.evaluator.SDPositionEvaluator
import java.util.*

class ConsoleUIWRCAB : ConsoleUI() {
    private val calculator = MinMaxCalculator<SummonersDuelSubjectsArea>()
    private val evaluator = SDPositionEvaluator()
    private val gameEndsEvaluator = GameEndsEvaluator()

    fun ui(
        areaOuter: SummonersDuelSubjectsArea,
        stageActionsStrategyResolver: StageActionsStrategyResolver<SummonersDuelSubjectsArea>,
        minDepth: Int,
        maxDepth: Int,
    ) {
        val scanner = Scanner(System.`in`)
        val consoleUI = ConsoleUI()
        var area = areaOuter
        while (true) {
            consoleUI.printSubjects(area)
            consoleUI.writeCurrentArea("", area)

            val actionList = stageActionsStrategyResolver.resolve(area)
            var pick: Int
            runBlocking {
                val job = launch(Dispatchers.Default) {
                    for (depth in minDepth..maxDepth) {
                        println("-------------------")
                        println("Depth: $depth")
                        var t = System.currentTimeMillis()
                        for (i in 0 until actionList.size) {
                            val action = actionList[i]
                            val innerArea = action.calcAct(area)
                            val result = calculator.calcPosition(
                                area = innerArea,
                                stageActionsStrategyResolver = stageActionsStrategyResolver,
                                evaluator = evaluator,
                                gameEndsEvaluator = gameEndsEvaluator,
                                depth = depth,
                                prune = if (innerArea.currentPlayerUnitIndex == 0) Float.POSITIVE_INFINITY else Float.NEGATIVE_INFINITY,
                            )
                            printAction(
                                "${i + 1}",
                                action,
                                " result: " + result + " time:" + (System.currentTimeMillis() - t)
                            )
                            t = System.currentTimeMillis()
                        }
                    }
                }
                do {
                    scanner.nextLine()
                    job.cancelAndJoin()
                    print("Enter actionIndex: ")
                    pick = scanner.nextInt()
                    scanner.nextLine()
                    if (pick == 0) {
                        job.cancel()
                    }
                    pick--
                    val t = (pick >= 0 && pick < actionList.size)
                    val f = !t
                } while (!(pick >= 0 && pick < actionList.size))

                job.cancel()
                area = actionList[pick].calcAct(area)
            }

        }
    }
}