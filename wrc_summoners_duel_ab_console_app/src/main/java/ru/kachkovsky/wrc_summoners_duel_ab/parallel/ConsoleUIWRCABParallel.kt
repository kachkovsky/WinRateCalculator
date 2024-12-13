package ru.kachkovsky.wrc_summoners_duel_ab.parallel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.kachkovsky.wrc.stage.strategy.StageActionsStrategyResolver
import ru.kachkovsky.wrc_ab.MinMaxCalculator
import ru.kachkovsky.wrc_console_ui.ConsoleUI
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea
import ru.kachkovsky.wrc_summoners_duel_ab.turn.evaluator.GameEndsEvaluator
import ru.kachkovsky.wrc_summoners_duel_ab.turn.evaluator.SDPositionEvaluator
import java.util.*

class ConsoleUIWRCABParallel : ConsoleUI() {
    private val calculator = MinMaxCalculator<SummonersDuelSubjectsArea>()
    private val evaluator = SDPositionEvaluator()
    private val gameEndsEvaluator = GameEndsEvaluator()

    private val turnIterationsParallelUI = TurnIterationsParallelUI()

    fun ui(
        areaOuter: SummonersDuelSubjectsArea,
        stageActionsStrategyResolver: StageActionsStrategyResolver<SummonersDuelSubjectsArea>
    ) {
        //System.setProperty("kotlinx.coroutines.debug", "on" )
        val scanner = Scanner(System.`in`)
        val consoleUI = ConsoleUI()
        var area = areaOuter
        while (true) {
            consoleUI.printSubjects(area)
            consoleUI.writeCurrentArea("", area)

            val actionList = stageActionsStrategyResolver.resolve(area)
            var pick: Int
            runBlocking {
                turnIterationsParallelUI.preparePrint { i, actions ->
                    printAction(
                        "${i + 1}",
                        actionList[i],
                        " result: " + actions[i].first + " time:" + actions[i].second
                    )
                }
                val iterationsJob = launch(Dispatchers.IO) {
                    var calculating = true
                    var depth = 1
                    while (calculating) {
                        calculating = false
                        //println("-------------------")
                        //println("Depth: $depth")

                        val chanceTimePairList = Array(actionList.size) {
                            val action = actionList[it]
                            async(Dispatchers.Default) {
                                val innerArea = action.calcAct(area)
                                val t = System.currentTimeMillis()
                                val result = calculator.calcPosition(
                                    area = innerArea,
                                    stageActionsStrategyResolver = stageActionsStrategyResolver,
                                    evaluator = evaluator,
                                    gameEndsEvaluator = gameEndsEvaluator,
                                    depth = depth,
                                    prune = if (innerArea.currentPlayerUnitIndex == 0) Float.POSITIVE_INFINITY else Float.NEGATIVE_INFINITY,
                                )
                                Pair(result, System.currentTimeMillis() - t)
                            }
                        }.map {
                            val p = it.await()
                            if (p.first != Float.POSITIVE_INFINITY && p.first != Float.NEGATIVE_INFINITY) {
                                calculating = true
                            }
                            p
                        }
                        turnIterationsParallelUI.setIteration(
                            TurnIterationsParallelUI.Iteration(
                                depth,
                                chanceTimePairList
                            )
                        )
                        depth++
                    }
                }
                turnIterationsParallelUI.startTurnAndWaitEnter(iterationsJob, scanner).join()
                do {
                    print("Enter actionIndex: ")
                    pick = scanner.nextInt()
                    scanner.nextLine()
//                    if (pick == 0) {
//                        job.cancel()
//                    }
                    pick--
                } while (!(pick >= 0 && pick < actionList.size))
                area = actionList[pick].calcAct(area)
            }
        }
    }
}