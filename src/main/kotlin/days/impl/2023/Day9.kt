package days.impl.`2023`

import days.impl.AdventOfCodeDayImpl
import days.model.Oasis
import days.model.PredictionMode


class Day9 : AdventOfCodeDayImpl(2023, 9, 114L, 2L, 1731106378L) {

    override fun partOne(input: List<String>) =
        execute(input, PredictionMode.FORWARD)

    override fun partTwo(input: List<String>) =
        execute(input, PredictionMode.BACKWARD)

    private fun execute(input: List<String>, predictionMode: PredictionMode) =
        Oasis(input, predictionMode).predictionSum()
}
