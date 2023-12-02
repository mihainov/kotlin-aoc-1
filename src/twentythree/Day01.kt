package twentythree

import readInputTwentyThree

enum class Numbers(val string: String, val number: Int) {
    ONE("one", 1),
    TWO("two", 2),
    THREE("three", 3),
    FOUR("four", 4),
    FIVE("five", 5),
    SIX("six", 6),
    SEVEN("seven", 7),
    EIGHT("eight", 8),
    NINE("nine", 9)
}

fun String.firstNumberInString(): Int? {
    return Numbers.values()
        .map {
            Pair(this.indexOf(it.string), it)
        }.sortedBy {
            it.first
        }.filter {
            it.first != -1
        }.firstOrNull()?.second?.number
}

fun String.lastNumberInString(): Int? {
    return Numbers.values()
        .map {
            Pair(this.lastIndexOf(it.string), it)
        }.sortedBy {
            it.first
        }.filter {
            it.first != -1
        }.lastOrNull()?.second?.number
}

fun part1(input: List<String>): Int {
    var result = 0
    input.forEach {
        for (i in it) {
            if (i.isDigit()) {
                result += i.digitToInt() * 10
                break
            }
        }

        for (i in it.reversed()) {
            if (i.isDigit()) {
                result += i.digitToInt()
                break
            }
        }
    }

    return result
}

fun findFirstOccurrence(str: String): Int? {
    str.forEachIndexed { index, c ->
        if (c.isDigit()) {
            return when (val it = str.substring(0, index).firstNumberInString()) {
                null -> c.digitToInt()
                else -> it
            }
        }
    }
    return str.firstNumberInString()
}

fun findLastOccurrence(str: String): Int? {
    for (i in (0..str.length - 1).reversed()) {
        if (str[i].isDigit()) {
            return when (val it = str.substring(i).lastNumberInString()) {
                null -> str[i].digitToInt()
                else -> it
            }
        }
    }
    return str.lastNumberInString()
}

fun part2(input: List<String>): Int {
    var result = 0;
    input.forEach { str ->
        print("$str: ")
        result += (findFirstOccurrence(str).also(::print) ?: 0) * 10

        result += findLastOccurrence(str).also(::print) ?: 0
        println()
    }
    return result
}

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInputTwentyThree("Day01_test")
//    check(part1(testInput).also(::println) == 142)
    check(part2(testInput).also(::println) == 281)

    val input = readInputTwentyThree("Day01")
    println("Part1: ${part1(input)}")
    println("Part2: ${part2(input)}")
}
