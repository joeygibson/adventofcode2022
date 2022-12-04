package com.joeygibson.aoc2022.day4

import picocli.CommandLine
import java.io.File
import java.io.IOException
import java.util.concurrent.Callable
import kotlin.system.exitProcess

@CommandLine.Command(
    name = "day4",
    mixinStandardHelpOptions = true,
    version = ["day4 1.0.0"],
    description = ["AoC 2022 day4"]
)
class App : Callable<Int> {
    @CommandLine.Parameters(index = "0", description = ["The file to process"])
    val file: File? = null

    private fun part1(lines: List<String>): Any {
        return lines
            .map { it.split(",") }
            .count {
                val (aStart, aEnd) = it[0].split("-").map(String::toInt)
                val (bStart, bEnd) = it[1].split("-").map(String::toInt)

                (bStart >= aStart && bEnd <= aEnd) ||
                        (aStart >= bStart && aEnd <= bEnd)
            }
    }

    private fun part2(lines: List<String>): Any {
        TODO()
    }

    @Throws(IOException::class)
    override fun call(): Int {
        if (file == null) {
            println("Usage: day4 <input file>")
            exitProcess(1)
        }

        val lines = readWithoutBlanks(file)

        printResults("part1", part1(lines))
//        printResults("part2", part2(lines))

        return 0
    }
}
