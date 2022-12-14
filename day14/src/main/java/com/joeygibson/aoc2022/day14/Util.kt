package com.joeygibson.aoc2022.day14

import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.Terminal
import java.io.File

fun readInput(file: File) = file.readLines()

fun readWithoutBlanks(file: File) = readInput(file).filter { it.isNotBlank() }

fun printResults(part: String, result: Any) = println("$part: $result")

fun <T> List<T>.splitOn(predicate: (T) -> Boolean): List<List<T>> {
    val top = this.takeWhile { predicate(it) }
    val rest = this.dropWhile { !predicate(it) }.drop(1)

    return listOf(top, rest)
}

fun setupTerminal(): Terminal {
    val defaultTerminalFactory = DefaultTerminalFactory()
    val terminal = defaultTerminalFactory.createTerminal()
    val foreground = TextColor.Factory.fromString("white")
    val background = TextColor.Factory.fromString("black")

    terminal.setCursorVisible(false)
    terminal.setForegroundColor(foreground)
    terminal.setBackgroundColor(background)

    return terminal
}

fun resetTerminal(terminal: Terminal) {
    terminal.clearScreen()
    terminal.setCursorVisible(true)
}

fun getTerminalDimensions(terminal: Terminal): Pair<Int, Int> {
    val columns = terminal.terminalSize.columns - 1
    val rows = terminal.terminalSize.rows - 1

    return Pair(rows, columns)
}
