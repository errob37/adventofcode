package days.model

data class ScratchCard(
    val number: String,
    val winningNumber: Set<Long> = setOf(),
    val pickedNumber: Set<Long> = setOf(),
    val matches: Long = winningNumber.intersect(pickedNumber).size.toLong()
) {
    var copies = 1L
        private set

    fun addCopies(n: Long) {
        copies += n
    }

    fun points(): Long {
        return when (matches) {
            0L, 1L -> matches
            else -> (1 shl (matches.toInt() - 1)).toLong()
        }
    }
}
