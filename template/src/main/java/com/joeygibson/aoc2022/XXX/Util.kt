package com.joeygibson.aoc2022.XXX

import java.io.File

fun readInput(file: File) = file.readLines()

fun readWithoutBlanks(file: File) = readInput(file).filter { it.isNotBlank() }
	
