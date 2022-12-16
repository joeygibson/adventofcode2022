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

        var lastVisited = aa
        var currentValve = aa
        var moveTo: Valve? = aa.links.first();
        var minute = 1

        while (minute <= 30) {
            if (moveTo != null) {
                currentValve.visited.add(moveTo.name)
                lastVisited = currentValve
                currentValve = moveTo
                moveTo = null
            } else {
                if (currentValve.pressure > 2) {
                    currentValve.open = true
                    valvesAtMinute[minute] = currentValve

                    moveTo = currentValve.links
                        .filterNot { currentValve.visited.contains(it.name) }
                        .filterNot { it.name == lastVisited.name }
                        .firstOrNull()

                    if (moveTo == null) {
                        moveTo = lastVisited
                    }
                } else {
                    val localMoveTo = currentValve.links
//                        .filterNot { currentValve.visited.contains(it.name) }
                        .filterNot { it.name == lastVisited.name }
                        .first()
                    currentValve.visited.add(localMoveTo.name)
                    lastVisited = currentValve
                    currentValve = localMoveTo
                    moveTo = null
                }
            }

            println("min: $minute, current: ${currentValve.name}, next: ${moveTo?.name}, last: ${lastVisited?.name}")

            minute++
        }

//        while (minute <= 30) {
//            if (nextValve != null) {
//                currentValve = nextValve
//                currentValve.open = true
//                nextValve = null
//                valvesAtMinute[minute] = currentValve
//            } else {
//                nextValve = currentValve.links
//                    .filterNot { currentValve.visited.contains(it.name) }
//                    .filterNot { it.name == lastVisited.name }
//                    .first()
//
//                currentValve.visited.add(nextValve.name)
//            }
//
//            println("min: $minute, current: ${currentValve.name}, next: ${nextValve?.name}, last: ${lastVisited?.name}")
//            lastVisited = currentValve
//            minute++
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
    val visited = mutableListOf<String>()
}