package days

interface AdventOfCodeDay {
    fun getTestExpectedOutput(): Long
    fun getInputFileName() : String
    fun partOne() :Long
    fun partTwo(): Long
}
