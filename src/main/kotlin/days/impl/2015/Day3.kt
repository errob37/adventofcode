package days.impl.`2015`

import days.impl.AdventOfCodeDayImpl

class Day3 : AdventOfCodeDayImpl(2015, 1, 2L, 11L, 2565L) {
    override fun partOne(input: List<String>): Long {
        val paths = mutableListOf(Point(0, 0))

        input[0].forEach { c -> paths.add(paths.last().move(c)) }

        return paths.toSet().size.toLong();
    }

    override fun partTwo(input: List<String>): Long {
        val pathSanta = mutableListOf(Point(0, 0))
        val pathRoboSanta = mutableListOf(Point(0, 0))

        input[0].forEachIndexed { index, it ->
            if (index % 2 == 0) {
                pathSanta.addNewHouse(it)
            } else {
                pathRoboSanta.addNewHouse(it)
            }
        }


        return (pathSanta + pathRoboSanta).toSet().size.toLong();
    }

    fun MutableList<Point>.addNewHouse(c: Char) = this.add(this.last().move(c))

}

data class Point(val x: Long = 0L, val y: Long = 0L) {
    fun move(c: Char): Point {
        return when (c) {
            '^' -> Point(x, y + 1L)
            'v' -> Point(x, y - 1L)
            '<' -> Point(x - 1L, y)
            '>' -> Point(x + 1L, y)
            else -> throw IllegalArgumentException()
        }
    }
}
