package days.model

class Network(private val input: List<String>) {

    private val _network = createNetwork()
    private val _directions = input.first().toList()

    fun hopCount(fromNode: Regex, toNode: Regex): Long {
        val from = _network.keys.filter { it.matches(fromNode) }
        val to = _network.keys.filter { it.matches(toNode) }

        return findLeastCommonMultiple(from.map { getHopCount(listOf(it), to) })
    }

    private fun getHopCount(startingNodes: List<String>, endingNodes: List<String>): Long {
        var hop = 0
        var currentNodes = startingNodes
        while (!endingNodes.containsAll(currentNodes)) {
            currentNodes = currentNodes.map {
                _network[it]?.let { nodes ->
                    if (nodes.isEndlessLoop) {
                        throw RuntimeException("This node loop to itself for every direction")
                    }

                    val nextDirection = _directions[hop % _directions.size]
                    if ('L' == nextDirection) nodes.left else nodes.right

                } ?: run {
                    throw RuntimeException("Node $it not found in the network")
                }
            }

            hop += 1
        }

        return hop.toLong()
    }

    private fun createNetwork() =
        input
            .drop(2)
            .map {
                val (label, rawNodes) = it.split(" = ")
                val (left, right) = rawNodes
                    .replace("(", "")
                    .replace(", ", " ")
                    .replace(")", "")
                    .split(" ")

                Node(label, left, right)
            }
            .groupBy { it.label }
            .mapValues { it.value.first() }


    private fun findLeastCommonMultiple(numbers: List<Long>): Long {
        var result = numbers[0]

        numbers
            .drop(1)
            .forEach { result = findLeastCommonMultiple(result, it) }

        return result
    }

    private fun findLeastCommonMultiple(a: Long, b: Long): Long {
        val larger = if (a > b) a else b
        val maxLeastCommonMultiple = a * b
        var current = larger
        while (current <= maxLeastCommonMultiple) {
            if (current % a == 0L && current % b == 0L) {
                return current
            }
            current += larger
        }
        return maxLeastCommonMultiple
    }
}


data class Node(
    val label: String,
    var left: String,
    var right: String
) {

    var isEndlessLoop = label == left && label == right
}
