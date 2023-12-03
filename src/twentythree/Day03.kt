package twentythree

import readInputTwentyThree

fun isCoordAdjacentToSymbol(row: Int, col: Int, matrix: List<String>): Boolean {
    for (i in -1..1) {
        try {
            if (matrix[row - 1][col + i].isSymbol()) {
                return true
            }
        } catch (e: IndexOutOfBoundsException) {
            /* no-op */
        }
    }
    try {
        if (matrix[row][col - 1].isSymbol()) {
            return true
        }
    } catch (e: IndexOutOfBoundsException) {
        /* no-op */
    }

    try {
        if (matrix[row][col + 1].isSymbol()) {
            return true
        }
    } catch (e: IndexOutOfBoundsException) {
        /* no-op */
    }

    for (i in -1..1) {
        try {
            if (matrix[row + 1][col + i].isSymbol()) {
                return true
            }
        } catch (e: IndexOutOfBoundsException) {
            /* no-op */
        }
    }
    return false
}

fun Char.isSymbol(): Boolean {
    return !this.isDigit() && !this.isLetter() && this != '.'
}

fun main() {
    fun part1(input: List<String>): Int {
        var result = 0
        var isPart = false
        var currentNum = 0
        var multiplier = 1
        for (row in input.indices) {
            for (col in input[row].indices.reversed()) {
                input[row][col].let {
                    if (it.isDigit()) {
                        currentNum += multiplier * it.digitToInt()
                        multiplier *= 10
                        if (!isPart) {
                            isPart = isCoordAdjacentToSymbol(row, col, input)
                        }
                    } else {
                        if (isPart) {
                            result += currentNum
                        }
                        isPart = false
                        currentNum = 0
                        multiplier = 1
                    }
                }
            }
            // if whole row is iterated, reset number
            if (isPart) {
                result += currentNum
            }
            isPart = false
            currentNum = 0
            multiplier = 1
        }
        return result;
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputTwentyThree("Day03_test")
    check(part1(testInput).also(::println) == 4361)
    check(part2(testInput).also(::println) == 0)

    val input = readInputTwentyThree("Day03")
    println(part1(input))
    println(part2(input))
}
