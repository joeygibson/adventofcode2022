package com.joeygibson.aoc2022.day9

import org.apache.commons.lang3.tuple.MutablePair
import picocli.CommandLine
import java.io.File
import java.io.IOException
import java.util.concurrent.Callable
import kotlin.system.exitProcess

@CommandLine.Command(
    name = "day9",
    mixinStandardHelpOptions = true,
    version = ["day9 1.0.0"],
    description = ["AoC 2022 day9"]
)
class App : Callable<Int> {
    @CommandLine.Parameters(index = "0", description = ["The file to process"])
    val file: File? = null

    @Throws(IOException::class)
    override fun call(): Int {
        if (file == null) {
            println("Usage: day9 <input file>")
            exitProcess(1)
        }

        val moves = readWithoutBlanks(file)
            .map {
                val chunks = it.split(" ")
                Pair(chunks[0], chunks[1].toInt())
            }

        printResults("part1", part1(moves))
        printResults("part2", part2(moves))

        return 0
    }

    private fun part1(moves: List<Pair<String, Int>>): Any {
        val headPos = MutablePair(0, 0)
        val tailPos = MutablePair(0, 0)
        var xCatchUp = 0
        var yCatchUp = 0
        val tailMoves = mutableSetOf<Pair<Int, Int>>(tailPos.toPair())

        for (move in moves) {
            repeat(move.second) {
                when (move.first) {
                    "R" -> {
                        headPos.left += 1
                        xCatchUp = -1
                        yCatchUp = 0
                    }

                    "L" -> {
                        headPos.left -= 1
                        xCatchUp = 1
                        yCatchUp = 0
                    }

                    "U" -> {
                        headPos.right += 1
                        xCatchUp = 0
                        yCatchUp = -1
                    }

                    "D" -> {
                        headPos.right -= 1
                        xCatchUp = 0
                        yCatchUp = 1
                    }
                }

                if (distance(headPos, tailPos) > 1) {
                    tailPos.left = headPos.left + xCatchUp
                    tailPos.right = headPos.right + yCatchUp
                    tailMoves.add(tailPos.toPair())
                }
            }
        }

        return tailMoves.size
    }

    private fun distance(head: MutablePair<Int, Int>, tail: MutablePair<Int, Int>): Int {
        return Math.sqrt(
            Math.pow((tail.left - head.left).toDouble(), 2.0) +
                    Math.pow((tail.right - head.right).toDouble(), 2.0)
        ).toInt()
    }

    private fun part2(moves: List<Pair<String, Int>>): Any {
        val knots = MutableList(10) { MutablePair(0, 0) }

        val tailMoves = mutableSetOf<Pair<Int, Int>>(knots[9].toPair())

        for (move in moves) {
            repeat(move.second) {
                when (move.first) {
                    "R" -> {
                        knots[0].left += 1
                    }

                    "L" -> {
                        knots[0].left -= 1
                    }

                    "U" -> {
                        knots[0].right += 1
                    }

                    "D" -> {
                        knots[0].right -= 1
                    }
                }

                for (i in 1..9) {
                    if (distance(knots[i - 1], knots[i]) > 1) {
                        var xDiff = knots[i - 1].left - knots[i].left
                        var yDiff = knots[i - 1].right - knots[i].right

                        when (move.first) {
                            "U" -> yDiff -= 1
                            "D" -> yDiff -= 1
                            "L" -> xDiff -= 1
                            "R" -> xDiff -= 1
                        }

                        knots[i].left += xDiff
                        knots[i].right += yDiff

                        assert(distance(knots[i - 1], knots[i]) <= 1)

                        if (i == 9) {
                            tailMoves.add(knots[i].toPair())
                        }
                    }
                }
            }
        }

        return tailMoves.size
    }
}