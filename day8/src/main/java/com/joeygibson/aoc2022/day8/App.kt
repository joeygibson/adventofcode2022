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

        printResults("part1", part1(lines))
        printResults("part2", part2(lines))

        return 0
    }

    private fun part1(lines: List<String>): Any {
        val trees = lines.map {
            it.toCharArray().map { c -> c.toString().toInt() }
        }

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
                        println(thisTree)
                        thisTree
                    } else {
                        null
                    }
                }
            }
        }.count { it != null }
    }

    private fun part2(lines: List<String>): Any {
        // part 2 goes here

        return "no result for part 2"
    }


}
