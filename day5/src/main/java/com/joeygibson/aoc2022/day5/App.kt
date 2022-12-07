package com.joeygibson.aoc2022.day5

import com.googlecode.lanterna.Symbols
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.Terminal
import picocli.CommandLine
import java.io.File
import java.io.IOException
import java.lang.Integer.max
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

    @CommandLine.Option(names = ["-v", "--visualize"], description = ["Show visualization"])
    var visualize: Boolean = false

    private val moveRegex = """move (\d+) from (\d+) to (\d+)""".toRegex()

    private lateinit var terminal: Terminal

    private var maxHeight: Int = 0

    @Throws(IOException::class)
    override fun call(): Int {
        if (file == null) {
            println("Usage: day5 <input file>")
            exitProcess(1)
        }

        if (visualize) {
            terminal = setupTerminal()
        }

        val lines = readInput(file)

        listOf(::part1, ::part2).forEach { func ->
            val result = moveCrates(lines, func)

            if (!visualize) {
                println("${func.name}: $result")
            }
        }

        return 0
    }

    private fun setupTerminal(): Terminal {
        val defaultTerminalFactory = DefaultTerminalFactory()
        val terminal = defaultTerminalFactory.createTerminal()
        val foreground = TextColor.Factory.fromString("white")
        val background = TextColor.Factory.fromString("black")

        terminal.setCursorVisible(false)
        terminal.setForegroundColor(foreground)
        terminal.setBackgroundColor(background)

        return terminal
    }

    private fun moveCrates(
        lines: List<String>,
        mover: (MutableMap<Int, MutableList<String>>, Int, Int, Int) -> Unit
    ): String {
        val (stackLines, moves) = lines.splitOn(String::isNotBlank)
        val stacks: MutableMap<Int, MutableList<String>> = buildStacks(stackLines)

        if (visualize) {
            terminal.clearScreen()
            visualize(stacks)
        }

        moves.forEach { move ->
            moveRegex.find(move)?.let { match ->
                val (num, src, dest) = match.destructured.toList().map(String::toInt)

                mover(stacks, num, src, dest)
            }
        }

        if (visualize) {
            val columns = terminal.terminalSize.columns - 1
            val rows = terminal.terminalSize.rows - 1
            val msg = "Press a key to continue..."
            val startCol = (columns - msg.length) / 2

            terminal.setCursorPosition(startCol, rows)
            terminal.putString(msg)

            // wait for a keypress
            terminal.readInput()
        }

        return stacks
            .values.joinToString("") { it.last() }
    }

    private fun drawFrame() {
        val columns = terminal.terminalSize.columns - 1
        val rows = terminal.terminalSize.rows - 1

        // top line
        terminal.setCursorPosition(0, 0)
        terminal.putCharacter(Symbols.SINGLE_LINE_TOP_LEFT_CORNER)

        for (i in 0 until columns) {
            terminal.putCharacter(Symbols.SINGLE_LINE_HORIZONTAL)
        }

        terminal.setCursorPosition(columns, 0)
        terminal.putCharacter(Symbols.SINGLE_LINE_TOP_RIGHT_CORNER)

        // bottom line
        terminal.setCursorPosition(0, rows)
        terminal.putCharacter(Symbols.SINGLE_LINE_BOTTOM_LEFT_CORNER)

        for (i in 0 until columns) {
            terminal.putCharacter(Symbols.SINGLE_LINE_HORIZONTAL)
        }

        terminal.setCursorPosition(columns, rows)
        terminal.putCharacter(Symbols.SINGLE_LINE_BOTTOM_RIGHT_CORNER)

        // side lines
        (1 until rows)
            .forEach {
                terminal.setCursorPosition(0, it)
                terminal.putCharacter(Symbols.SINGLE_LINE_VERTICAL)

                terminal.setCursorPosition(columns, it)
                terminal.putCharacter(Symbols.SINGLE_LINE_VERTICAL)
            }
    }

    private fun visualize(stacks: MutableMap<Int, MutableList<String>>) {
        drawFrame()

        val columns = terminal.terminalSize.columns - 1
        val rows = terminal.terminalSize.rows - 1

        clearRegion(rows, columns, stacks)

        // crate-moving logic
        val stackCount = stacks.keys.size
        val widthNeeded = 4 * stackCount - 1
        (1..stackCount)
            .map {
                val startPos = ((columns - widthNeeded) / 2)
                listOf(it, startPos + (it - 1) * 4)
            }
            .zip(stacks.values)
            .forEach { (nameAndPos, stack) ->
                val (name, pos) = nameAndPos
                terminal.setCursorPosition(pos, rows - 1)
                terminal.putCharacter(name.toString()[0])

                stack.withIndex()
                    .forEach() { (index, crate) ->
                        val crateStr = "[$crate]"
                        terminal.setCursorPosition(pos - 1, rows - 1 - (index + 1))
                        terminal.putString(crateStr)
                    }
            }

        terminal.flush()

        terminal.pollInput()?.let {
            resetTerminal(terminal)
            exitProcess(0)
        }
    }

    private fun clearRegion(rows: Int, cols: Int, stacks: MutableMap<Int, MutableList<String>>) {
        maxHeight = max(stacks.values.maxOf { it.size }, maxHeight)

        val top = max(1, rows - 1 - maxHeight)
        val left = (cols - stacks.keys.size * 4) / 2 - 1
        val bottom = rows - 2
        val right = cols - left - 4

        (top..bottom).forEach { row ->
            (left..right).forEach { col ->
                terminal.setCursorPosition(col, row)
                terminal.putCharacter(' ')
            }
        }
    }

    private fun resetTerminal(terminal: Terminal) {
        terminal.clearScreen()
        terminal.setCursorVisible(true)
    }

    private fun part1(stacks: MutableMap<Int, MutableList<String>>, num: Int, src: Int, dest: Int) {
        repeat(num) {
            stacks[src]?.last()?.let { crate ->
                stacks[dest]?.add(crate)
                stacks[src]?.removeLast()

                if (visualize) {
                    visualize(stacks)
                    Thread.sleep(100)
                }
            }
        }
    }

    private fun part2(stacks: MutableMap<Int, MutableList<String>>, num: Int, src: Int, dest: Int) {
        stacks[src]?.takeLast(num)?.let { crates ->
            stacks[dest]?.addAll(crates)

            repeat(num) {
                stacks[src]?.removeLast()
            }

            if (visualize) {
                visualize(stacks)
                Thread.sleep(100)
            }
        }
    }

    private fun buildStacks(lines: List<String>): MutableMap<Int, MutableList<String>> {
        val keys = lines.last()
            .trim()
            .split("""\s+""".toRegex())
            .map { it.toInt() }

        val stacks = keys.associateWith { mutableListOf<String>() }.toMutableMap()

        lines.dropLast(1)
            .reversed()
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
