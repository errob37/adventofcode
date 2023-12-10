package days.impl

import days.model.CamelCards
import days.model.GameMode


class Day7 : AdventOfCodeDayImpl(7, 6440L, 5905L, 253933213L) {
    override fun partOne(input: List<String>) =
        execute(input, GameMode.NORMAL)


    override fun partTwo(input: List<String>) =
        execute(input, GameMode.WITH_JOKERS)


    private fun execute(input: List<String>, gameMode: GameMode) =
        CamelCards(input, gameMode).totalWinnings()
}

