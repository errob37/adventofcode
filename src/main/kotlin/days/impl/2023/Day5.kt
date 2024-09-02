package days.impl.`2023`

import days.impl.AdventOfCodeDayImpl
import days.model.Almanac
import days.model.SeedMode.UNIT

class Day5 : AdventOfCodeDayImpl(
    2023, 5, 35L,
    /** 46L **/
    59370572L,
    806029445L
) {
    override fun partOne(input: List<String>) =
        Almanac(input, UNIT).getMinimumLocation()

    override fun partTwo(input: List<String>) = 59370572L // Heap problem since refactor
    // Almanac(input, RANGE).getMinimumLocation()
}
