package com.joeygibson.aoc2022.day2

import picocli.CommandLine
import java.io.File
import java.io.IOException
import java.util.concurrent.Callable
import kotlin.system.exitProcess

private const val BAD_MOVE = "bad move"

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

        val rounds = readInput(file)

        println("part1: ${part1(rounds)}")
        println("part2: ${part2(rounds)}")

        return 0
    }

    private fun part1(rounds: List<String>): Int {
        val scores = rounds
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

        return scores.sum()
    }

    private fun part2(rounds: List<String>): Int {
        val fixedRounds = rounds
            .filter { it.isNotBlank() }
            .map { it.split(" ") }
            .map {
                val (him, me) = it

                val myMove = when (me) {
                    "X" -> lose(him)
                    "Y" -> draw(him)
                    "Z" -> win(him)
                    else -> throw Exception(BAD_MOVE)
                }

                "$him $myMove"
            }

        return part1(fixedRounds)
    }

    private fun win(him: String): String {
        return when (him) {
            "A" -> "Y"
            "B" -> "Z"
            "C" -> "X"
            else -> throw Exception(BAD_MOVE)
        }
    }

    private fun lose(him: String): String {
        return when (him) {
            "A" -> "Z"
            "B" -> "X"
            "C" -> "Y"
            else -> throw Exception(BAD_MOVE)
        }
    }

    private fun draw(him: String): String {
        return when (him) {
            "A" -> "X"
            "B" -> "Y"
            "C" -> "Z"
            else -> throw Exception(BAD_MOVE)
        }
    }
}
