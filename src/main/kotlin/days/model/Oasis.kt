package days.model

class Oasis(
    private val input: List<String>,
    private val predictionMode: PredictionMode
) {

    fun predictionSum() = input
        .map { it.split(" ").map { s -> s.toLong() } }
        .map { Report(it, predictionMode) }
        .sumOf { it.predictValue() }
}

enum class PredictionMode { BACKWARD, FORWARD }

data class Report(
    val history: List<Long>,
    val predictionMode: PredictionMode
) {

    fun predictValue() =
        if (predictionMode == PredictionMode.FORWARD) predictNextValue()
        else predictFirstValue()

    private fun predictFirstValue(): Long {
        val cumulated = reduce()
        val inversed = cumulated.asReversed()

        inversed.forEachIndexed { index, it ->
            if (index == 0 || index == 1) inversed[index].add(0, it.first())
            else {
                inversed[index].add(0, inversed[index].first() - inversed[index - 1].first())
            }
        }
        return inversed.last().first()
    }

    private fun predictNextValue(): Long {
        val cumulated = reduce()
        val inversed = cumulated.asReversed()

        inversed.forEachIndexed { index, it ->
            if (index == 0 || index == 1) inversed[index].add(it.last())
            else {
                inversed[index].add(inversed[index].last() + inversed[index - 1].last())
            }
        }

        return inversed.last().last()
    }

    private fun reduce(): MutableList<MutableList<Long>> {
        var current = history
        val cumulated = mutableListOf(current.toMutableList())
        while (!current.all { it == 0L }) {

            current = current.mapIndexedNotNull { index, _ ->
                if (index == current.size - 1) null
                else {
                    current[index + 1] - current[index]
                }
            }.also { cumulated.add(it.toMutableList()) }
        }

        return cumulated
    }
}

