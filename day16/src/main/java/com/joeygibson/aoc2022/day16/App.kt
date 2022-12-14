package com.joeygibson.aoc2022.day16

import com.googlecode.lanterna.terminal.Terminal
import picocli.CommandLine
import java.io.File
import java.io.IOException
import java.util.concurrent.Callable
import kotlin.system.exitProcess

@CommandLine.Command(
    name = "day16",
    mixinStandardHelpOptions = true,
    version = ["day16 1.0.0"],
    description = ["AoC 2022 day16"]
)
class App : Callable<Int> {
    @CommandLine.Parameters(index = "0", description = ["The file to process"])
    val file: File? = null

    private lateinit var terminal: Terminal

    private val valves = mutableMapOf<String, Valve>()

    private lateinit var aa: Valve

    @Throws(IOException::class)
    override fun call(): Int {
        if (file == null) {
            println("Usage: day16 <input file>")
            exitProcess(1)
        }

        val lines = readWithoutBlanks(file)

        createGraph(lines)

        // uncomment to do visualization
        // terminal = setupTerminal()

        printResults("part1", part1())
        printResults("part2", part2())

        return 0
    }

    private fun createGraph(lines: List<String>) {
        val re = """Valve (\w{2}) has flow rate=(\d+); tunnels? leads? to valves? (.+)""".toRegex()

        lines.forEach { line ->
            re.find(line)?.let { match ->
                val (id, rate, links) = match.destructured.toList()
                valves[id] = Valve(id, rate.toInt())
            }
        }

        lines.forEach { line ->
            re.find(line)?.let { match ->
                val (id, rate, links) = match.destructured.toList()
                val valve = valves[id] ?: throw Exception("bad graph")

                valve.links.addAll(links.split(", ").map { linkId ->
                    valves[linkId] ?: throw Exception("bad links")
                })
            }
        }

        aa = valves["AA"] ?: throw Exception("bad graph")
    }

    private fun part1(): Any {
        val valvesAtMinute = mutableMapOf<Int, Valve>()

        var currentValve = aa

        var lastVisited: Valve? = null
        var moveTo: Valve? = null
        var minute = 1

        while (minute <= 30) {
            if (moveTo != null) {
                lastVisited = currentValve
                currentValve.links = currentValve.links.rotate()
                currentValve = moveTo
                moveTo = null
            } else if (currentValve.pressure <= 2) {
                // nothing to do here; go to next valve
                var localCv = currentValve.links[0]
                currentValve.links = currentValve.links.rotate()

                if (localCv.name == lastVisited?.name) {
                    localCv = currentValve.links[0]
                    currentValve.links = currentValve.links.rotate()
                }

                lastVisited = currentValve
                currentValve = localCv
            } else {
                currentValve.open = true
                valvesAtMinute[minute] = currentValve
                moveTo = currentValve.links.first()
                currentValve.links = currentValve.links.rotate()

                if (moveTo.name == lastVisited?.name) {
                    moveTo = currentValve.links.first()
                    currentValve.links = currentValve.links.rotate()
                }
            }

            println("min: $minute, current: ${currentValve.name}, next: ${moveTo?.name}, last: ${lastVisited?.name}")

            minute++
        }

//        var lastVisited = aa
//        var currentValve = aa
//        var moveTo: Valve? = aa.links.first();
//        currentValve.links = currentValve.links.rotate()
//        var minute = 1


//        if (moveTo != null) {
//            currentValve.links = currentValve.links.rotate()
//            lastVisited = currentValve
//            currentValve = moveTo
//            moveTo = null
//        } else {
//            if (currentValve.pressure > 2) {
//                currentValve.open = true
//                valvesAtMinute[minute] = currentValve
//                moveTo = currentValve.links.first()
//                if (lastVisited.name == moveTo.name) {
//                    currentValve.links = currentValve.links.rotate()
//                    moveTo = currentValve.links.first()
//                }
//                currentValve.links = currentValve.links.rotate()
//            } else {
//                var localMoveTo = currentValve.links
//                    .filterNot { it.name == lastVisited.name }
//                    .first()
//
//                if (lastVisited.name == localMoveTo.name && currentValve.links.size == 1) {
//                    localMoveTo = currentValve.links.first()
//                }
//
//                currentValve.links = currentValve.links.rotate()
//
//                lastVisited = currentValve
//                currentValve = localMoveTo
//                moveTo = null
//            }
//        }
//
        return "no result for part 1"
    }

    private fun part2(): Any {
        // part 2 goes here

        return "no result for part 2"
    }
}

data class Valve(
    val name: String,
    var pressure: Int = 0,
    var open: Boolean = false
) {
    var links: MutableList<Valve> = mutableListOf()
}

fun <T> MutableList<T>.rotate(): MutableList<T> = (this.drop(1) + this.take(1)).toMutableList()
