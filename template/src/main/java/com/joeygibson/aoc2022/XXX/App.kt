package com.joeygibson.aoc2022.XXX

import picocli.CommandLine
import java.io.File
import java.io.IOException
import java.util.concurrent.Callable
import kotlin.system.exitProcess

@CommandLine.Command(
    name = "XXX",
    mixinStandardHelpOptions = true,
    version = ["XXX 1.0.0"],
    description = ["AoC 2022 XXX"]
)
class App : Callable<Int> {
    @CommandLine.Parameters(index = "0", description = ["The file to process"])
    val file: File? = null

    @Throws(IOException::class)
    override fun call(): Int {
        if (file == null) {
            println("Usage: XXX <input file>")
            exitProcess(1)
        }

        val lines = readInput(file)

        printResults("part1", part1(lines))
        printResults("part2", part2(lines))

        return 0
    }

    private fun part1(lines: List<String>): Any {
        // part 1 goes here

        return "no result for part 1"
    }

    private fun part2(lines: List<String>): Any {
        // part 2 goes here

        return "no result for part 2"
    }
}
