package com.joeygibson.aoc2022.day14

import com.googlecode.lanterna.terminal.Terminal
import picocli.CommandLine
import java.io.File
import java.io.IOException
import java.util.concurrent.Callable
import kotlin.system.exitProcess

private const val ROCK = "#"
private const val AIR = "."
private const val SAND = "o"

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

    private val theMap = mutableMapOf<Int, MutableMap<Int, String>>().withDefault { mutableMapOf() }
    private var theFloor: Int = 0

    @Throws(IOException::class)
    override fun call(): Int {
        if (file == null) {
            println("Usage: day14 <input file>")
            exitProcess(1)
        }

        createMap(readWithoutBlanks(file))

        printResults("part1", part1())

        createMap(readWithoutBlanks(file))

        printResults("part2", part2())

        return 0
    }

    private fun createMap(lines: List<String>) {
        theMap.clear()
        
        lines.forEach { line ->
            line.split(" -> ")
                .map { it.split(",").map(String::toInt) }
                .windowed(2) { (first, second) ->
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
                            rowMap[startCol] = ROCK
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
                            colMap[col] = ROCK
                        }
                    }
                }
        }

        theFloor = theMap.keys.max() + 2

        drawMap()
    }

    private fun part1(): Any {
        var grainsOfSand = 0
        var fallingIntoTheVoid = false

        while (!fallingIntoTheVoid) {
            var col = 500
            var row = 1
            var cameToRest = false

            outerLoop@ while (!cameToRest) {
                if (isOpen(row, col)) {
                    row++
                    continue
                }

                // decide left or right
                val left = col - 1
                val right = col + 1

                if (isOpen(row, left)) {
                    if (isTheVoid(left)) {
                        fallingIntoTheVoid = true
                        break
                    }

                    col = left
                    continue
                } else if (isOpen(row, right)) {
                    if (isTheVoid(right)) {
                        fallingIntoTheVoid = true
                        break
                    }

                    col = right
                    continue
                } else {
                    val colMap = theMap.getOrPut(row - 1) { mutableMapOf() }
                    colMap[col] = SAND
                    cameToRest = true
                    grainsOfSand++
                    break@outerLoop
                }
            }
        }

        drawMap()

        return grainsOfSand
    }

    private fun part2(): Any {
        var grainsOfSand = 0
        var stoppedUp = false

        while (!stoppedUp) {
            var col = 500
            var row = 1
            var cameToRest = false

            while (!cameToRest) {
                val hitTheFloor = isTheFloor(row)

                if (isOpen(row, col) && !hitTheFloor) {
                    row++
                    continue
                }

                if (hitTheFloor) {
                    val colMap = theMap.getOrPut(row - 1) { mutableMapOf() }
                    colMap[col] = SAND
                    cameToRest = true
                    grainsOfSand++
                    break
                }

                // decide left or right
                val left = col - 1
                val right = col + 1

                if (isOpen(row, left)) {
                    col = left
                    continue
                } else if (isOpen(row, right)) {
                    col = right
                    continue
                } else {
                    if (col == 500 && row == 1) {
                        stoppedUp = true
                    }

                    val colMap = theMap.getOrPut(row - 1) { mutableMapOf() }
                    colMap[col] = SAND
                    cameToRest = true
                    grainsOfSand++
                    break
                }
            }
        }

        drawMap()

        return grainsOfSand
    }

    private fun isTheFloor(row: Int): Boolean {
        return row == theFloor
    }

    private fun isTheVoid(col: Int): Boolean {
        val maxCols = theMap.values
            .flatMap { it.keys }
            .max()
        val minCols = theMap.values
            .flatMap { it.keys }
            .min()

        return col < minCols || col > maxCols
    }

    private fun isOpen(
        row: Int, col: Int
    ): Boolean {
        val space = theMap[row]?.get(col)

        return space == null
    }

    private fun drawMap() {
        println("--------------------------------------------")
        val maxRows = theMap.keys.max()
        val minRows = theMap.keys.min()
        val maxCols = theMap.values
            .flatMap { it.keys }
            .max()
        val minCols = theMap.values
            .flatMap { it.keys }
            .min()

        (0..maxRows).forEach { row ->
            (minCols..maxCols).forEach { col ->
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
}
