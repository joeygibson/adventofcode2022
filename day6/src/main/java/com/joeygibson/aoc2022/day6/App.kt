package com.joeygibson.aoc2022.day6

import picocli.CommandLine
import java.io.File
import java.io.IOException
import java.util.concurrent.Callable
import kotlin.system.exitProcess

@CommandLine.Command(
    name = "day6",
    mixinStandardHelpOptions = true,
    version = ["day6 1.0.0"],
    description = ["AoC 2022 day6"]
)
class App : Callable<Int> {
    @CommandLine.Parameters(index = "0", description = ["The file to process"])
    val file: File? = null

    @Throws(IOException::class)
    override fun call(): Int {
        if (file == null) {
            println("Usage: day6 <input file>")
            exitProcess(1)
        }

        val line = readInput(file).take(1)[0]

        printResults("part1", part1(line))
        printResults("part2", part2(line))

        return 0
    }

    private fun part1(line: String): Any {
        var count = 0
        var marker = mutableListOf<String>()

        for (ch in line.toCharArray()) {
            val chStr = ch.toString()

            count++

            if (!marker.contains(chStr)) {
                marker.add(chStr)

                if (marker.size == 4) {
                    break
                }
            } else {
                val previous = marker.indexOf(chStr)
                marker = marker
                    .withIndex()
                    .dropWhile { (index, _) -> index <= previous }
                    .map { (_, item) -> item }
                    .toMutableList()

                marker.add(chStr)
            }
        }


        println("marker: [${marker.joinToString()}]")

        return count
    }

    private fun part2(line: String): Any {
        // part 2 goes here

        return "no result for part 2"
    }
}
