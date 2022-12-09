package com.joeygibson.aoc2022.day9

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
        val headPos = Knot('H', 0, 0)
        val tailPos = Knot('T', 0, 0)
        var xCatchUp = 0
        var yCatchUp = 0
        val tailMoves = mutableSetOf<Pair<Int, Int>>(tailPos.coordinates)

        for (move in moves) {
            repeat(move.second) {
                when (move.first) {
                    "R" -> {
                        headPos.x += 1
                        xCatchUp = -1
                        yCatchUp = 0
                    }

                    "L" -> {
                        headPos.x -= 1
                        xCatchUp = 1
                        yCatchUp = 0
                    }

                    "U" -> {
                        headPos.y += 1
                        xCatchUp = 0
                        yCatchUp = -1
                    }

                    "D" -> {
                        headPos.y -= 1
                        xCatchUp = 0
                        yCatchUp = 1
                    }
                }

                if (distance(headPos, tailPos) > 1) {
                    tailPos.x = headPos.x + xCatchUp
                    tailPos.y = headPos.y + yCatchUp
                    tailMoves.add(tailPos.coordinates)
                }
            }
        }

        return tailMoves.size
    }

    private fun distance(head: Knot, tail: Knot): Int {
        return Math.sqrt(
            Math.pow((tail.x - head.x).toDouble(), 2.0) +
                    Math.pow((tail.y - head.y).toDouble(), 2.0)
        ).toInt()
    }

    private fun part2(moves: List<Pair<String, Int>>): Any {
        val knots = CharRange('A', 'J')
            .map { Knot(it, 0, 0) }
            .toList()

        val tailMoves = mutableSetOf<Pair<Int, Int>>(knots[9].coordinates)

        for (move in moves) {
            repeat(move.second) {
                when (move.first) {
                    "R" -> {
                        knots[0].x += 1
                    }

                    "L" -> {
                        knots[0].x -= 1
                    }

                    "U" -> {
                        knots[0].y += 1
                    }

                    "D" -> {
                        knots[0].y -= 1
                    }
                }

                for (i in 1..9) {
                    if (distance(knots[i - 1], knots[i]) > 1) {
                        var xDiff = knots[i - 1].x - knots[i].x
                        var yDiff = knots[i - 1].y - knots[i].y

                        if (xDiff == 2) {
                            xDiff = 1
                        } else if (xDiff == -2) {
                            xDiff = -1
                        }

                        if (yDiff == 2) {
                            yDiff = 1
                        } else if (yDiff == -2) {
                            yDiff = -1
                        }

                        knots[i].x += xDiff
                        knots[i].y += yDiff

                        assert(distance(knots[i - 1], knots[i]) <= 1)

                        if (i == 9) {
                            tailMoves.add(knots[i].coordinates)
                        }
                    }
                }
            }
        }

        return tailMoves.size
    }
}

data class Knot(val name: Char, var x: Int, var y: Int) {
    val coordinates: Pair<Int, Int>
        get() = Pair(this.x, this.y)
}