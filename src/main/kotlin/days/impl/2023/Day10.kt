package days.impl.`2023`

import days.impl.AdventOfCodeDayImpl
import days.model.maze.Maze


class Day10 : AdventOfCodeDayImpl(2023, 10, 8L, 0L) {

    override fun partOne(input: List<String>): Long {

        return Maze(input).getMainLoop().size / 2L

    }

    override fun partTwo(input: List<String>): Long {
        return 0L
    }
}
