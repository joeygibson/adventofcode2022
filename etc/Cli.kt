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
class App : Callable<Int> {
    @CommandLine.Parameters(index = "0", description = ["The file to process"])
    var file: File? = null

    @Throws(IOException::class)
    override fun call(): Int {
		val fileName = file?.canonicalPath

        return 0
    }

    companion object {
        private val logger = LogManager.getLogger(
            App::class.java
        )
    }
}
