package com.joeygibson.aoc2022.day16

import picocli.CommandLine
import kotlin.system.exitProcess
fun main(args: Array<String>) {
    val exitCode = CommandLine(App()).execute(*args)
    exitProcess(exitCode)
}

