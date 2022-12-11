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

        printResults("part1", part1(buildMonkeys(lines)))
        printResults("part2", part2(buildMonkeys(lines)))

        return 0
    }

    private fun buildMonkeys(fileLines: List<List<String>>): List<Monkey> {
        return fileLines
            .map { lines ->
                val id = lines[0].split(" ")[1].replace(":", "").toInt()
                val items = lines[1].split(": ")[1]
                    .split(""",\s*""".toRegex())
                    .map { Item(it.toLong()) }
                    .toMutableList()
                val opChunks = lines[2].split("old ")[1].split(" ")
                val op = if (opChunks[1] != "old") {
                    if (opChunks[0] == "+") {
                        { old: Long -> old + opChunks[1].toInt() }
                    } else {
                        { old: Long -> old * opChunks[1].toInt() }
                    }
                } else {
                    if (opChunks[0] == "+") {
                        { old: Long -> old + old }
                    } else {
                        { old: Long -> old * old }
                    }
                }
                val test = lines[3].split("divisible by ")[1].toLong()
                val trueMonkey = lines[4].split("monkey ")[1].toInt()
                val falseMonkey = lines[5].split("monkey ")[1].toInt()

                Monkey(id, items, test, trueMonkey, falseMonkey, op)
            }
    }

    private fun part1(monkeys: List<Monkey>): Any {
        (0 until 20).forEach { turn ->
            monkeys.forEach { monkey ->
                monkey.takeTurn(monkeys, 3)
            }
        }

        return monkeys
            .sortedByDescending { it.inspectionCount }
            .take(2)
            .map { it.inspectionCount }
            .reduce { a, b -> a * b }
    }

    private fun part2(monkeys: List<Monkey>): Any {
        val worryReducer = monkeys
            .map { it.test }
            .fold(1L) { a, b -> a * b }

        (0 until 10_000).forEach { turn ->
            monkeys.forEach { monkey ->
                monkey.takeTurn(monkeys, worryReducer)
            }
        }

        return monkeys
            .sortedByDescending { it.inspectionCount }
            .take(2)
            .map { it.inspectionCount }
            .reduce { a, b -> a * b }
    }
}

data class Item(var worry: Long) {
    fun inspect(op: (Long) -> Long) {
        worry = op(worry)
        assert(worry >= 0)
    }

    fun expressBoredom(worryReducer: Long) {
        if (worryReducer == 3L) {
            worry /= 3
        } else {
            worry %= worryReducer
        }
    }

    fun test(testVal: Long): Boolean = worry % testVal == 0L
}

data class Monkey(
    val number: Int,
    val items: MutableList<Item>,
    val test: Long,
    val trueMonkey: Int,
    val falseMonkey: Int,
    val operation: (Long) -> Long
) {
    var inspectionCount = 0L

    fun takeTurn(monkeys: List<Monkey>, worryReducer: Long) {
        items.forEach { item ->
            item.inspect(operation)
            item.expressBoredom(worryReducer)

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