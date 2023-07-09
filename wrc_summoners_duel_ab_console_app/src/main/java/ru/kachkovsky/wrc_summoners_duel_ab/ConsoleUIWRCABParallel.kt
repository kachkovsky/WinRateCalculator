package ru.kachkovsky.wrc_summoners_duel_ab

import kotlinx.coroutines.*
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

    class Iteration(val depth: Int, val results: List<Pair<Float, Long>>)

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
            println("Turns Number: ${actionList.size}")
            var pick: Int
            runBlocking {
                val job = launch(Dispatchers.IO) {
                    var calculating = true
                    var depth = 1

                    var iter: Iteration? = null

                    launch(Dispatchers.IO) {
                        var prev: Iteration? = null
                        while (true) {
                            delay(500)
                            iter?.let {
                                if (it != prev) {
                                    println("-------------------")
                                    println("Depth: $depth, actions: ${actionList.size}")
                                    for (i in 0 until actionList.size) {
                                        printAction(
                                            "${i + 1}",
                                            actionList[i],
                                            " result: " + it.results[i].first + " time:" + it.results[i].second
                                        )
                                    }
                                }
                                prev = it
                            }
                        }
                    }
                    while (calculating) {
                        calculating = false
                        //println("-------------------")
                        //println("Depth: $depth")

                        val defArr = Array(actionList.size) {
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
                        }
                        val list = mutableListOf<Pair<Float, Long>>()

                        for (i in 0 until defArr.size) {

                            //val action = actionList[i]
                            val p = defArr[i].await()
                            if (p.first != Float.POSITIVE_INFINITY && p.first != Float.NEGATIVE_INFINITY) {
                                calculating = true
                            }
                            list.add(p)
                            //printAction("x", action, "result: " + p.first + " time:" + p.second)
                        }
                        iter = Iteration(depth, list)
                        depth++
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
                } while (!(pick >= 0 && pick < actionList.size))
                job.cancel()
                area = actionList[pick].calcAct(area)
            }
        }
    }
}