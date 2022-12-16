package com.joeygibson.aoc2022.day15

import picocli.CommandLine
import java.io.File
import java.io.IOException
import java.lang.Math.abs
import java.util.concurrent.Callable
import kotlin.math.max
import kotlin.math.min
import kotlin.system.exitProcess

@CommandLine.Command(
    name = "day15",
    mixinStandardHelpOptions = true,
    version = ["day15 1.0.0"],
    description = ["AoC 2022 day15"]
)
class App : Callable<Int> {
    @CommandLine.Parameters(index = "0", description = ["The file to process"])
    val file: File? = null

    private val sensorAndBeaconRegex =
        """Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)""".toRegex()
    private val theMap = mutableMapOf<Int, MutableMap<Int, Thing>>().withDefault { mutableMapOf() }
    private val beacons = mutableSetOf<Thing>()
    private val sensors = mutableSetOf<Thing>()
    private val sensorsAndTheirClosestBeaconDistance = mutableSetOf<Pair<Thing, Int>>()

    @Throws(IOException::class)
    override fun call(): Int {
        if (file == null) {
            println("Usage: day15 <input file>")
            exitProcess(1)
        }

        val lines = readWithoutBlanks(file)
        createMap(lines)

//        drawMap()

        printResults("part1", part1(file.name))
        printResults("part2", part2(file.name))

        return 0
    }

    private fun createMap(lines: List<String>) {
        lines.forEach { line ->
            sensorAndBeaconRegex.find(line)?.let { match ->
                val (sCol, sRow, bCol, bRow) = match.destructured.toList().map(String::toInt)

                val sensor = Thing(sCol, sRow)
                var rowMap = theMap.getOrPut(sRow) { mutableMapOf() }
                rowMap[sCol] = sensor
                sensors.add(sensor)

                val beacon = Thing(bCol, bRow, true)
                rowMap = theMap.getOrPut(bRow) { mutableMapOf() }
                rowMap[bCol] = beacon
                beacons.add(beacon)

                val dist = distanceBetweenThings(sensor, beacon)
                sensorsAndTheirClosestBeaconDistance.add(Pair(sensor, dist))
            }
        }
    }

    private fun part1(fileName: String): Any {
        val rowOfInterest = if (fileName == "data1.txt") {
            2000000
        } else {
            10
        }

        val minSensorCol = sensors.minOf { it.col }
        val maxSensorCol = sensors.maxOf { it.col }
        val minBeaconCol = beacons.minOf { it.col }
        val maxBeaconCol = beacons.maxOf { it.col }

        val minCol = min(minSensorCol, minBeaconCol) - 5_000_000
        val maxCol = max(maxSensorCol, maxBeaconCol) + 5_000_000

        val excludedCols = mutableSetOf<Int>()

        sensorsAndTheirClosestBeaconDistance.forEach { sensorAndDistance ->
            val sensor = sensorAndDistance.first
            val dist = sensorAndDistance.second

            (minCol..maxCol).forEach { col ->
                if (distanceFromThing(sensor, col, rowOfInterest) <= dist) {
                    excludedCols.add(col)
                }
            }
        }

        beacons.filter {
            it.row == rowOfInterest
        }.map {
            it.col
        }.forEach { col ->
            excludedCols.remove(col)
        }

        sensors.filter {
            it.row == rowOfInterest
        }.map {
            it.col
        }.forEach { col ->
            excludedCols.remove(col)
        }

        return excludedCols.size
    }

    private fun part2(fileName: String): Any {
        val minCoord = 0
        val maxCoord = if (fileName == "data0.txt") {
            20
        } else {
            4_000_000
        }

        val excludedCoords = mutableSetOf<Pair<Int, Int>>()

        println("checking sensor ranges")
        sensorsAndTheirClosestBeaconDistance.forEach { sensorAndDistance ->
            val sensor = sensorAndDistance.first
            val dist = sensorAndDistance.second

            (minCoord..maxCoord).forEach { row ->
                (minCoord..maxCoord).forEach { col ->
                    if (distanceFromThing(sensor, col, row) <= dist) {
                        excludedCoords.add(Pair(col, row))
                    }
                }
            }
        }

        println("comparing results")
        for (row in (minCoord..maxCoord)) {
            for (col in (minCoord..maxCoord)) {
                if (!excludedCoords.contains(Pair(col, row))) {
                    return Pair(col, row)
                }
            }
        }

        return "not found"
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

                print(value)
            }

            println()
        }
    }

    private fun distanceFromThing(from: Thing, toCol: Int, toRow: Int): Int =
        distanceBetweenPoints(from.col, from.row, toCol, toRow)

    private fun distanceBetweenThings(from: Thing, to: Thing): Int =
        distanceBetweenPoints(from.col, from.row, to.col, to.row)

    private fun distanceBetweenPoints(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int): Int =
        abs(fromRow - toRow) + abs(fromCol - toCol)
}

data class Thing(val col: Int, val row: Int, val beacon: Boolean = false) {
    val coordinates: Pair<Int, Int>
        get() = Pair(col, row)

    override fun toString() =
        if (beacon) {
            "B"
        } else {
            "S"
        }
}
