package days.impl

class DayOne() : AdventOfCodeDayImpl(1, 142L, 281L) {
    companion object {
        // Conversion without modifying the complete string
        // So 'twooneight' line becomes:
        // 1) twone1oneight
        // 2) two2twone1oneight
        // 3) two2twone1oneight8eight
        private val conversion = mapOf(
            "one" to "one1one",
            "two" to "two2two",
            "three" to "three3three",
            "four" to "four4four",
            "five" to "five5five",
            "six" to "six6six",
            "seven" to "seven7seven",
            "eight" to "eight8eight",
            "nine" to "nine9nine"
        )
    }

    override fun partOne(input: List<String>) =
        input.sumOf { extractCalibrationValue(it, CalibrationSearchStrategy.DIGIT_ONLY) }

    override fun partTwo(input: List<String>): Long =
        input.sumOf { extractCalibrationValue(it, CalibrationSearchStrategy.DIGIT_OR_WORD) }

    internal fun extractCalibrationValue(
        messedUpCalibrationValue: String,
        searchStrategy: CalibrationSearchStrategy
    ): Long {

        val messedUpValue = if (searchStrategy == CalibrationSearchStrategy.DIGIT_OR_WORD) {
            convertDigitsInLetter(messedUpCalibrationValue)
        } else {
            messedUpCalibrationValue
        }

        val tens = messedUpValue.find { it.isDigit() }?.toString()?.toLong() ?: 0L
        val unit = messedUpValue.findLast { it.isDigit() }?.toString()?.toLong() ?: 0L
        return (tens * 10L + unit)
    }

    internal fun convertDigitsInLetter(messedUpCalibrationValue: String): String {
        var converted = messedUpCalibrationValue
        conversion.forEach { converted = converted.replace(it.key, it.value) }
        return converted
    }
}

internal enum class CalibrationSearchStrategy {
    DIGIT_ONLY,
    DIGIT_OR_WORD
}
