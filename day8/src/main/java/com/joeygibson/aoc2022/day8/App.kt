package com.joeygibson.aoc2022.day8

import picocli.CommandLine
import java.io.File
import java.io.IOException
import java.util.concurrent.Callable
import kotlin.system.exitProcess

@CommandLine.Command(
    name = "day8",
    mixinStandardHelpOptions = true,
    version = ["day8 1.0.0"],
    description = ["AoC 2022 day8"]
)
class App : Callable<Int> {
    @CommandLine.Parameters(index = "0", description = ["The file to process"])
    val file: File? = null

    @Throws(IOException::class)
    override fun call(): Int {
        if (file == null) {
            println("Usage: day8 <input file>")
            exitProcess(1)
        }

        val lines = readWithoutBlanks(file)

        val trees = lines.map {
            it.toCharArray().map { c -> c.toString().toInt() }
        }

        printResults("part1", part1(trees))
        printResults("part2", part2(trees))

        return 0
    }

    private fun part1(trees: List<List<Int>>): Any {
        return (0 until trees.size).flatMap { i ->
            (0 until trees[0].size).map { j ->
                val thisTree = Pair(i, j)

                if ((i == 0 || i == trees.size - 1) ||
                    (j == 0 || j == trees[0].size - 1)
                ) {
                    thisTree
                } else {
                    val thisTreeHeight = trees[i][j]

                    val aboveCount = (0 until i)
                        .count {
                            thisTreeHeight <= trees[it][j]
                        }

                    val belowCount = (i + 1 until trees.size)
                        .count {
                            thisTreeHeight <= trees[it][j]
                        }

                    val leftCount = (0 until j)
                        .count {
                            thisTreeHeight <= trees[i][it]
                        }

                    val rightCount = (j + 1 until trees[0].size)
                        .count {
                            thisTreeHeight <= trees[i][it]
                        }

                    if (aboveCount == 0 || belowCount == 0 ||
                        leftCount == 0 || rightCount == 0
                    ) {
                        thisTree
                    } else {
                        null
                    }
                }
            }
        }.count { it != null }
    }

    private fun part2(trees: List<List<Int>>): Any {
        val scores = (0 until trees.size).flatMap { i ->
            (0 until trees[0].size).map { j ->
                val thisTreeHeight = trees[i][j]

                var aboveCount = 1
                var belowCount = 1
                var leftCount = 1
                var rightCount = 1

                for (ii in (i - 1) downTo 0) {
                    if (trees[ii][j] < thisTreeHeight) {
                        aboveCount++
                    } else {
                        break
                    }
                }

                for (ii in (i + 1) until trees.size) {
                    if (trees[ii][j] < thisTreeHeight) {
                        belowCount++
                    } else {
                        break
                    }
                }

                for (jj in (j - 1) downTo 0) {
                    if (trees[i][jj] < thisTreeHeight) {
                        leftCount++
                    } else {
                        break
                    }
                }

                for (jj in (j + 1) until trees[0].size) {
                    if (trees[i][jj] < thisTreeHeight) {
                        rightCount++
                    } else {
                        break
                    }
                }

                aboveCount * belowCount * leftCount * rightCount
            }
        }.max()

        println("scores: $scores")

        return "foo"
    }
}
