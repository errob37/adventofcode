package days.model.maze

import java.util.*


class Maze(
    private val input: List<String>
) {

    private val size = Pair(input.size, input[0].length)
    private val maze: List<List<MazePosition>> = MazePosition.from(input)

    private val start =
        maze
            .first { line -> line.any { it.item == ItemType.START } }
            .firstNotNullOf { if (it.item == ItemType.START) it else null }


    fun getMainLoop() = createMaze()


    private fun createMaze(stack: Stack<MazePosition>? = null): Stack<MazePosition> {
        if (stack == null) return createMaze(Stack<MazePosition>().apply { add(start.apply { alreadyInLoop = true }) })

        val lastPosition = stack.peek()
        if (lastPosition.item == ItemType.START && stack.size > 2) return stack

        val x = lastPosition.neighbors()
            .filter { it in this }
            .map { maze[it.line()][it.column()] }
            .filter { it.connect.any { it == lastPosition.coordinates } }
            .filter { !it.alreadyInLoop }


        if (x.isEmpty()) {
            stack.pop()
        }


        stack.add(x.first().also { it.alreadyInLoop = true })
        return createMaze(stack)
    }

    private operator fun contains(coordinates: Coordinates) =
        coordinates.column() >= 0
                && coordinates.column() < size.second
                && coordinates.line() >= 0
                && coordinates.line() < size.first
}
