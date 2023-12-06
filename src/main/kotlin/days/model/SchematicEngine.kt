package days.model


data class SchematicEngineRow(
    val rowNumber: Int,
    val schematicInformations: List<SchematicInformation> = listOf(),
)

data class SchematicInformation(
    val column: Int,
    val informationType: InformationType,
    val value: Char
)

enum class InformationType {
    DIGIT,
    PERIOD,
    SYMBOL
}

data class SymbolPosition(
    val symbol: Char,
    val rowNumber: Int,
    val colNumber: Int,
    val isGear: Boolean = symbol == '*'
)

data class NumberPosition(
    val number: Long,
    val rowNumber: Int,
    val position: IntRange,
    val areaCovered: Area = Area(
        IntRange(rowNumber - 1, rowNumber + 1),
        IntRange(position.first - 1, position.last + 1)
    )
)

data class Area(
    val rowRange: IntRange,
    val columnsRange: IntRange
) {
    fun contains(symbolPosition: SymbolPosition) =
        rowRange.contains(symbolPosition.rowNumber)
                && columnsRange.contains(symbolPosition.colNumber)
}
