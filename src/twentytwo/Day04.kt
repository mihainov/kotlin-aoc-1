package twentytwo

import readInput

data class Section(val start: Int, val end: Int) {
    fun getSize(): Int {
        return end - start
    }
}

data class ElfSections(val firstSection: Section, val secondSection: Section)

fun String.toSections(): ElfSections {
    val elvesSection = this.split(",")
    val firstElfSections = elvesSection.component1().split("-")
    val firstSection = Section(firstElfSections.component1().toInt(), firstElfSections.component2().toInt())
    val secondElfSections = elvesSection.component2().split("-")
    val secondSection = Section(secondElfSections.component1().toInt(), secondElfSections.component2().toInt())

    // sort so that the bigger section is always first
    val sections = listOf(firstSection, secondSection).sortedBy { it.getSize() }.reversed()
    return ElfSections(sections.component1(), sections.component2())
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { it.toSections() }
            .stream()
            .filter { it.firstSection.start <= it.secondSection.start && it.firstSection.end >= it.secondSection.end }
            .count()
            .toInt()
    }

    fun part2(input: List<String>): Int {
        return input.map { it.toSections() }
            .stream()
            .filter {
                it.secondSection.start in it.firstSection.start..it.firstSection.end
                        || it.secondSection.end in it.firstSection.start..it.firstSection.end
            }
            .count()
            .toInt()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput).also(::println) == 2)
    check(part2(testInput).also(::println) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
