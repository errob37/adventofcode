package days.impl.`2015`

import days.impl.AdventOfCodeDayImpl

class Day6 : AdventOfCodeDayImpl(2015, 6, 4L, 2000000L, 377891) {
    override fun partOne(input: List<String>): Long {
        val actions = input.map { LightsActionParser(it).parse() }
        var openCount = 0L
        val initialState = State.OFF
        for (i in 0..999) {
            for (j in 0..999) {
                var currentState = initialState
                val filtered = actions.filter { it.concernBy(i, j) }
                filtered.forEach { currentState = it.nextState(currentState) }
                if (currentState == State.ON) {
                    openCount++
                }
            }
        }

        return openCount
    }

    override fun partTwo(input: List<String>): Long {
        val actions = input.map { LightsActionParser(it).parse() }
        var totalBrightness = 0L
        for (i in 0..999) {
            for (j in 0..999) {
                var brightness = 0L
                val filtered = actions.filter { it.concernBy(i, j) }
                filtered.forEach { brightness = it.nextBrightness(brightness) }
                totalBrightness += brightness
            }
        }

        return totalBrightness
    }
}

class LightsActionParser(val s: String) {
    fun parse(): Action {
        val splitted = s.split(" ")
        //col, line
        return when (splitted.size) {
            4 -> Action(
                splitted[1].split(",")[1].toInt()..splitted[3].split(",")[1].toInt(),
                splitted[1].split(",")[0].toInt()..splitted[3].split(",")[0].toInt(),
                ActionType.TOGGLE
            )

            5 -> Action(
                splitted[2].split(",")[1].toInt()..splitted[4].split(",")[1].toInt(),
                splitted[2].split(",")[0].toInt()..splitted[4].split(",")[0].toInt(),
                if ("on" == splitted[1]) ActionType.TURN_ON else ActionType.TURN_OFF
            )

            else -> throw IllegalArgumentException()
        }
    }
}


data class Action(val lines: IntRange, val columns: IntRange, val actionType: ActionType) {
    fun concernBy(line: Int, column: Int) = lines.contains(line) && columns.contains(column)
    fun nextBrightness(currentBrightness: Long) = when (actionType) {
        ActionType.TURN_OFF -> if (currentBrightness == 0L) 0L else currentBrightness - 1
        ActionType.TURN_ON -> currentBrightness + 1
        else -> currentBrightness + 2
    }

    fun nextState(currentState: State): State = when (actionType) {
        ActionType.TURN_OFF -> State.OFF
        ActionType.TURN_ON -> State.ON
        else -> if (currentState == State.ON) State.OFF else State.ON
    }
}

enum class State {
    OFF,
    ON
}


enum class ActionType {
    TURN_OFF,
    TURN_ON,
    TOGGLE
}
