package twentyfour

import readInputTwentyThree

fun main() {
    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputTwentyThree("Day00_test")
    check(part1(testInput).also(::println) == 0)
    check(part2(testInput).also(::println) == 0)

    val input = readInputTwentyThree("Day00")
    println(part1(input))
    println(part2(input))
}
