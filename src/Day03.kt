// ext fun to get the priority of the char according to the task
fun Char.priority(): Int {
    val ascii = this.code
    return if (this.isUpperCase()) {
        ascii - 38
    } else {
        ascii - 96
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach {
            val firstComp = it.substring(0, it.length / 2).toSet()
            val secondComp = it.substring(it.length / 2, it.length).toSet()
            val intersection = firstComp.intersect(secondComp)
            sum += intersection.single().priority()
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        for (i in input.indices step 3) {
            sum += (input[i].toSet()
                    intersect input[i + 1].toSet()
                    intersect input[i + 2].toSet())
                .single().priority()
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
