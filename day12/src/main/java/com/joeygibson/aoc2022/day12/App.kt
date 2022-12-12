package com.joeygibson.aoc2022.day12

import com.googlecode.lanterna.terminal.Terminal
import picocli.CommandLine
import java.io.File
import java.io.IOException
import java.util.concurrent.Callable
import kotlin.system.exitProcess


@CommandLine.Command(
    name = "day12",
    mixinStandardHelpOptions = true,
    version = ["day12 1.0.0"],
    description = ["AoC 2022 day12"]
)
class App : Callable<Int> {
    @CommandLine.Parameters(index = "0", description = ["The file to process"])
    val file: File? = null

    private lateinit var terminal: Terminal

    @Throws(IOException::class)
    override fun call(): Int {
        if (file == null) {
            println("Usage: day12 <input file>")
            exitProcess(1)
        }

        val alphabet = ('a'..'z').zip(0..25).toMap()

        val theMap = readWithoutBlanks(file)
            .withIndex()
            .map { (row, line) ->
                line.chunked(1)
                    .withIndex()
                    .map { (col, ch) ->
                        val c = ch[0]
                        val height = when (c) {
                            'S' -> {
                                0
                            }

                            'E' -> {
                                25
                            }

                            else -> {
                                alphabet[c] ?: throw Exception("bad map data: $c")
                            }
                        }

                        MapPoint(row, col, c, height)
                    }
            }

        theMap.forEach { row ->
            row.forEach { point ->
                val neighbors = getNeighbors(theMap, point)
                point.neighbors.addAll(neighbors)
            }
        }

        // uncomment to do visualization
        // terminal = setupTerminal()

        printResults("part1", part1(theMap))
        printResults("part2", part2(theMap))

        return 0
    }

    private fun part1(theMap: List<List<MapPoint>>): Int {
        val start = getStart(theMap)
        var visited = mutableSetOf<MapPoint>()
        val end = getEnd(theMap)
        var moves = 0

        println("start: $start, end: $end")

        return bfs(theMap, start, end)
    }

    private fun bfs(theMap: List<List<MapPoint>>, start: MapPoint, end: MapPoint): Int {
        val queue = mutableListOf(Pair(start, 0))
        val visited = mutableSetOf<MapPoint>()
        var best = 100000

        while (queue.isNotEmpty()) {
            val (cur, score) = queue[0]; queue.removeAt(0)

            if (cur == end) {
                if (score < best) {
                    best = score
                }

                continue
            }

            if (visited.contains(cur)) {
                continue
            }

            visited.add(cur)

            for (neighbor in cur.neighbors) {
                if (cur.height + 1 >= neighbor.height) {
                    queue.add(Pair(neighbor, score + 1))
                }
            }
        }

        return best
    }

    private fun part2(lines: List<List<MapPoint>>): Any {
        // part 2 goes here

        return "no result for part 2"
    }

    private fun getStart(theMap: List<List<MapPoint>>): MapPoint {
        for (row in theMap) {
            for (col in row) {
                if (col.isStart()) {
                    return col
                }
            }
        }

        throw Exception("start not found")
    }

    private fun getEnd(theMap: List<List<MapPoint>>): MapPoint {
        for (row in theMap) {
            for (col in row) {
                if (col.isEnd()) {
                    return col
                }
            }
        }

        throw Exception("end not found")
    }

    private fun getNeighbors(theMap: List<List<MapPoint>>, cur: MapPoint): List<MapPoint> {
        val rows = theMap.size
        val cols = theMap[0].size

        val neighbors = mutableListOf<MapPoint>()

        val up = cur.row - 1
        val down = cur.row + 1
        val left = cur.col - 1
        val right = cur.col + 1

        if (up >= 0) {
            neighbors.add(theMap[up][cur.col])
        }

        if (down < rows) {
            neighbors.add(theMap[down][cur.col])
        }

        if (left >= 0) {
            neighbors.add(theMap[cur.row][left])
        }

        if (right < cols) {
            neighbors.add(theMap[cur.row][right])
        }

        return neighbors
            .sortedByDescending { it.height }
            .toList()
    }
}

data class MapPoint(val row: Int, val col: Int, val mapValue: Char, val height: Int) {
    val neighbors = mutableListOf<MapPoint>()

    fun isStart() = mapValue == 'S'
    fun isEnd() = mapValue == 'E'
}
