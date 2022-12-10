package com.joeygibson.aoc2022.day10

import picocli.CommandLine
import java.io.File
import java.io.IOException
import java.util.concurrent.Callable
import kotlin.system.exitProcess

@CommandLine.Command(
    name = "day10",
    mixinStandardHelpOptions = true,
    version = ["day10 1.0.0"],
    description = ["AoC 2022 day10"]
)
class App : Callable<Int> {
    @CommandLine.Parameters(index = "0", description = ["The file to process"])
    val file: File? = null

    @Throws(IOException::class)
    override fun call(): Int {
        if (file == null) {
            println("Usage: day10 <input file>")
            exitProcess(1)
        }

        val lines = readWithoutBlanks(file)

        printResults("part1", part1(lines))
        printResults("part2", part2(lines))

        return 0
    }

    private fun part1(lines: List<String>): Int {
        val cycleValues = mutableMapOf<Int, Int>()
        val cyclesToCheck = listOf(20, 60, 100, 140, 180, 220)
        var register = 1
        var cycle = 0

        lines.forEach { line ->
            cycle++

            if (cyclesToCheck.contains(cycle)) {
                cycleValues[cycle] = register
            }

            when {
                line.startsWith("addx") -> {
                    val value = line.split(" ")[1].toInt()

                    repeat(2) { c ->
                        if (cyclesToCheck.contains(cycle)) {
                            cycleValues[cycle] = register
                        }

                        if (c == 1) {
                            register += value
                        } else {
                            cycle++
                        }
                    }
                }
            }
        }

        cycle++

        println("cycle: $cycle, register: $register")
        println(cycleValues)

        return cycleValues.map { (key, value) ->
            key * value
        }.sum()
    }

    private fun part2(lines: List<String>): Any {
        val cycleValues = mutableListOf<Int>()
        var register = 1
        var cycle = 0

        lines.forEach { line ->
            cycle++

            cycleValues.add(register)

            when {
                line.startsWith("addx") -> {
                    val value = line.split(" ")[1].toInt()

                    repeat(2) { c ->
                        if (c == 1) {
                            register += value
                        } else {
                            cycle++
                            cycleValues.add(register)
                        }
                    }
                }
            }
        }

        cycle++
        cycleValues.add(register)

        (0..5).forEach { row ->
            (0..39).forEach { col ->
                val index = row * 40 + col
                val registerValue = cycleValues[index]
                val range = (col - 1 .. col + 1)

                val pixel = if (range.contains(registerValue)) {
                    "#"
                } else {
                    "."
                }

                print(pixel)
            }

            println()
        }

        return ""
    }
}
