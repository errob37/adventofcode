package days.impl

import days.model.Boat
import days.model.Record

class Day6 : AdventOfCodeDayImpl(6, 288L, 71503L, 5133600L) {

    override fun partOne(input: List<String>) =
        execute(input, false)

    override fun partTwo(input: List<String>) =
        execute(input, true)

    private fun execute(input: List<String>, onlyOneRace: Boolean): Long {
        val b = Boat()

        return Record
            .from(input, onlyOneRace)
            .map { b.beat(it).size.toLong() }
            .fold(1L) { acc, it -> acc * it }
    }
}

