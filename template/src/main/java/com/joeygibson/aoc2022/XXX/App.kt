package com.joeygibson.aoc2022.XXX

import org.apache.logging.log4j.LogManager
import picocli.CommandLine
import java.io.File
import java.io.IOException
import java.util.concurrent.Callable
import kotlin.system.exitProcess

@CommandLine.Command(
    name = "XXX",
    mixinStandardHelpOptions = true,
    version = ["XXX 1.0.0"],
    description = ["AoC 2022 XXX"]
)
class App : Callable<Int> {
    @CommandLine.Parameters(index = "0", description = ["The file to process"])
    val file: File? = null

    @Throws(IOException::class)
    override fun call(): Int {
        if (file == null) {
            println("Usage: XXX <input file>")
            exitProcess(1)
        }

        val lines = readInput(file)

        return 0
    }

    companion object {
        private val logger = LogManager.getLogger(
            App::class.java
        )
    }
}
