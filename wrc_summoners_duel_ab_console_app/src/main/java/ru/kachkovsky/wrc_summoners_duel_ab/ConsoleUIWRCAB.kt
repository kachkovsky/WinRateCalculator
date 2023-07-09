package ru.kachkovsky.wrc_summoners_duel_ab

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.kachkovsky.wrc.eventsgraph.TurnNode
import ru.kachkovsky.wrc.stage.strategy.StageActionsStrategyResolver
import ru.kachkovsky.wrc_ab.MinMaxCalculator
import ru.kachkovsky.wrc_console_ui.ConsoleUI
import ru.kachkovsky.wrc_console_ui.ConsoleUIWRC
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea
import ru.kachkovsky.wrc_summoners_duel_ab.turn.evaluator.GameEndsEvaluator
import ru.kachkovsky.wrc_summoners_duel_ab.turn.evaluator.SDPositionEvaluator
import java.util.*
import kotlin.system.exitProcess

class ConsoleUIWRCAB : ConsoleUI() {
    private val calculator = MinMaxCalculator<SummonersDuelSubjectsArea>()
    private val evaluator = SDPositionEvaluator()
    private val gameEndsEvaluator = GameEndsEvaluator()

    fun ui(
        areaOuter: SummonersDuelSubjectsArea,
        stageActionsStrategyResolver: StageActionsStrategyResolver<SummonersDuelSubjectsArea>
    ) {
        val consoleUI = ConsoleUI()
        var area = areaOuter
        while(true) {
            consoleUI.printSubjects(area)
            consoleUI.writeCurrentArea("",area)

            val actionList = stageActionsStrategyResolver.resolve(area)
            var pick = 0
            runBlocking {
                val job = launch(Dispatchers.Default) {
                    for (depth in 1..20) {
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
                }
                val scanner = Scanner(System.`in`)
                do {
                    pick = scanner.nextInt()
                    if(pick==0){
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