package days.model.maze


typealias Offset = Pair<Int, Int>

fun Offset.line() = this.first
fun Offset.column() = this.second


typealias Coordinates = Pair<Int, Int>

operator fun Coordinates.plus(offset: Offset) =
    Coordinates(this.line() + offset.line(), this.column() + offset.column())

fun Coordinates.neighbors() =
    listOf(
        Coordinates(this.line(), this.column() - 1),
        Coordinates(this.line(), this.column() + 1),
        Coordinates(this.line() - 1, this.column()),
        Coordinates(this.line() + 1, this.column())
    )



