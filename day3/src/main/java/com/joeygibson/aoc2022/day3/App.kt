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

        val lines = readInput(file)

        val part1 = lines
            .filter { it.isNotBlank() }
            .map {
                it.chunked(it.length / 2)
            }
            .flatMap {
                val (side0, side1) = it
                side0.filter { item -> side1.contains(item) }.toList().distinct()
            }
            .map {
                if (it.isUpperCase()) {
                    upperWeights.getOrDefault(it, 0)
                } else {
                    lowerWeights.getOrDefault(it, 0)
                }
            }
            .sum()

        println(part1)

        return 0
    }
}
