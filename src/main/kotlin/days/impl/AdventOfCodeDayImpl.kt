package days.impl

import days.AdventOfCodeDay
import util.Readers

abstract class AdventOfCodeDayImpl(
    private val dayNumber: Int,
    private val expectedValueTestPartOne: Long,
    private val expectedValueTestPartTwo: Long
) : AdventOfCodeDay {

    fun answersForTheDay(): Pair<Long, Long> {
        return Pair(
            answer(1, expectedValueTestPartOne),
            answer(2, expectedValueTestPartTwo)
        )
    }

    private fun answer(part: Int, expectedTestValue: Long): Long {
        var input = Readers.readTestInput(dayNumber, part)

        val impl = getImpl(part)

        val result = impl(input)
        if (result != expectedTestValue)
            throw RuntimeException("Implementation of part [$part] is incorrect. Expected [$expectedTestValue]. Got [$result]")

        input = Readers.readInput(dayNumber)
        return impl(input)
    }

    private fun getImpl(part: Int) = if (part == 1) {
        { x: List<String> -> partOne(x) }
    } else {
        { x: List<String> -> partTwo(x) }
    }
}

