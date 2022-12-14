package com.joeygibson.aoc2022.day3

import picocli.CommandLine
import java.io.File
import java.io.IOException
import java.util.concurrent.Callable
import kotlin.system.exitProcess

@CommandLine.Command(
    name = "day3",
    mixinStandardHelpOptions = true,
    version = ["day3 1.0.0"],
    description = ["AoC 2022 day3"]
)
class App : Callable<Int> {
    @CommandLine.Parameters(index = "0", description = ["The file to process"])
    val file: File? = null

    @Throws(IOException::class)
    override fun call(): Int {
        if (file == null) {
            println("Usage: day3 <input file>")
            exitProcess(1)
        }

        val lowerWeights = ('a'..'z').zip(1..26).toMap()
        val upperWeights = ('A'..'Z').zip(27..52).toMap()

        val lines = readWithoutBlanks(file)

        printResults("part1", part1(lines, upperWeights, lowerWeights))
        printResults("part2", part2(lines, upperWeights, lowerWeights))

        return 0
    }

    private fun part1(
        lines: List<String>,
        upperWeights: Map<Char, Int>,
        lowerWeights: Map<Char, Int>
    ) = lines
        .map {
            it.chunked(it.length / 2)
        }
        .flatMap {
            it[0].toList().intersect(it[1].toList().toSet())
        }.sumOf {
            getPriority(it, upperWeights, lowerWeights)
        }

    private fun part2(
        lines: List<String>,
        upperWeights: Map<Char, Int>,
        lowerWeights: Map<Char, Int>
    ) = lines
        .chunked(3)
        .flatMap {
            it[0].toList().intersect(it[1].toList().toSet()).intersect(it[2].toList().toSet())
        }.sumOf {
            getPriority(it, upperWeights, lowerWeights)
        }

    private fun getPriority(
        item: Char,
        upperWeights: Map<Char, Int>,
        lowerWeights: Map<Char, Int>
    ) = if (item.isUpperCase()) {
        upperWeights.getOrDefault(item, 0)
    } else {
        lowerWeights.getOrDefault(item, 0)
    }
}
