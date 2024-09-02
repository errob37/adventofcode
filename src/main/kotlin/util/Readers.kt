package util

class Readers {
    companion object {
        fun readInput(year: Int = 2023, day: Int) = read("/input/$year/day-$day")
        fun readTestInput(year: Int = 2023, day: Int, part: Int) = read("/test/$year/day-$day-$part")
        private fun read(path: String) = object {}.javaClass.getResource("$path.txt")?.readText()!!.trim().split("\n")
    }
}
