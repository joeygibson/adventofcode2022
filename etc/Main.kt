package com.joeygibson.aoc2022.XXX

import picocli.CommandLine
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val exitCode = CommandLine(Cli()).execute(*args)
    exitProcess(exitCode)
}

