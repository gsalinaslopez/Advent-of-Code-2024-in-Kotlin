
fun main() {
    val direction = listOf(
        Pair(-1, 0),
        Pair(0, 1),
        Pair(1, 0),
        Pair(0, -1),
    )

    val orientationChar = mapOf(
        Pair(-1, 0) to '^',
        Pair(0, 1) to '>',
        Pair(1, 0) to 'v',
        Pair(0, -1) to '<',
    )

    fun getMaze(input: List<String>) = Array(input.size) { i ->
        Array(input[i].length) { j -> input[i][j] }
    }

    fun getGuardInitialPosition(maze: Array<Array<Char>>): Pair<Int, Pair<Int, Int>> {
        for (i in maze.indices) {
            for (j in maze[i].indices) {
                val c = maze[i][j]
                when (c) {
                    '^' -> return Pair(0, Pair(i, j))
                    '>' -> return Pair(1, Pair(i, j))
                    'v' -> return Pair(2, Pair(i, j))
                    '<' -> return Pair(3, Pair(i, j))
                }
            }
        }
        return Pair(0, Pair(0, 0))
    }

    fun traverseMap(maze: Array<Array<Char>>): Set<Pair<Int, Int>>? {
        val visitedDirectionMap = mutableSetOf<Pair<Char, Pair<Int, Int>>>()
        val visitedCellMap = mutableSetOf<Pair<Int, Int>>()
        var (directionIt, guardPosition) = getGuardInitialPosition(maze)

        while (true) {
            visitedCellMap.add(guardPosition)

            val currentDirection = direction[directionIt]
            val currentOrientation: Char = orientationChar.getOrDefault(currentDirection, '.')

            if (visitedDirectionMap.contains(Pair(currentOrientation, guardPosition))) return null

            visitedDirectionMap.add(
                Pair(
                    currentOrientation,
                    guardPosition
                )
            )

            // determine the guard's next position
            val nextPosition = Pair(
                guardPosition.first + direction[directionIt].first,
                guardPosition.second + direction[directionIt].second
            )

            if (nextPosition.first !in maze.indices || nextPosition.second !in maze[0].indices) break

            if (maze[nextPosition.first][nextPosition.second] == '#') {
                directionIt = (directionIt + 1) % direction.size
            } else {
                guardPosition = nextPosition
            }

        }
        return visitedCellMap
    }

    fun part1(input: List<String>) = traverseMap(getMaze(input))?.size ?: 0

    fun part2(input: List<String>): Int {
        val maze = getMaze(input)
        val (_ , guardPosition) = getGuardInitialPosition(maze)
        val visitedCellMap = traverseMap(maze) ?: mutableSetOf()

        var result = 0
        visitedCellMap.minus(guardPosition).forEach { cell ->
            maze[cell.first][cell.second] = '#'
            if (traverseMap(maze) == null) {
                result++
            }
            maze[cell.first][cell.second] = '.'
        }
        return result
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41)
    check(part2(testInput) == 6)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
