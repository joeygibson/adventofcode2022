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

    private val totalSpace = 70_000_000
    private val desiredAvailale = 30_000_000

    @Throws(IOException::class)
    override fun call(): Int {
        if (file == null) {
            println("Usage: day7 <input file>")
            exitProcess(1)
        }

        val lines = readInput(file)
        val root = buildDirectory(lines.drop(2))

        printResults("part1", part1(root))
        printResults("part2", part2(root))

        return 0
    }

    private fun part1(root: Directory): Long {
        val matchingDirs = walkDirectories(root) { d -> d.size <= 100_000 }

        return matchingDirs.sumOf { it.size }
    }

    private fun part2(root: Directory): Long {
        val totalUsed = root.size
        val needToDelete = desiredAvailale - (totalSpace - totalUsed)

        return walkDirectories(root) { d -> d.size >= needToDelete }
            .minOf { it.size }
    }


    private fun walkDirectories(dir: Directory, func: (Directory) -> Boolean): List<Directory> {
        val matchingDirs = buildList {
            if (func(dir)) {
                this.add(dir)
            }
        }

        val matchingSubDirs = dir.directories
            .flatMap { walkDirectories(it, func) }

        return matchingDirs.plus(matchingSubDirs)
    }

    private fun buildDirectory(lines: List<String>): Directory {
        val root = Directory("/", null)
        var cwd = root

        for (line in lines) {
            when {
                (line.startsWith("dir") || line.startsWith("$ ls")) -> continue
                (line.startsWith("$ cd ..")) -> cwd = cwd.parent ?: root
                (line.startsWith("$ cd")) -> {
                    val subDirName = line.split(" ")[2]
                    val subDir = Directory(subDirName, cwd)
                    cwd.directories.add(subDir)
                    cwd = subDir
                }

                else -> {
                    val size = line.split(" ")[0]
                    cwd.fileSize += size.toInt()
                }
            }
        }

        return root
    }
}

data class Directory(val name: String, val parent: Directory?) {
    var fileSize: Long = 0
    val directories: MutableList<Directory> = mutableListOf()

    val size: Long
        get() {
            val dirSizes = directories.sumOf { it.size }

            return fileSize + dirSizes
        }

    override fun toString(): String {
        return """Dir(name=${name}, parent=${parent?.name ?: ""}, size=$size"""
    }
}

