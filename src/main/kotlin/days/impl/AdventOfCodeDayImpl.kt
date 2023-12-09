package days.impl

import days.AdventOfCodeDay
import util.Readers
import kotlin.time.measureTime

abstract class AdventOfCodeDayImpl(
    private val dayNumber: Int,
    private val expectedValueTestPartOne: Long,
    private val expectedValueTestPartTwo: Long,
    private val expectedValuePartOne: Long? = null
) : AdventOfCodeDay {

    fun answersForTheDay(): Pair<Long, Long> {
        return Pair(
            answer(1, expectedValueTestPartOne),
            answer(2, expectedValueTestPartTwo)
        )
    }

    private fun answer(part: Int, expectedTestValue: Long): Long {
        println("Day [$dayNumber] -> Starting part [$part]")

        var input = Readers.readTestInput(dayNumber, part)

        val impl = getImpl(part)

        val resultTest = impl(input)
        if (resultTest != expectedTestValue)
            handleIncorrectImplementation(resultTest, expectedTestValue, part)
        println("Day [$dayNumber] -> Test part [$part] = OK")


        println("Day [$dayNumber] -> Calculating result for part [$part]")
        var result: Long?
        measureTime {
            input = Readers.readInput(dayNumber)
            result = impl(input)

            if (1 == part && expectedValuePartOne != null && expectedValuePartOne != result) {
                handleIncorrectImplementation(result!!, expectedValuePartOne, part)
            }
        }.also { println("Day [$dayNumber] -> part [$part] took ${it.inWholeMilliseconds} milliseconds") }

        return result!!.also { println("Day [$dayNumber] -> part [$part] ended. Result [$it]") }
    }

    private fun getImpl(part: Int) = if (part == 1) {
        { x: List<String> -> partOne(x) }
    } else {
        { x: List<String> -> partTwo(x) }
    }

    private fun handleIncorrectImplementation(value: Long, expectedValue: Long, part: Int) {
        throw RuntimeException("Implementation of part [$part] is incorrect. Expected [$expectedValue]. Got [$value]")
    }
}
