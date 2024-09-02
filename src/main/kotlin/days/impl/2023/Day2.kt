package days.impl.`2023`

import days.impl.AdventOfCodeDayImpl
import days.model.Color
import days.model.Color.*
import days.model.CubeRevealed
import days.model.Draw
import days.model.Game

class Day2 : AdventOfCodeDayImpl(2023, 2, 8L, 2286L) {

    override fun partOne(input: List<String>): Long {
        val maximumRedAllowed = CubeRevealed(RED, 12)
        val maximumGreenAllowed = CubeRevealed(GREEN, 13)
        val maximumBlueAllowed = CubeRevealed(BLUE, 14)

        val games = createGames(input)

        return games
            .filter { it.possibleWith(listOf(maximumBlueAllowed, maximumGreenAllowed, maximumRedAllowed)) }
            .sumOf { it.id }
    }

    override fun partTwo(input: List<String>): Long {
        val games = createGames(input)

        return games
            .map { it.maxGroupByColor() }
            .sumOf { it.values.map { cubeRevealed -> cubeRevealed.qty }.reduce { acc, l -> acc * l } }
    }

    private fun createGames(input: List<String>) = input.map { createGame(it) }

    private fun createGame(input: String): Game {
        val (rawGameId, draws) = input.split(":")
        val (_, gameId) = rawGameId.trim().split(" ")

        return Game(gameId.toLong(), createDraws(draws))
    }

    private fun createDraws(rawDraws: String): List<Draw> {
        val rawDrawss = rawDraws.split(";")

        return rawDrawss.map { createDraw(it) }
    }

    private fun createDraw(rawDraw: String): Draw {
        val cubesRevealeds = rawDraw.trim().split(",")

        val cubeRevealed = cubesRevealeds.map {
            val (qty, color) = it.trim().split(" ")
            CubeRevealed(Color.valueOf(color.uppercase()), qty.toLong())
        }

        return Draw(cubeRevealed)
    }
}
