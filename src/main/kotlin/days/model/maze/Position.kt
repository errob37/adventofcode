package days.model.maze

data class MazePosition(
    val coordinates: Coordinates,
    val item: ItemType,
) {

    var alreadyInLoop = false

    val connect =
        when (item) {
            ItemType.VERTICAL -> listOf(
                Coordinates(coordinates.line() - 1, coordinates.column()),
                Coordinates(coordinates.line() + 1, coordinates.column())
            )

            ItemType.HORIZONTAL -> listOf(
                Coordinates(coordinates.line(), coordinates.column() - 1),
                Coordinates(coordinates.line(), coordinates.column() + 1)
            )

            ItemType.BOTTOM_RIGHT -> listOf(
                Coordinates(coordinates.line(), coordinates.column() - 1),
                Coordinates(coordinates.line() - 1, coordinates.column())
            )

            ItemType.BOTTOM_LEFT -> listOf(
                Coordinates(coordinates.line(), coordinates.column() + 1),
                Coordinates(coordinates.line() - 1, coordinates.column())
            )

            ItemType.TOP_RIGHT -> listOf(
                Coordinates(coordinates.line(), coordinates.column() - 1),
                Coordinates(coordinates.line() + 1, coordinates.column())
            )

            ItemType.TOP_LEFT -> listOf(
                Coordinates(coordinates.line(), coordinates.column() + 1),
                Coordinates(coordinates.line() + 1, coordinates.column())
            )

            ItemType.START -> listOf(
                Coordinates(coordinates.line(), coordinates.column() + 1),
                Coordinates(coordinates.line(), coordinates.column() - 1),
                Coordinates(coordinates.line() + 1, coordinates.column()),
                Coordinates(coordinates.line() - 1, coordinates.column())
            )

            else -> listOf()
        }

    fun neighbors() = coordinates.neighbors()

    companion object {
        fun from(input: List<String>) =
            input.mapIndexed { index, s -> from(index, s) }

        private fun from(line: Int, s: String) =
            s.mapIndexed { index, c -> from(line, index, c) }

        private fun from(line: Int, column: Int, c: Char) =
            MazePosition(
                Coordinates(line, column),
                when (c) {
                    '|' -> ItemType.VERTICAL
                    '-' -> ItemType.HORIZONTAL
                    'L' -> ItemType.BOTTOM_LEFT
                    'J' -> ItemType.BOTTOM_RIGHT
                    '7' -> ItemType.TOP_RIGHT
                    'F' -> ItemType.TOP_LEFT
                    'S' -> ItemType.START
                    else -> ItemType.GROUND
                }
            )
    }
}
