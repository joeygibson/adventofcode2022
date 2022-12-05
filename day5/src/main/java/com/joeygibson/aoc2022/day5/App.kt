package com.joeygibson.aoc2022.day5

import picocli.CommandLine
import java.io.File
import java.io.IOException
import java.util.concurrent.Callable
import kotlin.system.exitProcess

@CommandLine.Command(
    name = "day5",
    mixinStandardHelpOptions = true,
    version = ["day5 1.0.0"],
    description = ["AoC 2022 day5"]
)
class App : Callable<Int> {
    @CommandLine.Parameters(index = "0", description = ["The file to process"])
    val file: File? = null

    @Throws(IOException::class)
    override fun call(): Int {
        if (file == null) {
            println("Usage: day5 <input file>")
            exitProcess(1)
        }

        val lines = readInput(file)

        printResults("part1", part1(lines))

        return 0
    }

    fun part1(lines: List<String>): String {
        val moveRegex = """move (\d+) from (\d+) to (\d+)""".toRegex()
        val stackLines = lines.takeWhile { it.isNotBlank() }

        val moves = lines.drop(stackLines.size + 1)

        val stacks: MutableMap<Int, MutableList<String>> = buildStacks(stackLines)

        moves.forEach { move ->
            moveRegex.find(move)?.let { match ->
                val (num, src, dest) = match.destructured.toList().map { it.toInt() }

                repeat(num) {
                    val crate: String = stacks[src]?.last() ?: "empty"
                    stacks[dest]?.add(crate)
                    stacks[src]?.removeLast()
                }
            }
        }

        return stacks
            .values
            .map { it.last() }
            .joinToString("")
    }

    private fun buildStacks(lines: List<String>): MutableMap<Int, MutableList<String>> {
        val keys = lines.last()
            .trim()
            .split("""\s+""".toRegex())
            .map { it.toInt() }

        val stacks = keys.associateWith { mutableListOf<String>() }.toMutableMap()

        lines.dropLast(1).reversed()
            .forEach {
                val crates = it.chunked(4)

                keys.forEach { key ->
                    crates[key - 1].let { crate ->
                        if (crate.isNotBlank()) {
                            stacks[key]?.add(crate.trim().replace("""[\[\]]""".toRegex(), ""))
                        }
                    }
                }
            }

        return stacks
    }
}
