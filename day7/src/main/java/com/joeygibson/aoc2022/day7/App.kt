package com.joeygibson.aoc2022.day7

import picocli.CommandLine
import java.io.File
import java.io.IOException
import java.util.concurrent.Callable
import kotlin.system.exitProcess

@CommandLine.Command(
    name = "day7",
    mixinStandardHelpOptions = true,
    version = ["day7 1.0.0"],
    description = ["AoC 2022 day7"]
)
class App : Callable<Int> {
    @CommandLine.Parameters(index = "0", description = ["The file to process"])
    val file: File? = null

    @Throws(IOException::class)
    override fun call(): Int {
        if (file == null) {
            println("Usage: day7 <input file>")
            exitProcess(1)
        }

        val lines = readInput(file)
        val root = buildDirectory(lines.drop(2))

        printResults("part1", part1(root))
//        printResults("part2", part2(lines))

        return 0
    }

    private fun part1(root: ElvenDirectory): Int {
        val size = root.size
        println("size: $size")



        return -1
    }

    private fun walkDirectories(dir: ElvenDirectory, func: (ElvenDirectory) -> Boolean): ElvenDirectory {
        if (dir.directories.isEmpty()) {
            if (func(dir)) {
                return dir
            }
        }
        for (sub in dir.directories) {

        }
    }

    private fun part2(root: ElvenDirectory): Any {
        // part 2 goes here


        return "no result for part 2"
    }

    private fun buildDirectory(lines: List<String>): ElvenDirectory {
        val root = ElvenDirectory("/", null)
        var cwd = root

        for (line in lines) {
            when {
                (line.startsWith("dir") || line.startsWith("$ ls")) -> continue
                (line.startsWith("$ cd ..")) -> cwd = cwd.parent ?: root
                (line.startsWith("$ cd")) -> {
                    val subDirName = line.split(" ")[2]
                    val subDir = ElvenDirectory(subDirName, cwd)
                    cwd.directories.add(subDir)
                }

                else -> {
                    val (size, fileName) = line.split(" ")
                    val file = ElvenFile(fileName, size.toInt())
                    cwd.files.add(file)
                }
            }
        }

        return root
    }
}

data class ElvenDirectory(val name: String, val parent: ElvenDirectory?) {
    val files: MutableList<ElvenFile> = mutableListOf()
    val directories: MutableList<ElvenDirectory> = mutableListOf()

    val size: Long
        get() {
            val fileSizes = files.sumOf { it.size }
            val dirSizes = directories.sumOf { it.size }

            return fileSizes + dirSizes
        }
}

data class ElvenFile(val name: String, val size: Int)
