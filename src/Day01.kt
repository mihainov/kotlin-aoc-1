import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Int {
        var maxSum: Int = -1
        var tempSum = 0
        input.forEach { inputString ->
            if (inputString.isBlank()) {
                maxSum = max(tempSum, maxSum)
                tempSum = 0
                return@forEach
            }

            tempSum += inputString.toInt()
        }
        return maxSum
    }

    fun part2(input: List<String>): Int {
        val elvesSum = mutableListOf<Int>()
        var tempSum = 0
        input.forEach { inputString ->
            if (inputString.isBlank()) {
                elvesSum.add(tempSum)
                tempSum = 0
                return@forEach
            }

            tempSum += inputString.toInt()
        }
        // handle last elf's calories
        elvesSum.add(tempSum)

        elvesSum.sortDescending()
        return elvesSum.subList(0, 3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24_000)
    check(part2(testInput) == 45_000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
