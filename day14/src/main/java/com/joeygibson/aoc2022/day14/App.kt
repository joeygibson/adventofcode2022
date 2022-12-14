package com.joeygibson.aoc2022.day14

import com.googlecode.lanterna.terminal.Terminal
import picocli.CommandLine
import java.io.File
import java.io.IOException
import java.util.concurrent.Callable
import kotlin.system.exitProcess

@CommandLine.Command(
    name = "day14",
    mixinStandardHelpOptions = true,
    version = ["day14 1.0.0"],
    description = ["AoC 2022 day14"]
)
class App : Callable<Int> {
    @CommandLine.Parameters(index = "0", description = ["The file to process"])
    val file: File? = null

    private lateinit var terminal: Terminal

    @Throws(IOException::class)
    override fun call(): Int {
        if (file == null) {
            println("Usage: day14 <input file>")
            exitProcess(1)
        }

        val lines = readWithoutBlanks(file)

        // uncomment to do visualization
        // terminal = setupTerminal()

        printResults("part1", part1(lines))
        printResults("part2", part2(lines))

        return 0
    }

    private fun part1(lines: List<String>): Any {
        val theMap = mutableMapOf<Int, MutableMap<Int, String>>().withDefault { mutableMapOf() }

        lines.forEach { line ->
            line.split(" -> ")
                .map { it.split(",").map(String::toInt) }
                .windowed(2) { (first, second) ->
//                    println("first: $first, second: $second")
                    var startRow = first[1]
                    var endRow = second[1]
                    var startCol = first[0]
                    var endCol = second[0]

                    val colDiff = startCol - endCol
                    val rowDiff = startRow - endRow

                    if (colDiff == 0) {
                        if (endRow < startRow) {
                            val tmp = startRow
                            startRow = endRow
                            endRow = tmp
                        }

                        (startRow..endRow).forEach { row ->
                            val rowMap = theMap.getOrPut(row) { mutableMapOf() }
                            rowMap[startCol] = "#"
                        }
                    }

                    if (rowDiff == 0) {
                        if (endCol < startCol) {
                            val tmp = startCol
                            startCol = endCol
                            endCol = tmp
                        }

                        (startCol..endCol).forEach { col ->
                            val colMap = theMap.getOrPut(startRow) { mutableMapOf() }
                            colMap[col] = "#"
                        }
                    }
                }
        }

        drawMap(theMap)
        return "no result for part 1"
    }

    private fun drawMap(theMap: MutableMap<Int, MutableMap<Int, String>>) {
        val maxRows = theMap.keys.max()
        val minRows = theMap.keys.min()
        val maxCols = theMap.values
            .flatMap { it.keys }
            .max()
        val minCols = theMap.values
            .flatMap { it.keys }
            .min()

//        println("minCols: $minCols, maxCols: $maxCols, minRows: $minRows, maxRows: $maxRows")

        (0 .. maxRows).forEach { row ->
            (minCols .. maxCols).forEach { col ->
                val value = theMap[row]?.get(col) ?: "."

                if (row == 0 && col == 500) {
                    print("+")
                } else {
                    print(value)
                }
            }

            println()
        }
    }

    private fun part2(lines: List<String>): Any {
        // part 2 goes here

        return "no result for part 2"
    }
}
