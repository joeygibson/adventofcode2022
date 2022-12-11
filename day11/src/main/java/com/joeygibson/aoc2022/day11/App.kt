package com.joeygibson.aoc2022.day11

import com.googlecode.lanterna.terminal.Terminal
import picocli.CommandLine
import java.io.File
import java.io.IOException
import java.util.concurrent.Callable
import kotlin.system.exitProcess

@CommandLine.Command(
    name = "day11",
    mixinStandardHelpOptions = true,
    version = ["day11 1.0.0"],
    description = ["AoC 2022 day11"]
)
class App : Callable<Int> {
    @CommandLine.Parameters(index = "0", description = ["The file to process"])
    val file: File? = null

    private lateinit var terminal: Terminal

    @Throws(IOException::class)
    override fun call(): Int {
        if (file == null) {
            println("Usage: day11 <input file>")
            exitProcess(1)
        }

        val lines = file.readText().split("\n\n")
            .map {
                it.split("\n")
            }

        // uncomment to do visualization
        // terminal = setupTerminal()

        val monkeys = buildMonkeys(lines)

        printResults("part1", part1(monkeys))
//        printResults("part2", part2(monkeys))

        return 0
    }

    private fun buildMonkeys(fileLines: List<List<String>>): List<Monkey> {
        return fileLines
            .map { lines ->
                val id = lines[0].split(" ")[1].replace(":", "").toInt()
                val items = lines[1].split(": ")[1]
                    .split(""",\s*""".toRegex())
                    .map { Item(it.toInt()) }
                    .toMutableList()
                val opChunks = lines[2].split("old ")[1].split(" ")
                val op = if (opChunks[1] != "old") {
                    if (opChunks[0] == "+") {
                        { old: Int -> old + opChunks[1].toInt() }
                    } else {
                        { old: Int -> old * opChunks[1].toInt() }
                    }
                } else {
                    if (opChunks[0] == "+") {
                        { old: Int -> old + old }
                    } else {
                        { old: Int -> old * old }
                    }
                }
                val test = lines[3].split("divisible by ")[1].toInt()
                val trueMonkey = lines[4].split("monkey ")[1].toInt()
                val falseMonkey = lines[5].split("monkey ")[1].toInt()

                Monkey(id, items, test, trueMonkey, falseMonkey, op)
            }
    }

    private fun part1(monkeys: List<Monkey>): Any {
        (0 until 20).forEach { turn ->
            monkeys.forEach { monkey ->
                monkey.takeTurn(monkeys)
            }
        }

        return monkeys
            .sortedByDescending { it.inspectionCount }
            .take(2)
            .map { it.inspectionCount }
            .reduce { a, b -> a * b }
    }

    private fun part2(lines: List<Monkey>): Any {
        // part 2 goes here

        return "no result for part 2"
    }
}

data class Item(var worry: Int) {
    fun inspect(op: (Int) -> Int) {
        worry = op(worry)
    }

    fun expressBoredom() {
        worry /= 3
    }

    fun test(testVal: Int): Boolean = worry % testVal == 0
}

data class Monkey(
    val number: Int,
    val items: MutableList<Item>,
    val test: Int,
    val trueMonkey: Int,
    val falseMonkey: Int,
    val operation: (Int) -> Int
) {
    var inspectionCount = 0

    fun takeTurn(monkeys: List<Monkey>) {
        items.forEach { item ->
            item.inspect(operation)
            item.expressBoredom()

            if (item.test(test)) {
                monkeys[trueMonkey].items.add(item)
            } else {
                monkeys[falseMonkey].items.add(item)
            }

            inspectionCount++
        }

        items.clear()
    }
}