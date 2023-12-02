package util

class Readers {
    companion object {
        fun readInput(day: Int) = read("/input/day-$day")
        fun readTestInput(day: Int, part: Int) = read("/test/day-$day-$part")
        private fun read(path: String) = object {}.javaClass.getResource("$path.txt")?.readText()!!.trim().split("\n")
    }
}
