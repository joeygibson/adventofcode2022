package com.joeygibson.aoc2022.day10

import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.terminal.Terminal
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

    private lateinit var terminal: Terminal

    @Throws(IOException::class)
    override fun call(): Int {
        if (file == null) {
            println("Usage: day10 <input file>")
            exitProcess(1)
        }

        val lines = readWithoutBlanks(file)

        terminal = setupTerminal()

        printResults("part1", part1(lines))
        part2(lines)

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

        val (rows, cols) = getTerminalDimensions(terminal)
        println("rows: $rows, cols: $cols")
        var curX = (cols - 40) / 2
        var curY = (rows - 6) / 2

        terminal.clearScreen()

        (0..5).forEach { row ->
            curY += 1
            curX = (cols - 40) / 2

            (0..39).forEach { col ->
                val index = row * 40 + col
                val registerValue = cycleValues[index]
                val range = (col - 1..col + 1)

                val pixel = if (range.contains(registerValue)) {
                    '█'
                } else {
                    '.'
                }

                curX += 1

                terminal.setCursorPosition(curX, curY)
                terminal.setBackgroundColor(TextColor.Factory.fromString("blue"))
                terminal.putCharacter('.')
                Thread.sleep(10)

                terminal.setCursorPosition(curX, curY)
                terminal.setBackgroundColor(TextColor.Factory.fromString("black"))
                terminal.putCharacter(pixel)
                Thread.sleep(10)

                terminal.pollInput()?.let {
                    resetTerminal(terminal)
                    exitProcess(0)
                }
            }
        }

        terminal.readInput()
        terminal.setCursorVisible(true)

        return ""
    }
}
