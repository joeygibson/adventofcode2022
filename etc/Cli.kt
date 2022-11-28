package com.joeygibson.aoc2022.XXX

import org.apache.logging.log4j.LogManager
import picocli.CommandLine
import java.io.File
import java.io.IOException
import java.util.concurrent.Callable

@CommandLine.Command(
    name = "XXX",
    mixinStandardHelpOptions = true,
    version = ["XXX 1.0.0"],
    description = ["AoC 2022 XXX"]
)
class Cli : Callable<Int> {
    @CommandLine.Parameters(index = "0..*", description = ["The file(s) to process"])
    var files: Array<File>? = null

    @Throws(IOException::class)
    override fun call(): Int {
        files?.forEach { file ->
            val fileName = file.canonicalPath
        }

        return 0
    }

    companion object {
        private val logger = LogManager.getLogger(
            Cli::class.java
        )
    }
}
