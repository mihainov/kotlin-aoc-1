package twentytwo

import readInputTwentyTwo

fun main() {
    outerFunc@ fun part1(input: List<String>): Int {
        input.single().windowed(4).forEachIndexed { idx, string ->
            if (string.toSet().size == 4) {
                return@outerFunc idx + 4
            }
        }
        return -1
    }

    outerFunc@ fun part2(input: List<String>): Int {
        input.single().windowed(14).forEachIndexed { idx, string ->
            if (string.toSet().size == 14) {
                return@outerFunc idx + 14
            }
        }
        return -1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputTwentyTwo("Day06_test")
    check(part1(testInput).also(::println) == 5)
    check(part2(testInput).also(::println) == 23)

    val input = readInputTwentyTwo("Day06")
    println(part1(input))
    println(part2(input))
}
