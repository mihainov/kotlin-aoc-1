abstract class Shape {
    abstract val score: Int
    abstract fun fightWith(shape: Shape): Int
    abstract fun lose(): Int
    abstract fun draw(): Int
    abstract fun win(): Int

    companion object {
        fun getItem(string: String): Shape {
            return when (string) {
                "A" -> Rock()
                "X" -> Rock()
                "B" -> Paper()
                "Y" -> Paper()
                "C" -> Scissors()
                "Z" -> Scissors()
                else -> throw IllegalArgumentException()
            }
        }
    }
}

class Rock : Shape() {
    override val score = 1
    override fun fightWith(shape: Shape): Int {
        return when (shape) {
            is Rock -> 3 + score
            is Paper -> 0 + score
            is Scissors -> 6 + score
            else -> throw IllegalArgumentException()
        }
    }

    override fun lose(): Int {
        return Scissors().score + 0
    }

    override fun draw(): Int {
        return Rock().score + 3
    }

    override fun win(): Int {
        return Paper().score + 6
    }
}

class Paper : Shape() {
    override val score = 2
    override fun fightWith(shape: Shape): Int {
        return when (shape) {
            is Rock -> 6 + score
            is Paper -> 3 + score
            is Scissors -> 0 + score
            else -> throw IllegalArgumentException()
        }
    }

    override fun lose(): Int {
        return Rock().score + 0
    }

    override fun draw(): Int {
        return Paper().score + 3
    }

    override fun win(): Int {
        return Scissors().score + 6
    }
}

class Scissors : Shape() {
    override val score = 3
    override fun fightWith(shape: Shape): Int {
        return when (shape) {
            is Rock -> 0 + score
            is Paper -> 6 + score
            is Scissors -> 3 + score
            else -> throw IllegalArgumentException()
        }
    }

    override fun lose(): Int {
        return Paper().score + 0
    }

    override fun draw(): Int {
        return Scissors().score + 3
    }

    override fun win(): Int {
        return Rock().score + 6
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        var score = 0
        input.forEach {
            val turns = it.split(" ")
            score += Shape.getItem(turns[1]).fightWith(Shape.getItem(turns[0]))
        }
        return score
    }

    fun part2(input: List<String>): Int {
        var score = 0
        input.forEach {
            val turns = it.split(" ")
            val shape = Shape.getItem(turns[0])
            score += when (turns[1]) {
                "X" -> shape.lose()
                "Y" -> shape.draw()
                "Z" -> shape.win()
                else -> throw IllegalStateException()
            }
        }
        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
