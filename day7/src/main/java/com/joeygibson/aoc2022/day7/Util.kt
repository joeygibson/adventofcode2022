package com.joeygibson.aoc2022.day7

import java.io.File

fun readInput(file: File) = file.readLines()

fun readWithoutBlanks(file: File) = readInput(file).filter { it.isNotBlank() }

fun printResults(part: String, result: Any) = println("$part: $result")

fun <T> List<T>.splitOn(predicate: (T) -> Boolean): List<List<T>> {
    val top = this.takeWhile { predicate(it) }
    val rest = this.dropWhile { !predicate(it) }.drop(1)

    return listOf(top, rest)
}
