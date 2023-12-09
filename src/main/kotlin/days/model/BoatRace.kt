package days.model

class Boat() {

    fun getWinningOptionCount(record: Record): Long {
        val beatingOptionPredicate = { it: Long -> (it * (record.duration - it)) > record.distance }

        val ticks = LongRange(1, record.duration)
        val minTick = ticks.first(beatingOptionPredicate)
        val maxTick = ticks.reversed().first(beatingOptionPredicate)

        return maxTick - minTick + 1
    }

}

class Record(
    val distance: Long,
    val duration: Long
) {
    companion object {
        fun from(input: List<String>, onlyOneRace: Boolean = false): List<Record> {
            val times = extractLine(input[0], onlyOneRace)
            val distance = extractLine(input[1], onlyOneRace)

            return IntRange(0, (times.size - 1))
                .map {
                    Record(
                        distance[it].trim().toLong(),
                        times[it].trim().toLong()
                    )
                }
        }

        private fun extractLine(input: String, onlyOneRace: Boolean): List<String> {
            var numbers = input.split(":")[1]
                .trim()
                .split("  ")
                .filter { it.isNotBlank() }

            if (onlyOneRace)
                numbers = listOf(numbers.joinToString("").replace(" ", ""))

            return numbers
        }
    }
}

