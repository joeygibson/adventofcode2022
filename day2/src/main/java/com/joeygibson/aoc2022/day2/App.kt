package com.joeygibson.aoc2022.day2

import picocli.CommandLine
import java.io.File
import java.io.IOException
import java.util.concurrent.Callable
import kotlin.system.exitProcess

@CommandLine.Command(
    name = "day2",
    mixinStandardHelpOptions = true,
    version = ["day2 1.0.0"],
    description = ["AoC 2022 day2"]
)
class App : Callable<Int> {
    @CommandLine.Parameters(index = "0", description = ["The file to process"])
    val file: File? = null

    @Throws(IOException::class)
    override fun call(): Int {
        if (file == null) {
            println("Usage: day2 <input file>")
            exitProcess(1)
        }

        val scores = readInput(file)
            .filter { it.isNotBlank() }
            .map {
                when (it) {
                    "A X" -> 4
                    "A Y" -> 8
                    "A Z" -> 3
                    "B X" -> 1
                    "B Y" -> 5
                    "B Z" -> 9
                    "C X" -> 7
                    "C Y" -> 2
                    "C Z" -> 6
                    else -> throw Exception("bogus move: $it")
                }
            }

        val score = scores.sum()

        println("part1: $score")

        return 0
    }
}
