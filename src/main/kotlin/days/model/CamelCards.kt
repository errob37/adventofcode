package days.model


enum class GameMode {
    NORMAL,
    WITH_JOKERS
}

class CamelCards(private val input: List<String>, private val gameMode: GameMode) {

    fun totalWinnings() =
        createHands()
            .sorted()
            .mapIndexed { index, hand -> (index + 1) * hand.bid }
            .sum()

    private fun createHands() =
        input.map {
            val (cards, bid) = it.split(" ")
            Hand(cards.trim(), bid.trim().toLong(), gameMode)
        }
}

data class Hand(
    val cards: String,
    val bid: Long,
    val gameMode: GameMode,
) : Comparable<Hand> {


    private val type: HandType
        get() {
            return if (gameMode == GameMode.NORMAL) {
                getTypeForNormalGameMode()
            } else {
                getTypeForWithJokerGameMode()
            }
        }

    private fun getTypeForNormalGameMode(): HandType {
        val byCardValue = cards.asSequence().groupBy { it }
        return when (byCardValue.size) {
            1 -> HandType.FIVE_OF_KIND
            2 -> handleTwoSets(byCardValue)
            3 -> handleThreeSets(byCardValue)
            4 -> HandType.ONE_PAIR
            else -> HandType.HIGH_CARD
        }
    }

    private fun getTypeForWithJokerGameMode(): HandType {
        val type = getTypeForNormalGameMode()

        return when (cards.jokerCount) {
            0, 5 -> type
            4 -> HandType.FIVE_OF_KIND
            3 -> if (type == HandType.FULL_HOUSE) HandType.FIVE_OF_KIND else HandType.FOUR_OF_KIND
            2 -> if (type == HandType.TWO_PAIR) HandType.FOUR_OF_KIND else HandType.THREE_OF_KIND
            1 -> when (type) {
                HandType.FOUR_OF_KIND -> HandType.FIVE_OF_KIND
                HandType.THREE_OF_KIND -> HandType.FOUR_OF_KIND
                HandType.TWO_PAIR -> HandType.FULL_HOUSE
                HandType.ONE_PAIR -> HandType.THREE_OF_KIND
                else -> HandType.ONE_PAIR
            }

            else -> HandType.ONE_PAIR
        }
    }

    private fun getOrderHexa() =
        cards.map {
            when (it) {
                'A' -> 'E'
                'K' -> 'D'
                'Q' -> 'C'
                'J' -> if (gameMode == GameMode.NORMAL) 'B' else '1'
                'T' -> 'A'
                else -> it
            }
        }
            .joinToString("")
            .toInt(16)

    private fun handleTwoSets(byCardValue: Map<Char, List<Char>>) =
        if (byCardValue.any { it.value.size == 4 }) HandType.FOUR_OF_KIND
        else HandType.FULL_HOUSE

    private fun handleThreeSets(byCardValue: Map<Char, List<Char>>) =
        if (byCardValue.values.any { it.size == 3 }) HandType.THREE_OF_KIND
        else HandType.TWO_PAIR

    override fun compareTo(other: Hand) =
        if (other.type != type) type.ordinal - other.type.ordinal
        else getOrderHexa() - other.getOrderHexa()
}

enum class HandType {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_KIND,
    FULL_HOUSE,
    FOUR_OF_KIND,
    FIVE_OF_KIND
}

private var String.jokerCount: Int
    get() = this.count { 'J' == it }
    private set(_) {}

