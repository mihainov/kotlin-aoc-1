package twentythree

import readInputTwentyThree


fun main() {
    fun part1(input: List<String>): Int {
        val directions = input.first().map { it }
        val nodes = input.filter { it.contains('=') }
            .associate {
                Pair(
                    it.substring(0, 3),
                    Pair(it.substring(7, 10), it.substring(12, 15))
                )
            }

        var currentNode = "AAA"
        var step = 0
        while (currentNode != "ZZZ") {
            if (directions[step % directions.size] == 'L') {
                currentNode = nodes[currentNode]!!.first
            } else {
                currentNode = nodes[currentNode]!!.second
            }
            step++
        }
        return step
    }

    fun part2(input: List<String>): Long {
        val directions = input.first().map { it }
        val nodes = input.filter { it.contains('=') }
            .associate {
                Pair(
                    it.substring(0, 3),
                    Pair(it.substring(7, 10), it.substring(12, 15))
                )
            }

        val currentNodes = nodes.keys.filter { it.endsWith("A") }.toMutableList()
        var steps = 0L
        var startTimer = System.currentTimeMillis()
        while (!currentNodes.all { it.endsWith("Z") }) {
            for (i in currentNodes.indices) {
                if (directions[(steps % directions.size).toInt()] == 'L') {
                    currentNodes[i] = nodes[currentNodes[i]]!!.first
                } else {
                    currentNodes[i] = nodes[currentNodes[i]]!!.second
                }
            }
            steps++
            if (steps % 10_000_000 == 0L) {
                println("Steps $steps: $currentNodes; ${System.currentTimeMillis() - startTimer}")
                startTimer = System.currentTimeMillis()
            }
        }
        return steps
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputTwentyThree("Day08_test")
//    check(part1(testInput).also(::println) == 2) yeet cuz part 2 does not work the same way
    check(part2(testInput).also(::println) == 6L)

    val input = readInputTwentyThree("Day08")
    println(part1(input))
    println(part2(input))
}