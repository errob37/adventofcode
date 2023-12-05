package days.impl

import days.model.*

class DayThree() : AdventOfCodeDayImpl(3, 4361L, 467835L) {
    override fun partOne(input: List<String>): Long {
        val engineSchematic = extractEngineSchematic(input)
        val (numbers, symbols) = extractPosition(engineSchematic)

        return symbols
            .map { numbers.filter { n -> n.areaCovered.contains(it) } }
            .flatten()
            .toSet()
            .sumOf(NumberPosition::number)
    }

    override fun partTwo(input: List<String>): Long {
        val engineSchematic = extractEngineSchematic(input)
        val (numbers, symbols) = extractPosition(engineSchematic)
        val gears = symbols.filter { it.isGear }

        return gears
            .map { numbers.filter { n -> n.areaCovered.contains(it) } }
            .filter { it.size == 2 }
            .sumOf { it[0].number * it[1].number }
    }

    internal fun extractPosition(engineSchematic: List<SchematicEngineRow>) =
        Pair(
            engineSchematic
                .mapIndexed { i, it -> extractNumberFromRow(i, it.schematicInformations) }
                .flatten(),

            engineSchematic
                .mapIndexed { i, it -> extractSymbolFromRow(i, it.schematicInformations) }
                .flatten()
        )

    internal fun extractEngineSchematic(input: List<String>) =
        input.mapIndexed(this::extractEngineSchematicRow)

    internal fun extractEngineSchematicRow(rowNumber: Int, row: String): SchematicEngineRow {
        val infos = row.asSequence().mapIndexed(this::toEngineSchematicInformation).toList()

        return SchematicEngineRow(rowNumber, infos)
    }

    internal fun extractSymbolFromRow(rowNumber: Int, infos: List<SchematicInformation>): List<SymbolPosition> =
        infos.mapIndexedNotNull { i, it ->
            if (it.informationType in listOf(InformationType.SYMBOL)) {
                SymbolPosition(it.value, rowNumber, i)
            } else {
                null
            }
        }

    internal fun extractNumberFromRow(rowNumber: Int, infos: List<SchematicInformation>): List<NumberPosition> {
        var currentNumber: String? = null
        var startRange = 0
        val numbers = mutableListOf<NumberPosition>()
        infos.mapIndexed { index, schematicInformation ->

            if (schematicInformation.informationType == InformationType.DIGIT) {
                if (currentNumber == null) {
                    currentNumber = schematicInformation.value.toString()
                    startRange = index
                    if (index + 1 == infos.size) {
                        numbers.add(NumberPosition(currentNumber!!.toLong(), rowNumber, IntRange(startRange, index)))

                        currentNumber = null
                    }

                } else {
                    currentNumber += schematicInformation.value.toString()
                    if (index + 1 == infos.size) {
                        numbers.add(NumberPosition(currentNumber!!.toLong(), rowNumber, IntRange(startRange, index)))

                        currentNumber = null
                    }
                }
            } else {
                if (currentNumber != null) {
                    numbers.add(NumberPosition(currentNumber!!.toLong(), rowNumber, IntRange(startRange, index - 1)))

                    currentNumber = null
                }
            }
        }

        return numbers
    }
    
    internal fun toEngineSchematicInformation(column: Int, value: Char): SchematicInformation =
        SchematicInformation(
            column,
            when {
                value.isDigit() -> InformationType.DIGIT
                '.' == value -> InformationType.PERIOD
                else -> InformationType.SYMBOL
            },
            value
        )
}
