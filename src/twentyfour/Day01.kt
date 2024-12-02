package twentyfour

import readInputTwentyFour
import kotlin.math.abs

fun main() {
    val currentFileName: String =
        Throwable().stackTrace.first().fileName
            ?.substringBefore('.')
            ?.substringAfter('.') ?: "Unknown"
    println("Current file name: $currentFileName")


    fun part1(input: List<String>): Int {
        val list1 = mutableListOf<Int>()
        val list2 = mutableListOf<Int>()
        input.forEach { tuple ->
            tuple.split("   ").let {
                list1.add(it[0].toInt())
                list2.add(it[1].toInt())
            }
        }

        list1.sort()
        list2.sort()

        return list1.mapIndexed { index, i ->
            abs(i - list2[index])
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val list1 = mutableListOf<Int>()
        val list2Occurrences = mutableMapOf<Int, Int>()
        input.forEach { tuple ->
            tuple.split("   ").let {
                list1.add(it[0].toInt())
                list2Occurrences[it[1].toInt()] = (list2Occurrences[it[1].toInt()] ?: 0) + 1
            }
        }

        return list1.sumOf {
            it * list2Occurrences.getOrDefault(it, 0)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputTwentyFour("${currentFileName}_test")
    val input = readInputTwentyFour(currentFileName)


    check(part1(testInput).also(::println) == 11)
    println(part1(input))

    check(part2(testInput).also(::println) == 31)
    println(part2(input))
}
