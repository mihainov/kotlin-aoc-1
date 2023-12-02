package twentytwo

import readInput
import java.util.*
import java.util.stream.Collectors

typealias Crate = Char
typealias Stack = LinkedList<Crate>

fun List<String>.createDock(): Dock {
    // does not work correctly when nCrates == 0, but that's impossible
    val nCrates = (this.first().length + 1) / 4
    val dock = Dock(nCrates)

    // filter starting crates
    this.forEach {
        for (i in 0 until nCrates) {
            val relevantChar = it[1 + (i * 4)]
            if (relevantChar.isLetter()) {
                dock.stacks[i].addLast(relevantChar)
            }
        }
    }
    return dock
}

// to support destructuring declaration with 6 elements
private operator fun <E> List<E>.component6(): E {
    return this[5]
}

class Dock(nStacks: Int) {
    val stacks = mutableListOf<Stack>()

    init {
        repeat(nStacks) { stacks.add(Stack()) }
    }

    fun getCrateTops(): String {
        return stacks.stream().map {
            it.peek().toString()
        }.collect(Collectors.joining())
    }

    fun handleOperationWithCrateMover9000(operation: String) {
        // move 3 from 2 to 1
        val (_, nCrates, _, fromStack, _, toStack) = operation.split(' ')
        operateCrateMover9000(nCrates.toInt(), fromStack.toInt(), toStack.toInt())
    }

    private fun operateCrateMover9000(nCrates: Int, fromStack: Int, toStack: Int) {
        repeat(nCrates) {
            val crateInTransit = stacks[fromStack - 1].pop()
            stacks[toStack - 1].addFirst(crateInTransit)
        }
    }

    fun handleOperationWithCrateMover9001(operation: String) {
        // move 3 from 2 to 1
        val (_, nCrates, _, fromStack, _, toStack) = operation.split(' ')
        operateCrateMover9001(nCrates.toInt(), fromStack.toInt(), toStack.toInt())
    }

    private fun operateCrateMover9001(nCrates: Int, fromStack: Int, toStack: Int) {
        val cratesInTransit = mutableListOf<Crate>()
        repeat(nCrates) {
            cratesInTransit.add(stacks[fromStack - 1].pollFirst())
        }
        cratesInTransit.reversed().forEach(stacks[toStack - 1]::addFirst)
    }
}


fun main() {
    fun part1(input: List<String>): String {
        val dockCreationCommands = input.filter { it.contains("[") }
        val dockOperationsCommands = input.filter { it.contains("move") }
        val dock = dockCreationCommands.createDock()
        dockOperationsCommands.forEach(dock::handleOperationWithCrateMover9000)
        return dock.getCrateTops()
    }

    fun part2(input: List<String>): String {
        val dockCreationCommands = input.filter { it.contains("[") }
        val dockOperationsCommands = input.filter { it.contains("move") }
        val dock = dockCreationCommands.createDock()
        dockOperationsCommands.forEach(dock::handleOperationWithCrateMover9001)
        return dock.getCrateTops()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput).also(::println) == "CMZ")
    check(part2(testInput).also(::println) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
