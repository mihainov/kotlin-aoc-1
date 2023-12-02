package twentytwo

import readInput

//helper function to handle destructuring if there is no second element
operator fun <T> List<out T>.component2(): T? = if (size > 1) get(1) else null;

class CPU() {
    var cycle = 0
        private set
    var regX = 1
        private set

    fun isFinished(): Boolean {
        return instructionCounter == instructionRegister.size && busy == 0
    }

    private var busy = 0
    private var instructionCounter = 0
    private lateinit var instructionRegister: List<String>

    fun cycle() {
        cycle++
        if (isFinished()) {
            return
        }

        if (busy == 0) {
            executeInstruction()
        } else {
            busy--
        }
    }

    fun loadInstructions(instrs: List<String>) {
        instructionRegister = instrs
        instructionCounter = 0
    }

    private fun executeInstruction() {
        val (cmd, arg) = instructionRegister[instructionCounter++].split(" ")
        when (cmd) {
            "noop" -> noop()
            "addx" -> addx(arg!!)
            else -> throw IllegalArgumentException("Unrecognised command $cmd")
        }
    }

    private fun noop() {
        cycle += 1
    }

    private fun addx(arg: String) {
        regX += arg.toInt()
        cycle += 2
    }

    private fun printState() {
        println("Cycle: $cycle; Instr $instructionCounter of ${instructionRegister.size}; busy: $busy")
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val cpu = CPU()
        cpu.loadInstructions(input)

        while (!cpu.isFinished()) {
            cpu.cycle()
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    part1(listOf("noop", "addx 3", "addx -5"))


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day00_test")
    check(part1(testInput).also(::println) == 13140)
    check(part2(testInput).also(::println) == 0)

    val input = readInput("Day00")
    println(part1(input))
    println(part2(input))
}
