package com.joeygibson.aoc2022.day1

import org.apache.logging.log4j.LogManager
import picocli.CommandLine
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.util.concurrent.Callable

@CommandLine.Command(
    name = "day1",
    mixinStandardHelpOptions = true,
    version = ["day1 1.0.0"],
    description = ["AoC 2022 day1"]
)
class Cli : Callable<Int> {
    @CommandLine.Parameters(index = "0..*", description = ["The file(s) to process"])
    var files: Array<File>? = null

    @Throws(IOException::class)
    override fun call(): Int {
        files?.forEach { file ->
            val fileName = file.canonicalPath

            val elvesCalories = mutableListOf<Int>()
            val calories = mutableListOf<Int>()

            BufferedReader(file.reader()).useLines { lines ->
                lines.forEach { line ->
                    if (line.isBlank()) {
                        if (!calories.isEmpty()) {
                            elvesCalories.add(calories.sum())
                            calories.clear()
                        }
                    } else {
                        calories.add(line.toInt())
                    }
                }
            }

            println("part1: ${elvesCalories.max()}")

            val topThreeTotal = elvesCalories.sortedDescending()
                .take(3)
                .sum()

            println("part2: $topThreeTotal")
        }

        return 0
    }

    companion object {
        private val logger = LogManager.getLogger(
            Cli::class.java
        )
    }
}
