package days.model

data class Almanac(
    val input: List<String>,
    val seedMode: SeedMode,
) {
    private val seeds = extractSeed()
    private val resourceMaps = extractResourceMaps()
    private var chainMap: List<ResourceMap>? = null

    fun getMinimumLocation(): Long {
        ensureChainMap()

        return seeds.minOf {
            chainMap!!.fold(it) { acc, resourcesMap ->
                resourcesMap.map(acc)
            }
        }
    }

    private fun extractSeed(): List<Long> {
        val seedList = input
            .first()
            .split(":")[1]
            .trim()
            .split(" ")
            .filter { it.isNotBlank() }
            .map { it.toLong() }

        if (seedMode == SeedMode.UNIT) return seedList

        return seedList
            .chunked(2)
            .map {
                val start = it.first()
                val range = it[1]
                val end = start + range
                val longRange = LongRange(start, end - 1)

                longRange
            }
            .toList()
            .flatten()
    }

    private fun extractResourceMaps() =
        input
            .drop(2)
            .splitOnEmptyLine()
            .map { extractResourceMap(it) }

    private fun extractResourceMap(input: List<String>): ResourceMap {
        val (from, to) = mapNameToResourceType(input.first())
        val rawRange = input.drop(1)
        return ResourceMap(from, to, rawRange.map { extractRange(it) })
    }

    private fun mapNameToResourceType(input: String): Pair<Resource, Resource> {
        val (resourceTypes, _) = input.split(" ")
        val (fromType, _, toType) = resourceTypes.split("-")

        return Pair(
            Resource.valueOf(fromType.uppercase()),
            Resource.valueOf(toType.uppercase())
        )
    }

    private fun extractRange(input: String): Mapper {
        val (destinationRangeStart, sourceRangeStart, rangeLength) =
            input.split(" ").map { it.trim() }

        return Mapper(sourceRangeStart.toLong(), destinationRangeStart.toLong(), rangeLength.toLong())
    }

    private fun ensureChainMap() {
        if (chainMap == null) {
            chainMap = mutableListOf<ResourceMap>().apply {
                var nextMap = search(Resource.SEED)
                while (nextMap != null) {
                    add(nextMap)
                    nextMap = search(nextMap.toResource)
                }
            }
        }
    }

    private fun search(resource: Resource): ResourceMap? {
        return resourceMaps.firstOrNull { it.fromResource == resource }
    }


}

data class ResourceMap(
    val fromResource: Resource,
    val toResource: Resource,
    val mapper: List<Mapper>
) {
    fun map(l: Long): Long {
        return mapper.firstOrNull { it.toLongRange().contains(l) }?.map(l) ?: l
    }
}

data class Mapper(
    val sourceStart: Long,
    val destinationStart: Long,
    val rangeLength: Long
) {

    fun map(l: Long): Long {
        val offset = l - sourceStart
        return destinationStart + offset
    }

    fun toLongRange() = LongRange(sourceStart, sourceStart + rangeLength - 1)
}

enum class Resource {
    FERTILIZER,
    LIGHT,
    LOCATION,
    SEED,
    SOIL,
    WATER,
    TEMPERATURE,
    HUMIDITY,
}

enum class SeedMode {
    UNIT,
    RANGE
}


fun List<String>.splitOnEmptyLine(): List<List<String>> {
    val index = this.indexOfFirst(String::isNullOrBlank)
    return if (index == -1) {
        listOf(this)
    } else {
        return listOf(this.take(index)) + this.drop(index + 1).splitOnEmptyLine()
    }
}
