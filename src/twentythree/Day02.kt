package twentythree

import readInputTwentyThree

typealias Turn = Map<Color, Int>

fun Turn.isPossible(constraints: Turn): Boolean {
    return constraints.entries.all { constraint ->
        // if no balls of a given color are drawn, element is null
        // in this case use -1, as it's always possible to draw 0 balls of a color
        (this[constraint.key]?.compareTo(constraint.value) ?: -1) <= 0
    }
}

data class Game(val index: Int, val turns: List<Turn>)

enum class Color(val string: String) {
    Red("red"), Green("green"), Blue("blue")
}

fun String.toColor(): Color {
    return Color.values().find { it.string == this }!!
}


fun String.parseGame(): Game {
    val index = this.substring(5, this.indexOf(':')).toInt().also { println("Parsed index: $it") }
    val stringTurns = this.substring(this.indexOf(':') + 1).split(';')
    val turns = stringTurns.map { it.split(", ") } // "7 green, 2 red, 1 blue"
        .map { untrimmedCombination -> // " 7 green"
            untrimmedCombination.map(String::trim) // "7 green"
                .map { it.split(" ") } // ["7", "green"]
                .associate {
                    Pair(it.component2().toColor(), it.component1().toInt())
                }
        }
    return Game(index, turns)
}


fun main() {
    val bag = mapOf(Pair(Color.Red, 12), Pair(Color.Blue, 14), Pair(Color.Green, 13))

    fun part1(input: List<String>): Int {
        val games = input.map { it.parseGame() }
        return games.filter { game: Game ->
            game.turns.all { it.isPossible(bag) }
        }.sumOf { game: Game ->
            game.index
        }
    }

    fun part2(input: List<String>): Int {
        val games = input.map { it.parseGame() }
        return games.sumOf {
            it.turns.fold(mutableMapOf<Color, Int>()) { acc, map ->
                map.forEach { entry ->
                    acc.merge(entry.key, entry.value) { new, old -> maxOf(new, old) }
                }
                acc
            }.values.reduce { acc, i -> acc * i }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputTwentyThree("Day02_test")
    check(part1(testInput).also(::println) == 8)
    check(part2(testInput).also(::println) == 2286)

    val input = readInputTwentyThree("Day02")
    println(part1(input))
    println(part2(input))
}
