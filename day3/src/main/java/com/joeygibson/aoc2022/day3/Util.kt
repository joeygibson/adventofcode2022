package com.joeygibson.aoc2022.day3

import java.io.File

fun readInput(file: File) = file.readLines()

fun readWithoutBlanks(file: File) = readInput(file).filter { it.isNotBlank() }

fun printResults(part: String, result: Any) = println("$part: $result")

	
