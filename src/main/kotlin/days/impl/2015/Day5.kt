package days.impl.`2015`


import days.impl.AdventOfCodeDayImpl

class Day5 : AdventOfCodeDayImpl(2015, 5, 2L, 1L, 258L) {

    override fun partOne(input: List<String>): Long = input.count { it.isNice(true) }.toLong()
    override fun partTwo(input: List<String>): Long = input.count { it.isNice(false) }.toLong()


    private fun String.isNice(isPartOne: Boolean = true): Boolean =
        (isPartOne && containsAtLeastThreeVowels() && atLeastOneCharacterAppearTwiceInARow() && !containsNaughtySubstring()) ||
                (!isPartOne && nGramAppearsAtLeastTwiceWithoutOverlapping() && containsPalindromeOfLengthThree())


    private val vowels = listOf('a', 'e', 'i', 'o', 'u')
    private fun String.containsAtLeastThreeVowels(): Boolean =
        toCharArray()
            .filter { it in vowels }
            .size >= 3

    private fun String.nGramAppearsAtLeastTwiceWithoutOverlapping(): Boolean {
        return toCharArray()
            .mapIndexed { i, _ ->
                if (length == 3 && this[0] == this[1] && this[1] == this[2]) null
                else if (i < length - 2 && this[i] == this[i + 1] && this[i + 1] == this[i + 2]) null
                else if (i < length - 1) this[i].toString() + this[i + 1].toString()
                else null
            }
            .filterNotNull()
            .groupingBy { it }
            .eachCount()
            .filter { it.value > 1 }
            .isNotEmpty()
    }

    private fun String.containsPalindromeOfLengthThree(): Boolean {
        return toCharArray()
            .mapIndexed { i, _ ->
                if (i < length - 2) {
                    this[i].toString() + this[i + 2].toString()
                } else {
                    null
                }
            }
            .filterNotNull()
            .any { it[0] == it[1] }
    }

    private fun String.atLeastOneCharacterAppearTwiceInARow(): Boolean {
        return toCharArray()
            .mapIndexed { i, it ->
                if (i < length - 1) {
                    it.toString() + this[i + 1].toString()
                } else {
                    null
                }
            }
            .filterNotNull()
            .any { it[0] == it[1] }
    }

    private fun String.containsNaughtySubstring(): Boolean =
        this.contains("ab")
                || this.contains("cd")
                || this.contains("pq")
                || this.contains("xy")
}


