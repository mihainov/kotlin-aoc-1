package twentythree

import readInputTwentyThree

enum class Combination {
    FIVE_OF_A_KIND,
    FOUR_OF_A_KIND,
    FULL_HOUSE,
    THREE_OF_A_KIND,
    TWO_PAIR,
    PAIR,
    HIGH;
}

enum class PlayingCard(val code: String) {
    ACE("A"),
    KING("K"),
    QUEEN("Q"),
    JACK("J"),
    TEN("T"),
    NINE("9"),
    EIGHT("8"),
    SEVEN("7"),
    SIX("6"),
    FIVE("5"),
    FOUR("4"),
    THREE("3"),
    TWO("2");
}

fun String.getCardByCode(): PlayingCard {
    return PlayingCard.values().find { it.code == this }!!
}

data class Hand(val cards: List<PlayingCard>, val bet: Int) : Comparable<Hand> {
    fun getStrength(): Combination {
        val occurrences = PlayingCard.values().associateWith { 0 }.toMutableMap()
        this.cards.forEach { occurrences[it] = occurrences[it]!!.inc() }
        val sorted = occurrences.values.sortedDescending()
        return when (sorted.first()) {
            5 -> Combination.FIVE_OF_A_KIND
            4 -> Combination.FOUR_OF_A_KIND
            3 -> if (sorted[1] == 2) {
                Combination.FULL_HOUSE
            } else {
                Combination.THREE_OF_A_KIND
            }

            2 -> if (sorted[1] == 2) {
                Combination.TWO_PAIR
            } else {
                Combination.PAIR
            }

            1 -> Combination.HIGH
            else -> Combination.HIGH
        }
    }

    override fun compareTo(other: Hand): Int {
        this.getStrength().compareTo(other.getStrength()).let {
            if (it != 0) {
                print("$this compared to $other is")
                return it.also(::println)
            }
        }
        print("Comparing $this to $other")
        this.cards.indices.forEach { idx ->
            this.cards[idx].compareTo(other.cards[idx]).let {
                if (it != 0) {
                    return it.also(::println)
                }
            }
        }
        println(0)
        return 0
    }
}

fun String.parseHand(): Hand {
    val cards = mutableListOf<PlayingCard>()
    for (i in 0..4) {
        cards.add(this[i].toString().getCardByCode())
    }
    val bet = this.substringAfter(' ').toInt()
    return Hand(cards, bet)
}


fun main() {
    fun part1(input: List<String>): Int {
        val hands = input.map { it.parseHand().also { println("Parsed hand $it with strength ${it.getStrength()}") } }
            .sortedDescending()

        return hands.mapIndexed { index, hand ->
            ((index + 1) * hand.bet).also { println("${index + 1} $hand ${hand.getStrength()} has a total value of $it") }
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputTwentyThree("Day07_test")
    // using test input of
    //2345A 1
    //Q2KJJ 13
    //Q2Q2Q 19
    //T3T3J 17
    //T3Q33 11
    //2345J 3
    //J345A 2
    //32T3K 5
    //T55J5 29
    //KK677 7
    //KTJJT 34
    //QQQJA 31
    //JJJJJ 37
    //JAAAA 43
    //AAAAJ 59
    //AAAAA 61
    //2AAAA 23
    //2JJJJ 53
    //JJJJ2 41
    check(part1(testInput).also(::println) == 6592)
//    check(part2(testInput).also(::println) == 6839)

    val input = readInputTwentyThree("Day07")
    println(part1(input))
    println(part2(input))
}
