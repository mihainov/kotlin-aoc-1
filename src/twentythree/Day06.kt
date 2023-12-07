package twentythree

import readInputTwentyThree
import java.util.stream.IntStream
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt
import kotlin.streams.asSequence

data class Race(val time: Long, val distance: Long)


fun main() {
    fun part1(input: List<String>): Int {
        val times = input[0].substringAfter(':')
            .split(" ")
            .filter(String::isNotBlank)
            .map(String::trim)
            .map(String::toLong)

        val distances = input[1].substringAfter(':')
            .split(" ")
            .filter(String::isNotBlank)
            .map(String::trim)
            .map(String::toLong)

        val races: List<Race> = IntStream.range(0, times.size)
            .asSequence()
            .map { i -> Race(times[i], distances[i]).also { println("Parsed race $it") } }
            .toList()


        return races.map { race ->
            println("Handling race $race")
            val a = -1.0
            val b = race.time.toDouble()
            val c = -race.distance.toDouble()
            val d = b * b - 4 * a * c
            val root1 = ((-b + sqrt(d)) / (2 * a)).also { println("Root 1 $it") }
            val root2 = ((-b - sqrt(d)) / (2 * a)).also { println("Root 2 $it") }
            val biggerRoot = max(root1, root2)
            val smallerRoot = min(root1, root2)
            IntStream.range(ceil(smallerRoot + 0.000000000001).toInt(), ceil(biggerRoot - 0.000000000001).toInt())
                .count()
                .toInt()
        }.fold(1) { acc, i -> acc * i }
    }

    fun part2(input: List<String>): Int {
        val t = input[0]
            .replace(" ", "")
            .substringAfter(':')
            .toLong()

        val d = input[1]
            .replace(" ", "")
            .substringAfter(':')
            .toLong()

        val race = Race(t, d)

        println("Handling race $race")
        val time = race.time.toDouble()
        val distance = -race.distance.toDouble()

        val discriminant = time * time - 4 * -distance
        val root1 = ((-time + sqrt(discriminant)) / (2 * -1))
        val root2 = ((-time - sqrt(discriminant)) / (2 * -1))

        val biggerRoot = max(root1, root2)
        val smallerRoot = min(root1, root2)

        // hack with 0.000000001 to avoid situations in which the solution is an exact integer
        return IntStream.range(ceil(smallerRoot + 0.000000000001).toInt(), ceil(biggerRoot - 0.000000000001).toInt())
            .count()
            .toInt()
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInputTwentyThree("Day06_test")
    check(part1(testInput).also(::println) == 288)
    check(part2(testInput).also(::println) == 71503)

    val input = readInputTwentyThree("Day06")
    println(part1(input))
    println(part2(input))
}
