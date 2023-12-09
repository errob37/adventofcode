package days.model

class Boat() {
    fun beat(record: Record) =
        getWinningOptions(record)

    private fun getWinningOptions(record: Record) =
        LongRange(0, record.duration)
            .map { Option(it, record.duration - it) }
            .filter { it.beating(record) }

}

class Option(
    chargeTime: Long,
    moveTime: Long
) {
    private val distance = chargeTime * moveTime


    fun beating(record: Record): Boolean {
        return distance > record.distance
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

