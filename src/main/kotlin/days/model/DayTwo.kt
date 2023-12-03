package days.model

class Game(
    val id: Long,
    private val draws: List<Draw>
) {
    fun possibleWith(cubesRevealed: List<CubeRevealed>): Boolean {
        val byColor = maxGroupByColor()

        return cubesRevealed
            .groupBy { it.color }
            .all { byColor[it.key]?.qty!! <= it.value[0].qty }
    }

    fun maxGroupByColor() = draws.map { it.byColor() }
        .flatMap { it.asSequence() }
        .groupBy({ it.key }, { it.value })
        .mapValues { it.value.flatten().max() }
}

class Draw(private val cubesRevealed: List<CubeRevealed>) {
    fun byColor() = cubesRevealed.groupBy { it.color }
}

class CubeRevealed(val color: Color, val qty: Long) : Comparable<CubeRevealed> {
    override fun compareTo(other: CubeRevealed) = qty.compareTo(other.qty)
}

enum class Color {
    BLUE,
    GREEN,
    RED
}
