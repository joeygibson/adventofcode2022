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

    private fun solve(lines: List<String>, solver: (Int, Int, Int, Int) -> Boolean): Int {
        return lines
            .map { it.split(",") }
            .count {
                val (aStart, aEnd) = it[0].split("-").map(String::toInt)
                val (bStart, bEnd) = it[1].split("-").map(String::toInt)

                solver(aStart, aEnd, bStart, bEnd)
            }
    }

    private fun solvePart1(aStart: Int, aEnd: Int, bStart: Int, bEnd: Int): Boolean =
        (bStart >= aStart && bEnd <= aEnd) ||
                (aStart >= bStart && aEnd <= bEnd)

    private fun solvePart2(aStart: Int, aEnd: Int, bStart: Int, bEnd: Int): Boolean {
        val aRange = (aStart..aEnd)
        val bRange = (bStart..bEnd)

        return aRange.contains(bStart) || aRange.contains(bEnd) ||
                bRange.contains(aStart) || bRange.contains(aEnd)
    }

    @Throws(IOException::class)
    override fun call(): Int {
        if (file == null) {
            println("Usage: day4 <input file>")
            exitProcess(1)
        }

        val lines = readWithoutBlanks(file)

        printResults("part1", solve(lines, ::solvePart1))
        printResults("part2", solve(lines, ::solvePart2))

        return 0
    }
}
