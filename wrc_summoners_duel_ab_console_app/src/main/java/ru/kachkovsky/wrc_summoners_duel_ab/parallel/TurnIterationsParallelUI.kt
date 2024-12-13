package ru.kachkovsky.wrc_summoners_duel_ab.parallel

import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

class TurnIterationsParallelUI : CoroutineScope {
    class Iteration(val depth: Int, val results: List<Pair<Float, Long>>)

    private var iteration: Iteration? = null
    private var printJob: Job? = null

    override val coroutineContext: CoroutineContext = Job() + newSingleThreadContext("my")

    fun setIteration(iteration: Iteration) {
        launch { this@TurnIterationsParallelUI.iteration = iteration }
    }

    fun preparePrint(
        printAction: (index: Int, actionList: List<Pair<Float, Long>>) -> Unit
    ) {
        printJob?.cancel()
        printJob = launch {
            iteration = null
            printSomeIterationsWithDelay(printAction)
        }
    }

    fun startTurnAndWaitEnter(
        iterationsJob: Job,
        scanner: Scanner,
    ): Job {
        return launch {
            withContext(Dispatchers.IO) { scanner.nextLine() }
            iterationsJob.cancel()
            printJob?.cancel()
        }
    }

    private suspend fun printSomeIterationsWithDelay(printAction: (index: Int, actionList: List<Pair<Float, Long>>) -> Unit) {
        var prev: Iteration? = null
        while (isActive) {
            try {
                delay(500)
            } finally {
                iteration?.let {
                    if (it != prev) {
                        println("-------------------")
                        println("Depth: ${it.depth}, actions: ${it.results.size}")
                        for (i in 0 until it.results.size) {
                            printAction(i, it.results)
                        }
                    }
                    prev = it
                }
            }
        }
    }
}