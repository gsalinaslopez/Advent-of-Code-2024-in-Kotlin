
fun main() {
    fun getMatrix(input: List<String>) =
        Array(input.size) { i ->
            Array(input[i].length) { j -> input[i][j] }
        }

    fun part1(input: List<String>): Int {
        val matrix = getMatrix(input)
        var result = 0
        for (i in matrix.indices ) {
            for (j in matrix[i].indices ) {
                // Explore in all directions
                val directions = listOf(
                    Pair(-1, -1), Pair(0, -1), Pair(1, -1),
                    Pair(-1, 0), Pair(1, 0),
                    Pair(-1, 1), Pair(0, 1), Pair(1, 1),
                )
                for (direction in directions) {
                    var newCoordinates = Pair(i, j)
                    var found = true
                    for (charToMatch in "XMAS") {
                        if (newCoordinates.first < 0 || newCoordinates.first >= matrix[0].size ||
                            newCoordinates.second < 0 || newCoordinates.second >= matrix.size) {
                            found = false
                            break
                        }

                        if (matrix[newCoordinates.first][newCoordinates.second] != charToMatch) {
                            found = false
                            break
                        }

                        newCoordinates = Pair(
                            newCoordinates.first + direction.first,
                            newCoordinates.second + direction.second
                        )
                    }
                    if (found) result++
                }
            }
        }
        return result
    }

    fun part2(input: List<String>): Int {
        val matrix = getMatrix(input)
        var result = 0
        for (i in matrix.indices ) {
            for (j in matrix[i].indices ) {
                if (matrix[i][j] != 'A') continue

                val upLeft = Pair(i - 1, j - 1)
                val upRight = Pair(i + 1, j - 1)
                val downLeft = Pair(i - 1, j + 1)
                val downRight = Pair(i + 1, j + 1)

                val firstDiagonal =
                if (upLeft.first in 0..<matrix[0].size && upLeft.second in matrix.indices &&
                    downRight.first in 0..<matrix[0].size && downRight.second in matrix.indices
                ) {
                    matrix[upLeft.first][upLeft.second] == 'M' && matrix[downRight.first][downRight.second] == 'S' ||
                    matrix[upLeft.first][upLeft.second] == 'S' && matrix[downRight.first][downRight.second] == 'M'
                } else {
                    false
                }

                val secondDiagonal =
                if (upRight.first in 0..<matrix[0].size && upRight.second in matrix.indices &&
                    downLeft.first in 0..<matrix[0].size && downLeft.second in matrix.indices
                ) {
                    matrix[upRight.first][upRight.second] == 'M' && matrix[downLeft.first][downLeft.second] == 'S' ||
                    matrix[upRight.first][upRight.second] == 'S' && matrix[downLeft.first][downLeft.second] == 'M'
                } else {
                    false
                }

                if (firstDiagonal && secondDiagonal) result++
            }
        }
        return result
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
