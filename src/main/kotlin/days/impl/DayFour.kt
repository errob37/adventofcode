package days.impl

import days.model.ScratchCard

class DayFour : AdventOfCodeDayImpl(4, 13L, 30L) {
    override fun partOne(input: List<String>): Long {
        return input
            .map { extractScratchCard(it) }
            .sumOf { it.points() }
    }

    override fun partTwo(input: List<String>): Long {
        val scratchCards = input.map { extractScratchCard(it) }

        scratchCards.forEachIndexed { index, scratchCard ->
            if (scratchCard.matches > 0) {
                for (i in 1..scratchCard.matches.toInt()) {
                    scratchCards[index + i].addCopies(scratchCard.copies)
                }
            }
        }

        return scratchCards.sumOf { it.copies }
    }

    private fun extractScratchCard(input: String): ScratchCard {
        val (winningNumbersAndId, pickedNumbers) = input.split(" | ")
        val (scratchPadId, winningNumbers) = winningNumbersAndId.split(":")

        return ScratchCard(
            scratchPadId,
            extractNumber(winningNumbers),
            extractNumber(pickedNumbers)
        )
    }

    private fun extractNumber(numbers: String) =
        numbers
            .split(" ")
            .filter { it.isNotEmpty() }
            .map { it.trim().toLong() }.toSet()

}
