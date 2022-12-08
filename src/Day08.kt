data class Visibility(
    var fromTop: Boolean = false,
    var fromLeft: Boolean = false,
    var fromRight: Boolean = false,
    var fromBot: Boolean = false
) {
    fun isVisible(): Boolean {
        return fromBot || fromLeft || fromRight || fromTop
    }
}

fun ltrSeeker(treeRow: List<Int>, lolVisibility: List<List<Visibility>>, ri: Int) {
    var currentHighest = -1
    treeRow.forEachIndexed { ti, tree ->
        if (tree > currentHighest) {
            lolVisibility[ri][ti].fromLeft = true
            currentHighest = tree
            if (currentHighest == 9) {
                return
            }
        }
    }
}

fun rtlSeeker(treeRow: List<Int>, lolVisibility: List<List<Visibility>>, ri: Int) {
    var currentHighest = -1
    for (ti in 0..treeRow.lastIndex) {
        val trueIndex = treeRow.lastIndex - ti
        val tree = treeRow[trueIndex]
        if (tree > currentHighest) {
            lolVisibility[ri][trueIndex].fromRight = true
            currentHighest = tree
            if (currentHighest == 9) {
                return
            }
        }
    }
}

fun ttbSeeker(lolTree: List<List<Int>>, lolVisibility: List<List<Visibility>>, ci: Int) {
    var currentHighest = -1
    for (i in 0..lolTree.lastIndex) {
        val tree = lolTree[i][ci]
        if (tree > currentHighest) {
            lolVisibility[i][ci].fromTop = true
            currentHighest = tree
            if (currentHighest == 9) {
                return
            }
        }
    }
}

fun bttSeeker(lolTree: List<List<Int>>, lolVisibility: List<List<Visibility>>, ci: Int) {
    var currentHighest = -1
    for (i in 0..lolTree.lastIndex) {
        val trueIndex = lolTree.lastIndex - i
        val tree = lolTree[trueIndex][ci]
        if (tree > currentHighest) {
            lolVisibility[trueIndex][ci].fromTop = true
            currentHighest = tree
            if (currentHighest == 9) {
                return
            }
        }
    }
}

fun main() {

    fun part1(input: List<String>): Int {
        val lolTree = mutableListOf<List<Int>>()
        input.forEach { treeRow -> lolTree.add(treeRow.toCharArray().map { it.digitToInt() }.toList()) }
        val lolVisibility = List<List<Visibility>>(lolTree.size) {
            List<Visibility>(lolTree.first().size) { Visibility() }
        }

        lolTree.forEachIndexed { ri, treeRow ->
            ltrSeeker(treeRow, lolVisibility, ri)
            rtlSeeker(treeRow, lolVisibility, ri)
        }

        for (i in 0..lolTree.first().lastIndex) {
            ttbSeeker(lolTree, lolVisibility, i)
            bttSeeker(lolTree, lolVisibility, i)
        }
        return lolVisibility.sumOf { it.count(Visibility::isVisible) }
    }


    fun part2(input: List<String>): Int {
        return 0
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput).also(::println) == 21)
    check(part2(testInput).also(::println) == 0)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
