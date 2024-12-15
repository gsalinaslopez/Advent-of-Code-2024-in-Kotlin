
fun main() {
    fun getAntennaPositions(input: List<String>): HashMap<Char, MutableList<Pair<Int, Int>>> {
        val antennaPositions = HashMap<Char, MutableList<Pair<Int, Int>>>()
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j] != '.') {
                    if (antennaPositions[input[i][j]] == null) {
                        antennaPositions[input[i][j]] = mutableListOf()
                    }
                    antennaPositions[input[i][j]]?.addLast(Pair(i, j))
                }
            }
        }
        return antennaPositions
    }

    fun part1(input: List<String>): Int {
        val antennaPositions = getAntennaPositions(input)
        //antennaPositions.println()

        val antinodePositions = mutableSetOf<Pair<Int, Int>>()
        antennaPositions.values.forEach { coordinates ->
            for (i in coordinates.indices) {
                for (j in i + 1..<coordinates.size) {
                    val firstAntenna = coordinates[i]
                    val secondAntenna = coordinates[j]

                    val firstAntinode = Pair(
                        firstAntenna.first + (firstAntenna.first - secondAntenna.first),
                        firstAntenna.second + (firstAntenna.second - secondAntenna.second)
                    )

                    val secondAntinode = Pair(
                        secondAntenna.first + (secondAntenna.first - firstAntenna.first),
                        secondAntenna.second + (secondAntenna.second - firstAntenna.second)
                    )
                    if (firstAntinode.first in input.indices && firstAntinode.second in input[0].indices) {
                        antinodePositions.add(firstAntinode)
                    }
                    if (secondAntinode.first in input.indices && secondAntinode.second in input[0].indices) {
                        antinodePositions.add(secondAntinode)
                    }
                }
            }
        }
        return antinodePositions.size
    }

    fun part2(input: List<String>): Int {
        val antennaPositions = getAntennaPositions(input)

        val antinodePositions = mutableSetOf<Pair<Int, Int>>()
        antennaPositions.values.forEach { coordinates ->
            for (i in coordinates.indices) {
                for (j in i + 1..<coordinates.size) {
                    val firstAntenna = coordinates[i]
                    val secondAntenna = coordinates[j]

                    // repeat this until you get out of bounds
                    val firstAntinodeDiff = Pair(
                        firstAntenna.first - secondAntenna.first,
                        firstAntenna.second - secondAntenna.second
                    )
                    var firstAntinode = Pair(firstAntenna.first, firstAntenna.second)
                    while (firstAntinode.first in input.indices && firstAntinode.second in input[0].indices) {
                        antinodePositions.add(firstAntinode)
                        firstAntinode = Pair(
                            firstAntinode.first + firstAntinodeDiff.first,
                            firstAntinode.second + firstAntinodeDiff.second
                        )
                    }

                    val secondAntinodeDiff = Pair(
                        secondAntenna.first - firstAntenna.first,
                        secondAntenna.second - firstAntenna.second
                    )
                    var secondAntinode = Pair(secondAntenna.first, secondAntenna.second)
                    while (secondAntinode.first in input.indices && secondAntinode.second in input[0].indices) {
                        antinodePositions.add(secondAntinode)
                        secondAntinode = Pair(
                            secondAntinode.first + secondAntinodeDiff.first,
                            secondAntinode.second + secondAntinodeDiff.second,
                        )
                    }
                }
            }
        }
        return antinodePositions.size
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 14)
    check(part2(testInput) == 34)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
