package days.impl

import days.model.Almanac
import days.model.SeedMode.RANGE
import days.model.SeedMode.UNIT

class Day5 : AdventOfCodeDayImpl(5, 35L, 46L, 806029445L) {
    override fun partOne(input: List<String>) =
        Almanac(input, UNIT).getMinimumLocation()

    override fun partTwo(input: List<String>) =
        Almanac(input, RANGE).getMinimumLocation()
}
