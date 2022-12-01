package com.joeygibson.aoc2022.day1

import org.apache.logging.log4j.LogManager
import picocli.CommandLine
import java.io.File
import java.io.IOException
import java.util.concurrent.Callable
import kotlin.system.exitProcess

@CommandLine.Command(
    name = "day1",
    mixinStandardHelpOptions = true,
    version = ["day1 1.0.0"],
    description = ["AoC 2022 day1"]
)
class App : Callable<Int> {
    @CommandLine.Parameters(index = "0", description = ["The file to process"])
    val file: File? = null

    @Throws(IOException::class)
    override fun call(): Int {
        if (file == null) {
            println("Usage: day1 <input file>")
            exitProcess(1)
        }

        val lines = readInput(file)

        val calories = lines.fold(mutableListOf(0)) { acc, line ->
            if (line.isNotBlank()) {
                val last = acc.removeLastOrNull() ?: 0
                acc.add(last + line.toInt())
            } else {
                acc.add(0)
            }

            acc
        }

        println("part1: ${calories.max()}")

        val topThreeTotal = calories.sortedDescending()
            .take(3)
            .sum()

        println("part2: $topThreeTotal")

        return 0
    }

    companion object {
        private val logger = LogManager.getLogger(
            App::class.java
        )
    }
}
