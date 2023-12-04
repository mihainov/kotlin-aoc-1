package twentythree

import readInputTwentyThree
import kotlin.math.pow

/**
 * Integer power using [Double.pow]
 */
infix fun Int.pow(exponent: Int): Int = toDouble().pow(exponent).toInt()

data class Card(val id: Int, val winningNumbers: Set<Int>, val ownNumbers: Set<Int>) {
    fun numberOfMatches() = winningNumbers.intersect(ownNumbers)
}

fun String.parseCard(): Card {
    val id = this.substring(4, this.indexOf(':')).trim().toInt().also { println("Parsed index: $it") }
    val winningNumbers = this.substring(this.indexOf(':') + 1, this.indexOf('|'))
        .split(' ')
        .map(String::trim)
        .filter { it.isNotBlank() }
        .map(String::toInt)
        .toSet()

    val ownNumbers = this.substring(this.indexOf('|') + 1)
        .split(' ')
        .map(String::trim)
        .filter { it.isNotBlank() }
        .map(String::toInt)
        .toSet()

    return Card(id, winningNumbers, ownNumbers)
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { it.parseCard() }
            .map { it.numberOfMatches() }
            .sumOf {
                if (it.isEmpty()) {
                    0
                } else {
                    1 * 2.pow(it.size - 1)
                }
            }
    }

    fun part2(input: List<String>): Int {
        val cards = input.map { it.parseCard() }
        val copiesAmount = cards.indices.associateWith { 1 }.toMutableMap()

        copiesAmount.keys.forEach { index ->
            cards[index].numberOfMatches().size
                .also { println("Card ${index + 1} has $it matches") }
                .let { matches ->
                    val currentCopies = copiesAmount[index] ?: 0.also { println("Have $it of card ${index + 1}") }
                    for (i in 0 until matches) {
                        val targetIndex = index + i + 1
                        copiesAmount[targetIndex] = copiesAmount[targetIndex]?.plus(currentCopies) ?: 0
                        println("Adding $currentCopies copies to card $targetIndex")
                    }
                    if (matches == 0 && currentCopies == 1) {
                        return@forEach
                    }
                }
        }

        return copiesAmount.values.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputTwentyThree("Day04_test")
    check(part1(testInput).also(::println) == 13)
    check(part2(testInput).also(::println) == 30)

    val input = readInputTwentyThree("Day04")
    println(part1(input))
    println(part2(input))
}
