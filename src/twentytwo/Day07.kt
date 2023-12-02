package twentytwo

import readInput

data class File(val size: Int, val name: String)

typealias FileSystem = MutableMap<String, MutableList<File>>

fun handleCommandCd(arg: String, currentDir: MutableList<String>) {
    when (arg) {
        "/" -> {
            currentDir.removeAll { true }
            currentDir.add("")
        }

        ".." -> {
            currentDir.removeLast()
        }

        else -> {
            currentDir.add(arg)
        }
    }
}

fun handleCommand(terminal: String, currentDir: MutableList<String>) {
    val splitString = terminal.split(" ")
    val command = splitString[1]
    // then it's a command
    @Suppress("MoveVariableDeclarationIntoWhen")
    when (command) {
        "cd" -> {
            val arg = splitString[2]
            handleCommandCd(arg, currentDir)
        }

        "ls" -> {
            if (splitString.size == 3) {
                error("Expected size is not 2 when ls command")
            }
        }

        else -> {
            error("Command $command not implemented yet")
        }
    }
}

fun parseInput(terminal: String, currentDir: MutableList<String>, fs: FileSystem) {
    val terminalSplit = terminal.split(" ")

    // handle directory changes
    if (terminalSplit.first() == "$") {
        handleCommand(terminal, currentDir)
        return
    }

    // add directories
    val path = currentDir.joinToString(separator = "/")
    if (!fs.containsKey(path)) {
        fs[path] = mutableListOf()
    }

    if (terminalSplit.first() == "dir") {
        fs[path]?.add(File(-1, terminalSplit.component2()))
        return
    }

    fs[path]
        ?.add(File(terminalSplit.first().toInt(), terminalSplit.component2()))

}

fun getFoldersContentSizes(fs: MutableMap<String, MutableList<File>>): Map<String, Int> {
    val foldersMap = mutableMapOf<String, Int>()
    // start by child folders
    fs.keys.sortedBy {
        it.split("/").size
    }.reversed().forEach {
        val folderSum = fs[it]!!
            .filter { file -> file.size != -1 }
            .sumOf { file -> file.size }
        foldersMap[it] = folderSum
    }

    // handle size of contained folders
    foldersMap.keys.forEach { subFolder ->
        for (folder in foldersMap.keys) {
            // make sure that we only count sub-folders that are 1 level lower than the folder
            if (folder.split("/").size + 1 != subFolder.split("/").size) {
                continue
            }
            if (subFolder.startsWith(folder)) {
                foldersMap[folder] = foldersMap[folder]!!.plus(foldersMap[subFolder]!!)
            }
        }
    }
    return foldersMap
}

fun main() {

    fun part1(input: List<String>): Int {
        val currentDir = mutableListOf<String>()
        val fs = mutableMapOf<String, MutableList<File>>()
        input.forEach { parseInput(it, currentDir, fs) }
        return getFoldersContentSizes(fs).values.filter { it < 100_000 }.sum()
    }

    fun part2(input: List<String>): Int {
        val fsSize = 70000000
        val necessarySpace = 30000000
        var needToFree: Int
        val currentDir = mutableListOf<String>()
        val fs = mutableMapOf<String, MutableList<File>>()
        input.forEach { parseInput(it, currentDir, fs) }
        return getFoldersContentSizes(fs).values.also {
            val rootSize = it.max()
            val unused = (fsSize - rootSize)
            needToFree = necessarySpace - unused
        }.filter { it > needToFree }.min()
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput).also(::println) == 95437)
    check(part2(testInput).also(::println) == 24933642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
